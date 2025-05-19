package com.bhagwat.retail.community.service;
import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.mapper.CommunityMapper;
import com.bhagwat.retail.community.repository.CommunitySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CommunitySearchService {

    private final CommunitySearchRepository searchRepository;

    public void indexCommunity(Community community) {
        CommunityDocument document = CommunityMapper.toDocument(community);
        searchRepository.save(document);
    }

    public List<CommunityDocument> searchByKeyword(String keyword) {
        return searchRepository.findByKeywordsContaining(keyword);
    }
}