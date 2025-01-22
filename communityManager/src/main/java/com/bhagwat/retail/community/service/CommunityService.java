package com.bhagwat.retail.community.service;

import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.Sku;
import com.bhagwat.retail.community.repository.CommunityRepository;
import com.bhagwat.retail.community.repository.SkuRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private SkuRepository skuRepository;

    private final StringRedisTemplate redisTemplate;

    public CommunityService(CommunityRepository communityRepository, StringRedisTemplate redisTemplate) {
        this.communityRepository = communityRepository;
        this.redisTemplate = redisTemplate;
    }

    public Community createCommunity(String name, List<String> hashKeys) {
        if (hashKeys.size() != 7) {
            throw new IllegalArgumentException("Exactly 7 hash keys are required.");
        }

        // Validate if all keys exist in a single community
        List<Community> matchingCommunities = ((List<Community>) communityRepository.findAll()).stream()
                .filter(c -> c.getHashKeys().containsAll(hashKeys))
                .collect(Collectors.toList());

        if (!matchingCommunities.isEmpty()) {
            throw new IllegalStateException(
                    "Community already exists with these hash keys: " + matchingCommunities.get(0).getName());
        }

        // Create new community
        Community community = new Community();
        community.setName(name);
        community.setHashKeys(hashKeys);
        return communityRepository.save(community);
    }

    public List<String> autoCompleteHashKeys(String prefix) {
        Set<String> rankedKeys = redisTemplate.opsForZSet()
                .rangeByScore("hashKeyRank", prefix.hashCode(), Double.MAX_VALUE);

        if (rankedKeys == null || rankedKeys.isEmpty()) {
            List<Community> communities = (List<Community>) communityRepository.findAll();
            List<String> matchingKeys = communities.stream()
                    .flatMap(c -> c.getHashKeys().stream())
                    .filter(key -> key.startsWith(prefix))
                    .distinct()
                    .collect(Collectors.toList());

            matchingKeys.forEach(key ->
                    redisTemplate.opsForZSet().add("hashKeyRank", key, System.currentTimeMillis()));

            return matchingKeys;
        }

        return new ArrayList<>(rankedKeys);
    }

    public List<Community> findCommunitiesByHashKey(String hashKey) {
        List<Community> communities = communityRepository.findByHashKeysContaining(hashKey);
        if (!communities.isEmpty()) {
            redisTemplate.opsForZSet().incrementScore("hashKeyRank", hashKey, 1);
        }
        return communities;
    }

    public Community addSkuToCommunity(Long communityId, Long skuId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new ResourceNotFoundException("Community not found"));
        Sku sku = skuRepository.findById(skuId).orElseThrow(() -> new ResourceNotFoundException("SKU not found"));
        community.getSkus().add(sku);
        return communityRepository.save(community);
    }

    public List<Community> searchCommunityByKeyword(String keyword) {
        return communityRepository.findByCommunityNameContainingIgnoreCase(keyword);
    }

    public Community createCommunity(String name, List<String> hashKeys) {
        Community community = new Community();
        community.setName(name);
        community.setHashKeys(hashKeys);
        return communityRepository.save(community);
    }

    public List<String> autoCompleteHashKeys(String prefix) {
        List<Community> communities = (List<Community>) communityRepository.findAll();
        return communities.stream()
                .flatMap(c -> c.getHashKeys().stream())
                .filter(key -> key.startsWith(prefix))
                .distinct()
                .collect(Collectors.toList());
    }

}
