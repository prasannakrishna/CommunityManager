package com.bhagwat.retail.community.dto;

import com.bhagwat.retail.community.enums.CommunityType;
import com.bhagwat.retail.community.enums.InterestCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunitySearchRequestDto {
    private CommunityType communityType;
    private InterestCategory interestCategory;
    LinkedHashSet<String> setKeyWords;
}
/*
{
  "communityType": "CULTURAL",
  "interestCategory": "FOOD",
  "setKeyWords": [
    "havyaka",
    "yellapura",
    "karwar",
    "brahmin"
  ]
}
*/
