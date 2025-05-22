package com.bhagwat.retail.community.customrepo;

import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.enums.CommunityType;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Repository;
import org.springframework.data.elasticsearch.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public abstract class CommunityRepositoryCustomImpl implements ICommunitySearchRepoCustom {

    /*private final ElasticsearchOperations elasticsearchOperations;

    public CommunityRepositoryCustomImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<CommunityDocument> searchByKeywordsAndInterestCategory(Set<String> keywords, CommunityType interestCategory) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (keywords != null && !keywords.isEmpty()) {
            keywords.forEach(keyword -> boolQuery.must(QueryBuilders.termQuery("keywords", keyword)));
        }

        if (interestCategory != null) {
            boolQuery.must(QueryBuilders.termQuery("interestCategory.keyword", interestCategory.name()));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        SearchHits<CommunityDocument> searchHits = elasticsearchOperations.search(searchQuery, CommunityDocument.class);

        return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }*/
}

