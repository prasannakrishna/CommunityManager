package com.bhagwat.retail.community.entity;

import com.bhagwat.retail.community.enums.CommunityType;
import com.bhagwat.retail.community.enums.InterestCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "community")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "community_name", nullable = false)
    private String communityName;

    @Column(name = "community_uid", unique = true)
    private String communityUid;

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type", nullable = true)
    private CommunityType type;  // ENUM: SUPPLIER, CONSUMER, SERVICE_PROVIDERS

    @Enumerated(EnumType.STRING)
    @Column(name = "interst_category", nullable = true)
    private InterestCategory interestCategory;  // ENUM: SUPPLIER, CONSUMER, SERVICE_PROVIDERS

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
}

