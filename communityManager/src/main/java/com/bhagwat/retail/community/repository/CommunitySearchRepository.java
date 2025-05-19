package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.CommunityDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunitySearchRepository extends ElasticsearchRepository<CommunityDocument, String> {
    List<CommunityDocument> findByKeywordsContaining(String keyword);
}
