package com.bhagwat.retail.community.service;

import com.bhagwat.retail.community.dto.CommunityResponseDto;
import com.bhagwat.retail.community.dto.CreateCommunityRequestDto;
import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.entity.Sku;
import com.bhagwat.retail.community.mapper.CommunityMapper;
import com.bhagwat.retail.community.repository.CommunityRepository;
import com.bhagwat.retail.community.repository.CommunitySearchRepository;
import com.bhagwat.retail.community.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    @Autowired
    private CommunitySearchRepository communityRepository;

    @Autowired
    private CommunitySearchRepository communitySearchRepository;

    @Autowired
    private SkuRepository skuRepository;

    private final StringRedisTemplate redisTemplate;

    public CommunityService(CommunitySearchRepository communityRepository, StringRedisTemplate redisTemplate) {
        this.communityRepository = communityRepository;
        this.redisTemplate = redisTemplate;
    }

    public CommunityResponseDto createCommunity(CreateCommunityRequestDto request) {

        Set<String> hashKeys = request.getKeywords();
        if (hashKeys.size() > 7) {
            throw new IllegalArgumentException("More then 7 hash keys are not required.");
        }

        // Validate if all keys exist in a single community
        /*List<Community> matchingCommunities = ((List<Community>) communityRepository.findAll()).stream()
                .filter(c -> c.getHashKeys().containsAll(hashKeys))
                .collect(Collectors.toList());

        if (!matchingCommunities.isEmpty()) {
            throw new IllegalStateException(
                    "Community already exists with these hash keys: " + matchingCommunities.get(0).getName());
        }*/

        // Create new community
        CommunityDocument community = new CommunityDocument();
        //community.setName(name);
        //community.setHashKeys(hashKeys);
        // Save to RDBMS
        community.setCommunityName(request.getCommunityName());
        community.setCommunityUid(request.getCommunityUid());
        community.setType(request.getType());
        community.setInterestCategory(request.getInterestCategory());
        community.setLocation(request.getLocation());
        community.setKeywords(request.getKeywords());
        community.setCreatedDate(LocalDateTime.now());
        community.setUpdatedDate(LocalDateTime.now());

        CommunityDocument saved = communityRepository.save(community);

        // Save to Elasticsearch
        CommunityDocument doc = CommunityDocument.builder()
                .id(String.valueOf(saved.getId()))
                .communityName(saved.getCommunityName())
                .communityUid(saved.getCommunityUid())
                .type(saved.getType())
                .interestCategory(saved.getInterestCategory())
                .location(saved.getLocation())
                .createdDate(saved.getCreatedDate())
                .updatedDate(saved.getUpdatedDate())
                .keywords(saved.getKeywords())
                .suggest(new Completion(new ArrayList<>(saved.getKeywords())))  // For autocomplete
                .build();

        communitySearchRepository.save(doc);
        return CommunityMapper.toCommunityResponseDto(saved);
    }

    public List<String> autoCompleteHashKeys(String prefix) {
        Set<String> rankedKeys = redisTemplate.opsForZSet()
                .rangeByScore("hashKeyRank", prefix.hashCode(), Double.MAX_VALUE);

        if (rankedKeys == null || rankedKeys.isEmpty()) {
            List<CommunityDocument> communities = (List<CommunityDocument>) communityRepository.findAll();
           /* List<String> matchingKeys = communities.stream()
                    .flatMap(c -> c.getHashKeys().stream())
                    .filter(key -> key.startsWith(prefix))
                    .distinct()
                    .collect(Collectors.toList());

            matchingKeys.forEach(key ->
                    redisTemplate.opsForZSet().add("hashKeyRank", key, System.currentTimeMillis()));

            return matchingKeys;*/
        }

        return new ArrayList<>(rankedKeys);
    }

    public List<Community> findCommunitiesByHashKey(String hashKey) {
        List<Community> communities= null;// communityRepository.findByHashKeysContaining(hashKey);
        if (!communities.isEmpty()) {
            redisTemplate.opsForZSet().incrementScore("hashKeyRank", hashKey, 1);
        }
        return communities;
    }

    public CommunityDocument addSkuToCommunity(Long communityId, Long skuId) {
        CommunityDocument community = communityRepository.findById(String.valueOf(communityId)).orElseThrow(() -> new ResourceNotFoundException("Community not found"));
        Sku sku = skuRepository.findById(skuId).orElseThrow(() -> new ResourceNotFoundException("SKU not found"));
        //community.getSkus().add(sku);
        return communityRepository.save(community);
    }

    public List<Community> searchCommunityByKeyword(String keyword) {
        //return communityRepository.findByCommunityNameContainingIgnoreCase(keyword);
        return null;
    }

}
