package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

    // Custom query to find SKUs by name (if needed)
    List<Sku> findBySkuNameContainingIgnoreCase(String skuName);

    // Custom query to find SKUs by client
    List<Sku> findByClient(String client);
}
