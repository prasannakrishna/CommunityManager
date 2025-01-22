package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByCommunityNameContainingIgnoreCase(String name);
    List<Community> findByLocationAndKeywordsIn(String location, Set<String> keywords);
}