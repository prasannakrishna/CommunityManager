package com.bhagwat.retail.community.controller;


import com.bhagwat.retail.community.dto.CommunitySearchRequestDto;
import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.enums.CommunityType;
import com.bhagwat.retail.community.repository.CommunitySearchRepository;
import com.bhagwat.retail.community.service.CommunityDocumentSearchService;
import com.bhagwat.retail.community.service.CommunitySearchService;
import com.bhagwat.retail.community.util.Utils;
import com.bhagwat.retail.community.validation.CommunityTypeValidator;
import com.bhagwat.retail.community.validation.InterestCategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunitySearchController {

    @Autowired
    private CommunityDocumentSearchService searchService;

    @PostMapping("/index")
    public String indexCommunity(@RequestBody Community community) {
        //searchService.indexCommunity(community);
        return "Community indexed in Elasticsearch.";
    }

    @PostMapping("/searchCommunities")
    public List<CommunityDocument> searchCommunity(@RequestBody CommunitySearchRequestDto requestDto) {
        //do validations like community type is enum provided or not
        //then interestcategory is enum or not
        // then get 5 strings from the set, put it in list and call service
        new CommunityTypeValidator()
                .setNext(new InterestCategoryValidator())
                .validate(requestDto);
        List<String> list_SearchKeys = Utils.formatSearchKeys(requestDto.getSetKeyWords());

        String communityKey = requestDto.getCommunityType().name().equals(CommunityType.DEFAULT.name()) ? "" :requestDto.getCommunityType().name();
        String interestCategoryKey = requestDto.getInterestCategory().name().equals(CommunityType.DEFAULT.name()) ? "" :requestDto.getInterestCategory().name();
        return searchService.fuzzySearchPrioritizedAndFiltered(list_SearchKeys, communityKey, interestCategoryKey);
    }
}

