package com.bhagwat.retail.community.validation;

import com.bhagwat.retail.community.dto.CommunitySearchRequestDto;

public interface IValidator {
    IValidator setNext(IValidator next);
    void validate(CommunitySearchRequestDto request);
}
