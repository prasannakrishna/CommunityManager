package com.bhagwat.retail.community.customrepo;

import com.bhagwat.retail.community.entity.CommunityDocument;
import com.bhagwat.retail.community.enums.CommunityType;

import java.util.List;
import java.util.Set;

public interface ICommunitySearchRepoCustom {
    List<CommunityDocument> searchByKeywordsAndInterestCategory(Set<String> keywords, CommunityType interestCategory);
}
