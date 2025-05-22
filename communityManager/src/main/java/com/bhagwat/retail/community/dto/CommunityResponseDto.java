package com.bhagwat.retail.community.dto;

import com.bhagwat.retail.community.enums.CommunityType;
import com.bhagwat.retail.community.enums.InterestCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityResponseDto {
    private Long id;

    private String communityName;

    private String communityUid;

    private CommunityType type;

    private InterestCategory interestCategory;

    private String location;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Set<String> keywords;
}
