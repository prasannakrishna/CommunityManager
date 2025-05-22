package com.bhagwat.retail.community.service;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.bhagwat.retail.community.entity.CommunityDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CommunitySearchService {

    private final ElasticsearchOperations elasticsearchOperations;


    /**
     * Performs a fuzzy search on community documents with prioritized matching.
     * Documents are collected in order of highest match count (7, then 6, then 5, etc.)
     * without duplicates.
     *
     * @param searchKeys The list of 7 search keys to query.
     * @return A combined list of CommunityDocuments, ordered by match count.
     */
    public List<CommunityDocument> fuzzySearchPrioritized(List<String> searchKeys) {
        if (searchKeys == null || searchKeys.size() != 7) {
            throw new IllegalArgumentException("Expected exactly 7 search keys.");
        }

        List<CommunityDocument> allCollectedDocuments = new ArrayList<>();
        Set<String> collectedDocumentIds = new HashSet<>(); // To track unique documents

        // Iterate from 7 matches down to 1 match
        for (int minShouldMatch = 7; minShouldMatch >= 1; minShouldMatch--) {
            List<CommunityDocument> currentBatch = searchWithMinimumMatches(searchKeys, minShouldMatch, collectedDocumentIds);
            for (CommunityDocument doc : currentBatch) {
                if (collectedDocumentIds.add(doc.getId())) { // Add only if not already collected
                    allCollectedDocuments.add(doc);
                }
            }
        }

        return allCollectedDocuments;
    }

    private List<CommunityDocument> searchWithMinimumMatches(
            List<String> searchKeys, int minimumShouldMatch, Set<String> excludeIds) {

        // Build the main query (should clauses for fuzzy matching)
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        for (String key : searchKeys) {
            boolQueryBuilder.should(s -> s
                    .fuzzy(f -> f
                            .field("keywords") // Assuming 'keywords' is the field containing the document keywords
                            .value(key)
                            .fuzziness("AUTO") // Or a specific fuzziness level like "1" or "2"
                    )
            );
        }

        // Apply minimum_should_match
        if (minimumShouldMatch > 0) {
            boolQueryBuilder.minimumShouldMatch(String.valueOf(minimumShouldMatch));
        }

        // Add must_not clause to exclude already collected document IDs
        if (!excludeIds.isEmpty()) {
            boolQueryBuilder.mustNot(mn -> mn
                    .ids(i -> i.values(new ArrayList<>(excludeIds)))
            );
        }

        // Build the NativeQuery for Spring Data Elasticsearch
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .build();

        SearchHits<CommunityDocument> searchHits = elasticsearchOperations.search(query, CommunityDocument.class);

        return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    public List<CommunityDocument> searchCommunities(List<String> searchKeys, String type, String interestCategory){
        // Build the main bool query
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        // Add fuzzy should clauses for keywords
        for (String key : searchKeys) {
            boolQueryBuilder.should(s -> s
                    .fuzzy(f -> f
                            .field("keywords")
                            .value(key)
                            .fuzziness("AUTO")
                    )
            );
        }

        // Minimum 1 should match (maximum 7 based on input size)
        if (!searchKeys.isEmpty()) {
            boolQueryBuilder.minimumShouldMatch("1");
        }

        // Add filter for interestCategory if provided
        if (interestCategory != null && !interestCategory.isBlank()) {
            boolQueryBuilder.must(m -> m
                    .term(t -> t
                            .field("interestCategory.keyword") // use `.keyword` for exact match
                            .value(interestCategory)
                    )
            );
        }

        // Add filter for community type if provided
        if (type != null && !type.isBlank()) {
            boolQueryBuilder.must(m -> m
                    .term(t -> t
                            .field("communityType.keyword") // assumes type is stored as 'communityType'
                            .value(type)
                    )
            );
        }


        // Build and execute the query
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .build();

        SearchHits<CommunityDocument> searchHits =
                elasticsearchOperations.search(query, CommunityDocument.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<CommunityDocument> searchCommunitiesChanged(
            List<String> searchKeys,
            String type,
            String interestCategory) {

        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        // Fuzzy "should" clauses for each search key
        if (searchKeys != null && !searchKeys.isEmpty()) {
            for (String key : searchKeys) {
                if (key != null && !key.isBlank()) {
                    boolQueryBuilder.should(s -> s
                            .fuzzy(f -> f
                                    .field("keywords")
                                    .value(key)
                                    .fuzziness("AUTO")
                            )
                    ). should(sk -> sk // Search key should match communityName
                            .fuzzy(f -> f
                                    .field("communityName")
                                    .value(key)
                                    .fuzziness("AUTO")
                            )
                    ).minimumShouldMatch("1");
                }
            }
            boolQueryBuilder.minimumShouldMatch(String.valueOf(1));
        }

        // Must match interestCategory
        if (interestCategory != null && !interestCategory.isBlank()) {
            boolQueryBuilder.must(m -> m
                    .match(t -> t
                            .field("interestCategory")
                            .query(interestCategory)
                    )
            );
        }

        // Must match community type
        if (type != null && !type.isBlank()) {
            boolQueryBuilder.must(m -> m
                    .match(t -> t
                            .field("type")
                            .query(type)
                    )
            );
        }


        // Build and execute the query
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .build();

        SearchHits<CommunityDocument> searchHits =
                elasticsearchOperations.search(query, CommunityDocument.class);

        return searchHits.stream()
                .sorted(Comparator.comparingDouble(SearchHit<CommunityDocument> ::getScore).reversed())
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

}