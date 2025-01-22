package com.bhagwat.retail.community.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserCommunityMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "joined_date")
    private LocalDateTime joinedDate;

    public enum Role {
        MEMBER,
        ADMIN
    }
}
