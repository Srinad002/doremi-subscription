package com.geektrust.backend.entity;

import java.util.Arrays;

/**
 * Enum of all Plans
 */
public enum Plan {
    FREE,
    PERSONAL,
    PREMIUM;

    /**
     * Gets the plan for given planName
     *
     * @param planName
     * @return Plan for given planName
     */
    public static Plan getPlan(String planName) {
        return Arrays.stream(Plan.values())
                .filter(plan -> planName.equalsIgnoreCase(plan.name()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Plan not Found"));
    }
}
