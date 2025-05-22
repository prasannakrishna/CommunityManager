package com.bhagwat.retail.community.entity;

import jakarta.persistence.*;

import jakarta.persistence.*;  // or javax.persistence depending on your setup

@Entity
@Table(name = "\"user\"")  // Postgres treats unquoted names as lowercase, "user" is a reserved keyword so quote it
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "user_community_association")
    private String userCommunityAssociation;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "community_type")
    private String communityType;

    @Column(name = "is_admin_flag")
    private Boolean isAdminFlag;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}
