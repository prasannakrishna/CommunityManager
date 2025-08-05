package com.bhagwat.retail.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CommSubscriptionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_sub_product_id")
    private Long id;

    // list of product IDs
    //frequency
    //product count


}
