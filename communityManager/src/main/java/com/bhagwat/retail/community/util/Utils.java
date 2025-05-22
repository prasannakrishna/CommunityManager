package com.bhagwat.retail.community.util;

import com.bhagwat.retail.community.exception.SearchKeysMissingException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class Utils {
    public static List<String> formatSearchKeys(LinkedHashSet<String> searchKeys) {
        if (searchKeys == null || searchKeys.isEmpty()) {
            throw new SearchKeysMissingException("At least one keyword must be provided.");
        }

        List<String> result = new ArrayList<>(7);
        Iterator<String> iterator = searchKeys.iterator();

        // Add up to 7 elements
        while (iterator.hasNext() && result.size() < 7) {
            result.add(iterator.next());
        }

        // Fill with empty strings if less than 7
        while (result.size() < 7) {
            result.add("");
        }

        return result;
    }
}
