package com.bhagwat.retail.community.mapper;

import com.bhagwat.retail.community.entity.Community;
import com.bhagwat.retail.community.entity.CommunityDocument;

public class CommunityMapper {
    public static CommunityDocument toDocument(Community community) {
        return CommunityDocument.builder()
                .id(community.getId().toString())
                .communityName(community.getCommunityName())
                .communityUid(community.getCommunityUid())
                //.communityType(community.getType() != null ? community.getType().name() : null)
                //.interestCategory(community.getInterestCategory() != null ? community.getInterestCategory().name() : null)
                .location(community.getLocation())
                .createdDate(community.getCreatedDate())
                .updatedDate(community.getUpdatedDate())
                .keywords(community.getKeywords())
                .build();
    }
    public static Community toEntity(CommunityDocument document) {
        Community community = new Community();
        community.setId(Long.parseLong(document.getId()));
        community.setCommunityUid(document.getCommunityUid());
        community.setCommunityName(document.getCommunityName());
        community.setType(document.getType());
        community.setInterestCategory(document.getInterestCategory());
        community.setLocation(document.getLocation());
        community.setCreatedDate(document.getCreatedDate());
        community.setUpdatedDate(document.getUpdatedDate());
        community.setKeywords(document.getKeywords());
        return community;
    }
}

