package com.bhagwat.retail.community.controller;

import com.bhagwat.retail.community.dto.CommunityResponseDto;
import com.bhagwat.retail.community.dto.CreateCommunityRequestDto;
import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/api/communities")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @PostMapping("/{communityId}/skus/{skuId}")
    public ResponseEntity<CommunityDocument> addSkuToCommunity(@PathVariable Long communityId, @PathVariable Long skuId) {
        return ResponseEntity.ok(communityService.addSkuToCommunity(communityId, skuId));
    }

    @PostMapping("/createCommunity")
    public ResponseEntity<CommunityResponseDto> createCommunity(@RequestBody CreateCommunityRequestDto request) {
        CommunityResponseDto created = communityService.createCommunity(request);
        return ResponseEntity.ok(created);
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

