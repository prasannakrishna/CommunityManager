package com.bhagwat.retail.community.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.bhagwat.retail.community.entity.CommunityDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityDocumentSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public CommunityDocumentSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * Performs a fuzzy search on community documents with prioritized matching,
     * allowing optional filtering by CommunityType and InterestCategory.
     * Documents are collected in order of highest match count (7, then 6, then 5, etc.)
     * without duplicates from the fuzzy search, then filtered by exact matches.
     *
     * @param searchKeys The list of 7 search keys to query.
     * @param communityType Optional: The CommunityType to filter by (e.g., "Linguistic", "Religious"). Can be null.
     * @param interestCategory Optional: The InterestCategory to filter by (e.g., "food", "fashion"). Can be null.
     * @return A combined list of CommunityDocuments, ordered by fuzzy match count and filtered.
     */
    public List<CommunityDocument> fuzzySearchPrioritizedAndFiltered(
            List<String> searchKeys,
            String communityType,
            String interestCategory) {

        if (searchKeys == null || searchKeys.size() != 7) {
            throw new IllegalArgumentException("Expected exactly 7 search keys.");
        }

        List<CommunityDocument> allCollectedDocuments = new ArrayList<>();
        Set<String> collectedDocumentIds = new HashSet<>(); // To track unique documents

        // Iterate from 7 matches down to 1 match for fuzzy search
        for (int minShouldMatch = 7; minShouldMatch >= 1; minShouldMatch--) {
            List<CommunityDocument> currentBatch = searchWithMinimumFuzzyMatches(
                    searchKeys, minShouldMatch, communityType, interestCategory, collectedDocumentIds);

            for (CommunityDocument doc : currentBatch) {
                if (collectedDocumentIds.add(doc.getId())) { // Add only if not already collected
                    allCollectedDocuments.add(doc);
                }
            }
        }

        return allCollectedDocuments;
    }

    private List<CommunityDocument> searchWithMinimumFuzzyMatches(
            List<String> searchKeys,
            int minimumShouldMatch,
            String communityType,
            String interestCategory,
            Set<String> excludeIds) {

        // Build the main query (should clauses for fuzzy matching)
        BoolQuery.Builder mainBoolQueryBuilder = QueryBuilders.bool();

        // Add fuzzy search clauses for each search key
        for (String key : searchKeys) {
            mainBoolQueryBuilder.should(s -> s
                    .bool(b -> b
                            .should(sk -> sk // Search key should fuzzy match 'keywords'
                                    .fuzzy(f -> f
                                            .field("keywords")
                                            .value(key)
                                            .fuzziness("AUTO")
                                    )
                            )
                            .should(sk -> sk // Search key should fuzzy match 'communityName'
                                    .fuzzy(f -> f
                                            .field("communityName")
                                            .value(key)
                                            .fuzziness("AUTO")
                                    )
                            )
                            .minimumShouldMatch("1") // At least one of the inner 'should' clauses must match for this search key
                    )
            );
        }

        // Apply overall minimum_should_match for the number of search keys that found a match
        if (minimumShouldMatch > 0) {
            mainBoolQueryBuilder.minimumShouldMatch(String.valueOf(minimumShouldMatch));
        }

        // Dynamically add filter clauses if communityType is provided
        if (communityType != null && !communityType.trim().isEmpty()) {
            mainBoolQueryBuilder.filter(f -> f
                    .term(t -> t
                            .field("type.keyword") // Use .keyword for exact match on enum/keyword field
                            .value(communityType.toUpperCase()) // Assuming your enum values are uppercase
                    )
            );
        }

        // Dynamically add filter clauses if interestCategory is provided
        if (interestCategory != null && !interestCategory.trim().isEmpty()) {
            mainBoolQueryBuilder.filter(f -> f
                    .term(t -> t
                            .field("interestCategory.keyword") // Use .keyword for exact match on enum/keyword field
                            .value(interestCategory.toUpperCase()) // Assuming your enum values are uppercase
                    )
            );
        }

        // Add must_not clause to exclude already collected document IDs
        if (!excludeIds.isEmpty()) {
            mainBoolQueryBuilder.mustNot(mn -> mn
                    .ids(i -> i.values(new ArrayList<>(excludeIds)))
            );
        }

        // Build the NativeQuery for Spring Data Elasticsearch
        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(mainBoolQueryBuilder.build()))
                .withSort(s -> s.score(score -> score.order(co.elastic.clients.elasticsearch._types.SortOrder.Desc))) // Sort by relevance score
                .withMaxResults(10000) // Set a reasonable size to fetch all potential matches for a given minShouldMatch
                .build();

        SearchHits<CommunityDocument> searchHits = elasticsearchOperations.search(query, CommunityDocument.class);

        /*return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());*/
        return searchHits.stream()
                .map(hit -> {
                    CommunityDocument doc = hit.getContent();
                    doc.setScore(hit.getScore()); // Set the score from SearchHit
                    return doc;
                })
                .collect(Collectors.toList());
    }
}
