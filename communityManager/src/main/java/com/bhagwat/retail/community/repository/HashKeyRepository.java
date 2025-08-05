package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.HashKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this interface as a Spring Data JPA repository
public interface HashKeyRepository extends JpaRepository<HashKey, String> {
    // JpaRepository provides methods like save(), findById(), findAll(), deleteById()
    // The second generic parameter (String) is the type of the primary key of the HashKey entity.

    // ADDED: New method for partial search (Spring Data JPA will implement this automatically)
    List<HashKey> findByHashKeyStartingWith(String prefix);

    // You can add custom query methods if needed, e.g.:
    // Optional<HashKey> findByDataValue(String dataValue);
}