package com.geektrust.backend.entity;

import java.util.Arrays;

/**
 * Enum for all topUp's
 */
public enum TopUp {
    FOUR_DEVICE,
    TEN_DEVICE;

    /**
     * Gets the topUp for given name
     *
     * @param name
     * @return TopUp of given name
     */
    public static TopUp getTopUp(String name) {
        return Arrays.stream(TopUp.values())
                .filter(topUp -> name.equalsIgnoreCase(topUp.name()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("TopUp not found"));
    }

    /**
     * Find whether given name present as TopUp
     *
     * @param name
     * @return true/false
     */
    public static boolean isPresent(String name) {
        return Arrays.stream(TopUp.values())
                .anyMatch( topUp -> name.equalsIgnoreCase(topUp.name()));
    }
}
