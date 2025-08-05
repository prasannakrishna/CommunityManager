package com.bhagwat.retail.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Entity
@Table(name = "hash_keys") // Maps to the hash_keys table
public class HashKey {

    @Id // Marks this field as the primary key
    @Column(name = "hash_key", length = 17, nullable = false, unique = true)
    private String hashKey;

    @Column(name = "data_value", columnDefinition = "TEXT") // Stores the associated data
    // Use @Column(name = "data_value", columnDefinition = "JSONB") if you want to store JSON
    private String dataValue;

    @CreationTimestamp // Automatically sets the creation timestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @CreationTimestamp // Automatically sets the creation timestamp
    @Column(name = "last_used_at", nullable = true, updatable = true)
    private OffsetDateTime lastUsedAt;

    @Column(name = "weight_percentage", nullable = true) // ADDED: New column for weight percentage
    private Integer weightPercentage;

    // Default constructor for JPA
    public HashKey() {
    }

    public HashKey(String hashKey, String dataValue) {
        this.hashKey = hashKey;
        this.dataValue = dataValue;
    }

    // Getters and Setters
    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "HashKey{" +
                "hashKey='" + hashKey + '\'' +
                ", dataValue='" + dataValue + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
