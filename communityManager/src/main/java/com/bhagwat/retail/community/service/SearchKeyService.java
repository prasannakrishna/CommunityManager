package com.bhagwat.retail.community.service;

import com.bhagwat.retail.community.entity.SearchKey;
import com.bhagwat.retail.community.repository.SearchKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchKeyService {

    @Autowired
    private SearchKeyRepository searchKeyRepository;

   // private Map<String, List<String>> userSearchCache = new HashMap<>();

    // Method to add a word to the Trie
    public void addWord(String word) {
        int rootNodeId = 0;
        Integer previousNodeId = rootNodeId;
        Integer height = 1;

        for (char ch : word.toCharArray()) {
            Integer currentNodeId = (int) ch;
            Optional<SearchKey> existingNode = searchKeyRepository.findByNodeIdAndLinkNodeIdAndIsActiveTrue(previousNodeId, currentNodeId);
            if (existingNode.isPresent()) {
                previousNodeId = currentNodeId;
            } else {
                SearchKey node = new SearchKey();
                node.setNodeId(currentNodeId);
                node.setRootNodeId(rootNodeId);
                node.setLinkNodeId(Long.valueOf(previousNodeId));
                node.setHeight(height++);
                node.setLastNode(false);
                searchKeyRepository.save(node);
                previousNodeId = currentNodeId;
            }
        }

        // Mark the final node as the end of the word
        SearchKey lastNode = searchKeyRepository.findByNodeIdAndLinkNodeIdAndIsActiveTrue(previousNodeId, null)
                .orElseThrow(() -> new RuntimeException("Node not found"));
        lastNode.setLastNode(true);
        searchKeyRepository.save(lastNode);
    }

    // Search for words with the given prefix
    public List<String> searchByPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        Integer rootNodeId = 0;
        Integer previousNodeId = rootNodeId;

        for (char ch : prefix.toCharArray()) {
            Integer currentNodeId = (int) ch;
            Optional<SearchKey> node = searchKeyRepository.findByNodeIdAndLinkNodeIdAndIsActiveTrue(previousNodeId, currentNodeId);
            if (node.isPresent()) {
                previousNodeId = currentNodeId;
            } else {
                return results;
            }
        }

        // Fetch all nodes that complete the prefix
        findAllWords(prefix, previousNodeId, results);
        return results;
    }

    // Helper method to find all words from a given node
    private void findAllWords(String prefix, Integer nodeId, List<String> results) {
        List<SearchKey> nodes = searchKeyRepository.findByNodeIdAndIsActiveTrue(nodeId);
        for (SearchKey node : nodes) {
            String word = prefix + (char) node.getNodeId().intValue();
            if (node.getLastNode()) {
                results.add(word);
            }
            findAllWords(word, node.getLinkNodeId(), results);
        }
    }

    /*@Cacheable(value = "searchResults", key = "#userId + ':' + #prefix")
    public List<String> searchByPrefixWithUserContext(String prefix, Long rootNodeId, Long userId) {
        List<String> results = new ArrayList<>();

        // 1. Check if the cache contains results for the given prefix and userId
        String cacheKey = userId + ":" + prefix;
        if (userSearchCache.containsKey(cacheKey)) {
            return userSearchCache.get(cacheKey);
        }

        // 2. Get the communities the user is part of
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Set<Long> communityIds = user.getCommunities().stream().map(c -> c.getId()).collect(Collectors.toSet());

        // 3. Retrieve only active nodes matching the prefix that belong to the user's communities
        List<SearchKey> nodes = searchKeyRepository.findByRootNodeIdAndActiveTrue(rootNodeId);
        for (SearchKey node : nodes) {
            if (communityIds.contains(node.getCommunity().getId()) && node.getNodeChar().toString().startsWith(prefix)) {
                results.add(buildWord(node.getId(), prefix));
            }
        }

        // 4. Cache the results for subsequent lookups
        userSearchCache.put(cacheKey, results);
        return results;
    }*/
    private String buildWord(Long nodeId, String prefix) {
        StringBuilder word = new StringBuilder(prefix);
        Optional<SearchKey> nodeOpt = searchKeyRepository.findById(nodeId);

        while (nodeOpt.isPresent() && !nodeOpt.get().getLastNode()) {
            SearchKey node = nodeOpt.get();
            word.append(node.getNodeChar());
            nodeOpt = searchKeyRepository.findById(node.getLinkNodeId());
        }
        return word.toString();
    }


    // Insert a word into the trie using the search_key table
    public void insertWord(String word, Long rootNodeId) {
        Long currentNodeId = rootNodeId;
        int height = 1;

        for (int i = 0; i < word.length(); i++) {
            Character nodeChar = word.charAt(i);
            SearchKey newNode = new SearchKey();
            newNode.setRootNodeId(Math.toIntExact(rootNodeId));
            newNode.setNodeChar(nodeChar);
            newNode.setHeight(height++);
            newNode.setLastNode(false);
            newNode.setActive(true); // Set the new node as active by default

            // Check if there's an existing active link_node_id from the current node
            SearchKey existingNode = searchKeyRepository
                    .findByRootNodeIdAndActiveTrue(rootNodeId)
                    .stream()
                    .filter(n -> n.getNodeChar().equals(nodeChar))
                    .findFirst()
                    .orElse(null);

            if (existingNode != null) {
                currentNodeId = existingNode.getId();
            } else {
                newNode.setLinkNodeId((long) Math.toIntExact(currentNodeId));
                searchKeyRepository.save(newNode);
                currentNodeId = newNode.getId();
            }
        }

        // Mark the last character as the end of the word
        SearchKey lastNode = searchKeyRepository.findById(currentNodeId).orElseThrow();
        lastNode.setLastNode(true);
        searchKeyRepository.save(lastNode);
    }

    // Retrieve all search keys that match the given string prefix
    public List<String> searchByPrefix(String prefix, Long rootNodeId) {
        List<String> results = new ArrayList<>();
        List<SearchKey> nodes = searchKeyRepository.findByRootNodeIdAndActiveTrue(rootNodeId); // Use active nodes only

        // Traverse through the nodes to match the prefix
        for (SearchKey node : nodes) {
            if (node.getNodeChar().toString().startsWith(prefix)) {
                results.add(buildWord(node.getId(), prefix));
            }
        }
        return results;
    }
    // Helper method to find all words from a given node
    private void findAllWords(String prefix, Long nodeId, List<String> results) {
        List<SearchKey> nodes = searchKeyRepository.findByNodeIdAndIsActiveTrue(Math.toIntExact(nodeId));
        for (SearchKey node : nodes) {
            String word = prefix + (char) node.getNodeId().longValue();
            if (node.getLastNode()) {
                results.add(word);
            }
            findAllWords(word, node.getLinkNodeId(), results);
        }
    }
    // Mark a search key as inactive
    /*public void deactivateSearchKey(Long id) {
        SearchKey searchKey = searchKeyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Search key not found with id: " + id));
        searchKey.setActive(false);
        searchKeyRepository.save(searchKey);
    }*/
}

