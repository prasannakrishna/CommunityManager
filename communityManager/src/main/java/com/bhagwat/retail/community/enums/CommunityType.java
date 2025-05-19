package com.bhagwat.retail.community.enums;

public enum CommunityType {
    DEFAULT("NA", "-", "Not Available"),
    LINGUISTIC("LING", "Linguistic", "Communities based on shared language or dialect"),
    RELIGIOUS("REL", "Religious", "Communities centered around a shared religion or faith"),
    ETHNIC("ETH", "Ethnic", "Communities formed based on shared ancestry or cultural heritage"),
    REGIONAL("REG", "Regional", "Communities from a specific geographic or administrative region"),
    SOCIO_CULTURAL("SOC", "Socio-Cultural", "Communities sharing social customs, values, or traditions"),
    GEOGRAPHICAL("GEO", "Geographical", "Communities defined by specific physical or urban geography");

    private final String code;
    private final String name;
    private final String description;

    CommunityType(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

