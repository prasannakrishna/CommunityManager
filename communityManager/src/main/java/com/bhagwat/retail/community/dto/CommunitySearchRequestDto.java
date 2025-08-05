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
{{
  "communityType": "SOCIO_CULTURAL",
  "interestCategory": "ORGANIC_AND_NATURAL_PRODUCTS",
  "setKeyWords": [
    "hdbodhkp",
            "creative",
            "u1oar",
            "kunjz",
            "avegiy",
            "tlzrjhat",
            "ORGANIC_AND_NATURAL_PRODUCTS",
            "jfvznvpi",
            "ie6w1q",
            "fdrax"
  ]
}
*/
