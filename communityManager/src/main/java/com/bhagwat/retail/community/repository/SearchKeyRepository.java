package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.SearchKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchKeyRepository extends JpaRepository<SearchKey, Long> {

    // Find the root node by node ID and is active
    Optional<SearchKey> findByNodeIdAndRootNodeIdAndIsActiveTrue(Integer nodeId, Integer rootNodeId);

    // Get linked nodes by node ID and root node
    List<SearchKey> findByRootNodeIdAndLinkNodeIdAndIsActiveTrue(Integer rootNodeId, Integer linkNodeId);

    // Search for a node by its link and root node
    Optional<SearchKey> findByNodeIdAndLinkNodeIdAndIsActiveTrue(Integer nodeId, Integer linkNodeId);

    // Custom method to search by prefix
    List<SearchKey> findByNodeIdAndIsActiveTrue(Integer nodeId);

    List<SearchKey> findByRootNodeIdAndActiveTrue(Long rootNodeId);
}

