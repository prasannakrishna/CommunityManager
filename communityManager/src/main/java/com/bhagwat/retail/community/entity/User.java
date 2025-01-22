package com.bhagwat.retail.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")  // Specify the table name explicitly
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)  // Map to `user_name` column in the database
    private String username;  // Changed the name to match the entity field

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

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUserCommunityAssociation() { return userCommunityAssociation; }
    public void setUserCommunityAssociation(String userCommunityAssociation) { this.userCommunityAssociation = userCommunityAssociation; }

    public Long getCommunityId() { return communityId; }
    public void setCommunityId(Long communityId) { this.communityId = communityId; }

    public String getCommunityType() { return communityType; }
    public void setCommunityType(String communityType) { this.communityType = communityType; }

    public Boolean getIsAdminFlag() { return isAdminFlag; }
    public void setIsAdminFlag(Boolean isAdminFlag) { this.isAdminFlag = isAdminFlag; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
