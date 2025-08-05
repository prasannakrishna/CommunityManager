package com.bhagwat.retail.community.dto;

import com.bhagwat.retail.community.entity.HashKey;

import java.time.OffsetDateTime;

public class HashKeyResponse {
    private String hashKey;
    private String dataValue;
    private OffsetDateTime createdAt;
    private int weight;

    public HashKeyResponse(HashKey hashKey) {
        this.hashKey = hashKey.getHashKey();
        this.dataValue = hashKey.getDataValue();
        this.createdAt = hashKey.getCreatedAt();
    }

    public int getWeight() {
        return weight;
    }

    // Getters
    public String getHashKey() {
        return hashKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}