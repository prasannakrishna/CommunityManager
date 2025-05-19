package com.bhagwat.retail.community.controller;


import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.service.CommunitySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunitySearchController {

    private final CommunitySearchService searchService;

    @PostMapping("/index")
    public String indexCommunity(@RequestBody Community community) {
        searchService.indexCommunity(community);
        return "Community indexed in Elasticsearch.";
    }

    @GetMapping("/search")
    public List<CommunityDocument> search(@RequestParam String keyword) {
        return searchService.searchByKeyword(keyword);
    }
}

