package com.bhagwat.retail.community.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sku")
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_name", nullable = false)
    private String skuName;

    @Column(name = "description")
    private String description;

    @Column(name = "product_group")
    private String productGroup;

    @Column(name = "each_volume", precision = 10, scale = 2)
    private BigDecimal eachVolume;

    @Column(name = "each_weight", precision = 10, scale = 2)
    private BigDecimal eachWeight;

    @Column(name = "each_height", precision = 10, scale = 2)
    private BigDecimal eachHeight;

    @Column(name = "each_depth", precision = 10, scale = 2)
    private BigDecimal eachDepth;

    @Column(name = "tag_volume", precision = 10, scale = 2)
    private BigDecimal tagVolume;

    @Column(name = "beam_rail_item_length", precision = 10, scale = 2)
    private BigDecimal beamRailItemLength;

    @Column(name = "client")
    private String client;

    @Column(name = "label")
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @Column(name = "is_plant_based", nullable = false)
    private Boolean isPlantBased = false;

    @Column(name = "is_medicinal", nullable = false)
    private Boolean isMedicinal = false;

    @Column(name = "is_hazardous", nullable = false)
    private Boolean isHazardous = false;

    @Column(name = "is_flammable", nullable = false)
    private Boolean isFlammable = false;

    @Column(name = "split_lowest", precision = 10, scale = 2)
    private BigDecimal splitLowest;

    @Column(name = "is_edible", nullable = false)
    private Boolean isEdible = false;

    @Column(name = "is_batch_controlled", nullable = false)
    private Boolean isBatchControlled = false;

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = "created_by", nullable = false)
    private Long createdBy; // Foreign key reference to the user table

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy; // Foreign key reference to the user table

    // User-defined fields (text fields)
    @Column(name = "user_defined_text_1")
    private String userDefinedText1;

    @Column(name = "user_defined_text_2")
    private String userDefinedText2;

    @Column(name = "user_defined_text_3")
    private String userDefinedText3;

    @Column(name = "user_defined_text_4")
    private String userDefinedText4;

    @Column(name = "user_defined_text_5")
    private String userDefinedText5;

    // User-defined boolean fields
    @Column(name = "user_defined_check_1", nullable = false)
    private Boolean userDefinedCheck1 = false;

    @Column(name = "user_defined_check_2", nullable = false)
    private Boolean userDefinedCheck2 = false;

    @Column(name = "user_defined_check_3", nullable = false)
    private Boolean userDefinedCheck3 = false;

    @Column(name = "user_defined_check_4", nullable = false)
    private Boolean userDefinedCheck4 = false;

    @Column(name = "user_defined_check_5", nullable = false)
    private Boolean userDefinedCheck5 = false;

    // User-defined date fields
    @Column(name = "user_defined_date_1")
    private LocalDate userDefinedDate1;

    @Column(name = "user_defined_date_2")
    private LocalDate userDefinedDate2;

    @Column(name = "user_defined_date_3")
    private LocalDate userDefinedDate3;

    @Column(name = "user_defined_date_4")
    private LocalDate userDefinedDate4;

    @Column(name = "user_defined_date_5")
    private LocalDate userDefinedDate5;

    @Column(name = "is_org_sku", nullable = false)
    private Boolean isOrgSku = false;

    @Column(name = "org_id")
    private Long orgId; // Foreign key reference to the business_org table

    // Enum to define SKU Colors
    public enum Color {
        RED, BLUE, GREEN, YELLOW, BLACK, WHITE, ORANGE, PURPLE, BROWN, GREY
    }

    // Getters and Setters for each field

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public BigDecimal getEachVolume() {
        return eachVolume;
    }

    public void setEachVolume(BigDecimal eachVolume) {
        this.eachVolume = eachVolume;
    }

    public BigDecimal getEachWeight() {
        return eachWeight;
    }

    public void setEachWeight(BigDecimal eachWeight) {
        this.eachWeight = eachWeight;
    }

    public BigDecimal getEachHeight() {
        return eachHeight;
    }

    public void setEachHeight(BigDecimal eachHeight) {
        this.eachHeight = eachHeight;
    }

    public BigDecimal getEachDepth() {
        return eachDepth;
    }

    public void setEachDepth(BigDecimal eachDepth) {
        this.eachDepth = eachDepth;
    }

    public BigDecimal getTagVolume() {
        return tagVolume;
    }

    public void setTagVolume(BigDecimal tagVolume) {
        this.tagVolume = tagVolume;
    }

    public BigDecimal getBeamRailItemLength() {
        return beamRailItemLength;
    }

    public void setBeamRailItemLength(BigDecimal beamRailItemLength) {
        this.beamRailItemLength = beamRailItemLength;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getPlantBased() {
        return isPlantBased;
    }

    public void setPlantBased(Boolean plantBased) {
        isPlantBased = plantBased;
    }

    public Boolean getMedicinal() {
        return isMedicinal;
    }

    public void setMedicinal(Boolean medicinal) {
        isMedicinal = medicinal;
    }

    public Boolean getHazardous() {
        return isHazardous;
    }

    public void setHazardous(Boolean hazardous) {
        isHazardous = hazardous;
    }

    public Boolean getFlammable() {
        return isFlammable;
    }

    public void setFlammable(Boolean flammable) {
        isFlammable = flammable;
    }

    public BigDecimal getSplitLowest() {
        return splitLowest;
    }

    public void setSplitLowest(BigDecimal splitLowest) {
        this.splitLowest = splitLowest;
    }

    public Boolean getEdible() {
        return isEdible;
    }

    public void setEdible(Boolean edible) {
        isEdible = edible;
    }

    public Boolean getBatchControlled() {
        return isBatchControlled;
    }

    public void setBatchControlled(Boolean batchControlled) {
        isBatchControlled = batchControlled;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUserDefinedText1() {
        return userDefinedText1;
    }

    public void setUserDefinedText1(String userDefinedText1) {
        this.userDefinedText1 = userDefinedText1;
    }

    public String getUserDefinedText2() {
        return userDefinedText2;
    }

    public void setUserDefinedText2(String userDefinedText2) {
        this.userDefinedText2 = userDefinedText2;
    }

    public String getUserDefinedText3() {
        return userDefinedText3;
    }

    public void setUserDefinedText3(String userDefinedText3) {
        this.userDefinedText3 = userDefinedText3;
    }

    public String getUserDefinedText4() {
        return userDefinedText4;
    }

    public void setUserDefinedText4(String userDefinedText4) {
        this.userDefinedText4 = userDefinedText4;
    }

    public String getUserDefinedText5() {
        return userDefinedText5;
    }

    public void setUserDefinedText5(String userDefinedText5) {
        this.userDefinedText5 = userDefinedText5;
    }

    public Boolean getUserDefinedCheck1() {
        return userDefinedCheck1;
    }

    public void setUserDefinedCheck1(Boolean userDefinedCheck1) {
        this.userDefinedCheck1 = userDefinedCheck1;
    }

    public Boolean getUserDefinedCheck2() {
        return userDefinedCheck2;
    }

    public void setUserDefinedCheck2(Boolean userDefinedCheck2) {
        this.userDefinedCheck2 = userDefinedCheck2;
    }

    public Boolean getUserDefinedCheck3() {
        return userDefinedCheck3;
    }

    public void setUserDefinedCheck3(Boolean userDefinedCheck3) {
        this.userDefinedCheck3 = userDefinedCheck3;
    }

    public Boolean getUserDefinedCheck4() {
        return userDefinedCheck4;
    }

    public void setUserDefinedCheck4(Boolean userDefinedCheck4) {
        this.userDefinedCheck4 = userDefinedCheck4;
    }

    public Boolean getUserDefinedCheck5() {
        return userDefinedCheck5;
    }

    public void setUserDefinedCheck5(Boolean userDefinedCheck5) {
        this.userDefinedCheck5 = userDefinedCheck5;
    }

    public LocalDate getUserDefinedDate1() {
        return userDefinedDate1;
    }

    public void setUserDefinedDate1(LocalDate userDefinedDate1) {
        this.userDefinedDate1 = userDefinedDate1;
    }

    public LocalDate getUserDefinedDate2() {
        return userDefinedDate2;
    }

    public void setUserDefinedDate2(LocalDate userDefinedDate2) {
        this.userDefinedDate2 = userDefinedDate2;
    }

    public LocalDate getUserDefinedDate3() {
        return userDefinedDate3;
    }

    public void setUserDefinedDate3(LocalDate userDefinedDate3) {
        this.userDefinedDate3 = userDefinedDate3;
    }

    public LocalDate getUserDefinedDate4() {
        return userDefinedDate4;
    }

    public void setUserDefinedDate4(LocalDate userDefinedDate4) {
        this.userDefinedDate4 = userDefinedDate4;
    }

    public LocalDate getUserDefinedDate5() {
        return userDefinedDate5;
    }

    public void setUserDefinedDate5(LocalDate userDefinedDate5) {
        this.userDefinedDate5 = userDefinedDate5;
    }

    public Boolean getOrgSku() {
        return isOrgSku;
    }

    public void setOrgSku(Boolean orgSku) {
        isOrgSku = orgSku;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
// More getters and setters omitted for brevity
}

