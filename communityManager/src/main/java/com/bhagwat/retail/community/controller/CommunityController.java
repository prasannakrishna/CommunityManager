package com.bhagwat.retail.community.controller;

import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @PostMapping("/{communityId}/skus/{skuId}")
    public ResponseEntity<Community> addSkuToCommunity(@PathVariable Long communityId, @PathVariable Long skuId) {
        return ResponseEntity.ok(communityService.addSkuToCommunity(communityId, skuId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Community>> searchCommunities(@RequestParam String keyword) {
        return ResponseEntity.ok(communityService.searchCommunityByKeyword(keyword));
    }
    @PostMapping
    public Community createCommunitywithkey(@RequestParam String name, @RequestBody List<String> hashKeys) {
        if (hashKeys.size() != 7) {
            throw new IllegalArgumentException("Exactly 7 hash keys are required.");
        }
        return communityService.createCommunity(name, hashKeys);
    }

    @GetMapping("/autocomplete")
    public List<String> autoCompleteHashKeys(@RequestParam String prefix) {
        return communityService.autoCompleteHashKeys(prefix);
    }

    //start
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }



    @GetMapping("/search")
    public List<Community> findCommunitiesByHashKey(@RequestParam String hashKey) {
        return communityService.findCommunitiesByHashKey(hashKey);
    }
    //end
}

