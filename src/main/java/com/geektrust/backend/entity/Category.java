package com.geektrust.backend.entity;

import java.util.Arrays;

/**
 * Enum for all categories
 */
public enum Category {
    MUSIC,
    VIDEO,
    PODCAST;

    /**
     * Gets the category of mentioned categoryName
     *
     * @param categoryName
     * @return Category
     */
    public static Category getCategory(String categoryName) {
        return  Arrays.stream(Category.values())
                .filter( category -> categoryName.equalsIgnoreCase(category.name()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    /**
     * To find whether provided categoryName present as Category
     *
     * @param categoryName
     * @return true/false
     */
    public static boolean isPresent(String categoryName) {
        return Arrays.stream(Category.values())
                .anyMatch( category -> categoryName.equalsIgnoreCase(category.name()));
    }
}
