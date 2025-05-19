package com.bhagwat.retail.community.enums;

public enum InterestCategory {
    DEFAULT("NA", "-", "Not Available"),
    FOOD_AND_BEVERAGES("FOOD", "Food & Beverages", "Includes groceries, snacks, beverages, organic foods"),
    FASHION_AND_LIFESTYLE("FASHION", "Fashion & Lifestyle", "Clothing, footwear, and fashion accessories"),
    HEALTH_AND_WELLNESS("HEALTH", "Health & Wellness", "Fitness, supplements, and wellness products"),
    BEAUTY_AND_PERSONAL_CARE("BEAUTY", "Beauty & Personal Care", "Cosmetics, skincare, grooming items"),
    JEWELLERY_AND_ACCESSORIES("JEWEL", "Jewellery & Accessories", "Traditional, fashion, and custom jewellery"),
    TOYS_GAMES_AND_BABY_PRODUCTS("TOYS", "Toys, Games & Baby Products", "Toys, games, and items for babies"),
    BOOKS_AND_STATIONERY("BOOKS", "Books & Stationery", "Books, educational material, and office supplies"),
    HOME_AND_KITCHEN_ESSENTIALS("HOME", "Home & Kitchen Essentials", "Furniture, decor, and kitchen tools"),
    ELECTRONICS_AND_GADGETS("ELEC", "Electronics & Gadgets", "Mobiles, laptops, accessories, gadgets"),
    SPORTS_AND_FITNESS("SPORTS", "Sports & Fitness", "Sports gear, workout equipment, activewear"),
    MEDICINAL_AND_HERBAL_PRODUCTS("MEDIC", "Medicinal & Herbal Products", "Ayurvedic, herbal, and OTC medicine"),
    ORGANIC_AND_NATURAL_PRODUCTS("ORG", "Organic & Natural Products", "Eco-friendly, sustainable goods"),
    ART_AND_CRAFTS("ART", "Art & Crafts", "Handmade items, creative supplies"),
    MUSIC_AND_ENTERTAINMENT("MUSIC", "Music & Entertainment", "Instruments, albums, movie merchandise"),
    PET_SUPPLIES("PET", "Pet Supplies", "Pet food, accessories, and care items"),
    GARDENING_AND_OUTDOOR("GARDEN", "Gardening & Outdoor", "Plants, tools, and outdoor equipment"),
    DIY_AND_HOBBY("DIY", "DIY & Hobby", "Craft kits, hobby tools, and educational kits"),
    AUTOMOTIVE_AND_ACCESSORIES("AUTO", "Automotive & Accessories", "Car/bike accessories, tools, maintenance"),
    LOCAL_SPECIALTIES("LOCAL", "Local Specialties", "Regional delicacies like jaggery, honey, pickles"),
    CUSTOM("CUS", "Custom", "User-defined interest categories");

    private final String code;
    private final String name;
    private final String description;

    InterestCategory(String code, String name, String description) {
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

