package com.bhagwat.retail.community.controller;

import com.bhagwat.retail.community.service.SearchKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-key")
public class SearchKeyController {

    @Autowired
    private SearchKeyService trieService;

    /*@GetMapping("/search/user-context")
    public List<String> searchWithUserContext(@RequestParam String prefix, @RequestParam Long rootNodeId, @RequestParam Long userId) {
        return trieService.searchByPrefixWithUserContext(prefix, rootNodeId, userId);
    }*/

    @PostMapping("/insert")
    public String insertWord(@RequestParam String word, @RequestParam Long rootNodeId) {
        trieService.insertWord(word, rootNodeId);
        return "Word inserted successfully.";
    }

    @GetMapping("/search")
    public List<String> searchByPrefix(@RequestParam String prefix, @RequestParam Long rootNodeId) {
        return trieService.searchByPrefix(prefix, rootNodeId);
    }

    /*@PutMapping("/deactivate/{id}")
    public String deactivateSearchKey(@PathVariable Long id) {
        trieService.deactivateSearchKey(id);
        return "Search key deactivated successfully.";
    }*/
}
