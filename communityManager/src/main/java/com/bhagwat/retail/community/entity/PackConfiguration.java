package com.bhagwat.retail.community.entity;
import com.bhagwat.retail.community.enums.TrackingLevel;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pack_configuration")
public class PackConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_configuration_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level_1", nullable = false)
    private TrackingLevel trackingLevel1;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level_2")
    private TrackingLevel trackingLevel2;

    @Column(name = "tracking_ratio_1_to_2", nullable = false)
    private Integer trackingRatio1To2;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level_3")
    private TrackingLevel trackingLevel3;

    @Column(name = "tracking_ratio_2_to_3")
    private Integer trackingRatio2To3;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level_4")
    private TrackingLevel trackingLevel4;

    @Column(name = "tracking_ratio_3_to_4")
    private Integer trackingRatio3To4;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level_5")
    private TrackingLevel trackingLevel5;

    @Column(name = "tracking_ratio_4_to_5")
    private Integer trackingRatio4To5;

    @Column(name = "lowest_unit_weight", precision = 10, scale = 2)
    private BigDecimal lowestUnitWeight;

    @Column(name = "tag_volume_weight", precision = 10, scale = 2)
    private BigDecimal tagVolumeWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "tagged_at_level")
    private TrackingLevel taggedAtLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "handling_unit_level")
    private TrackingLevel handlingUnitLevel;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrackingLevel getTrackingLevel1() {
        return trackingLevel1;
    }

    public void setTrackingLevel1(TrackingLevel trackingLevel1) {
        this.trackingLevel1 = trackingLevel1;
    }

    public TrackingLevel getTrackingLevel2() {
        return trackingLevel2;
    }

    public void setTrackingLevel2(TrackingLevel trackingLevel2) {
        this.trackingLevel2 = trackingLevel2;
    }

    public Integer getTrackingRatio1To2() {
        return trackingRatio1To2;
    }

    public void setTrackingRatio1To2(Integer trackingRatio1To2) {
        this.trackingRatio1To2 = trackingRatio1To2;
    }

    public TrackingLevel getTrackingLevel3() {
        return trackingLevel3;
    }

    public void setTrackingLevel3(TrackingLevel trackingLevel3) {
        this.trackingLevel3 = trackingLevel3;
    }

    public Integer getTrackingRatio2To3() {
        return trackingRatio2To3;
    }

    public void setTrackingRatio2To3(Integer trackingRatio2To3) {
        this.trackingRatio2To3 = trackingRatio2To3;
    }

    public TrackingLevel getTrackingLevel4() {
        return trackingLevel4;
    }

    public void setTrackingLevel4(TrackingLevel trackingLevel4) {
        this.trackingLevel4 = trackingLevel4;
    }

    public Integer getTrackingRatio3To4() {
        return trackingRatio3To4;
    }

    public void setTrackingRatio3To4(Integer trackingRatio3To4) {
        this.trackingRatio3To4 = trackingRatio3To4;
    }

    public TrackingLevel getTrackingLevel5() {
        return trackingLevel5;
    }

    public void setTrackingLevel5(TrackingLevel trackingLevel5) {
        this.trackingLevel5 = trackingLevel5;
    }

    public Integer getTrackingRatio4To5() {
        return trackingRatio4To5;
    }

    public void setTrackingRatio4To5(Integer trackingRatio4To5) {
        this.trackingRatio4To5 = trackingRatio4To5;
    }

    public BigDecimal getLowestUnitWeight() {
        return lowestUnitWeight;
    }

    public void setLowestUnitWeight(BigDecimal lowestUnitWeight) {
        this.lowestUnitWeight = lowestUnitWeight;
    }

    public BigDecimal getTagVolumeWeight() {
        return tagVolumeWeight;
    }

    public void setTagVolumeWeight(BigDecimal tagVolumeWeight) {
        this.tagVolumeWeight = tagVolumeWeight;
    }

    public TrackingLevel getTaggedAtLevel() {
        return taggedAtLevel;
    }

    public void setTaggedAtLevel(TrackingLevel taggedAtLevel) {
        this.taggedAtLevel = taggedAtLevel;
    }

    public TrackingLevel getHandlingUnitLevel() {
        return handlingUnitLevel;
    }

    public void setHandlingUnitLevel(TrackingLevel handlingUnitLevel) {
        this.handlingUnitLevel = handlingUnitLevel;
    }
}

