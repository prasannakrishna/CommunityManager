package com.bhagwat.retail.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "search_key")
public class SearchKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "node_char", nullable = false, length = 1)
    private Character nodeChar;
    @Column(name = "node_id", nullable = false)
    private Integer nodeId; // ASCII value of the character (e.g., 'a' = 97)

    @Column(name = "root_node_id", nullable = false)
    private Integer rootNodeId; // For the root node, it would be 0 or start ASCII value

    @Column(name = "link_node_id", nullable = true)
    private Long linkNodeId; // Linked node ASCII value

    @Column(name = "last_node", nullable = false)
    private Boolean lastNode = false; // Indicates the end of a valid word

    @Column(name = "height", nullable = false)
    private Integer height; // Height of the node in the Trie structure

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; // To mark active or inactive nodes
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    public Character getNodeChar() {
        return nodeChar;
    }

    public void setNodeChar(Character nodeChar) {
        this.nodeChar = nodeChar;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(Integer rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public Long getLinkNodeId() {
        return linkNodeId;
    }

    public void setLinkNodeId(Long linkNodeId) {
        this.linkNodeId = linkNodeId;
    }

    public Boolean getLastNode() {
        return lastNode;
    }

    public void setLastNode(Boolean lastNode) {
        this.lastNode = lastNode;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}

