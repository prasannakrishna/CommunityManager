package com.bhagwat.retail.community.entity;

import com.bhagwat.retail.community.enums.CommunityType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "community")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "community_name", nullable = false)
    private String communityName;

    @Column(name = "community_uid", unique = true)
    private String communityUid;

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type", nullable = false)
    private CommunityType type;  // ENUM: SUPPLIER, CONSUMER, SERVICE_PROVIDERS

    @ManyToMany
    @JoinTable(
            name = "community_sku_mapping",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "sku_id")
    )
    private List<Sku> skus = new ArrayList<>();

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ElementCollection
    @CollectionTable(name = "community_keywords", joinColumns = @JoinColumn(name = "community_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();  // Stores the 7 hash keys

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCommunityMapping> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityUid() {
        return communityUid;
    }

    public void setCommunityUid(String communityUid) {
        this.communityUid = communityUid;
    }

    public CommunityType getType() {
        return type;
    }

    public void setType(CommunityType type) {
        this.type = type;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public List<UserCommunityMapping> getUsers() {
        return users;
    }

    public void setUsers(List<UserCommunityMapping> users) {
        this.users = users;
    }
// Getters and Setters
}

