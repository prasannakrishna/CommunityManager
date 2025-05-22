package com.bhagwat.retail.community.validation;

import com.bhagwat.retail.community.dto.CommunitySearchRequestDto;
import com.bhagwat.retail.community.enums.InterestCategory;
import com.bhagwat.retail.community.exception.InvalidEnumValueException;

public class InterestCategoryValidator implements IValidator{
    private IValidator next;
    @Override
    public IValidator setNext(IValidator next) {
        this.next = next;
        return next;
    }

    @Override
    public void validate(CommunitySearchRequestDto request) {
        try {
            request.getInterestCategory().getCode();
            InterestCategory.valueOf(request.getInterestCategory().name());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidEnumValueException("Invalid interestCategory: " + request.getInterestCategory());
        }

        if (next != null) {
            next.validate(request);
        }
    }
}
