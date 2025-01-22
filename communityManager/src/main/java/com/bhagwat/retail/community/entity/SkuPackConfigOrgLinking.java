package com.bhagwat.retail.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sku_pack_condif_org_linking")
public class SkuPackConfigOrgLinking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;  // Foreign key reference to the sku table

    @Column(name = "pack_config_id", nullable = false)
    private Long packConfigId;  // Foreign key reference to the packing_configuration table

    @Column(name = "org_id", nullable = false)
    private Long orgId;  // Foreign key reference to the business_org table

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getPackConfigId() {
        return packConfigId;
    }

    public void setPackConfigId(Long packConfigId) {
        this.packConfigId = packConfigId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
