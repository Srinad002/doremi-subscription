package com.geektrust.backend.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlanTest")
public class PlanTest {

    @DisplayName("getPlan")
    @Test
    public void getPlan(){
        Assertions.assertDoesNotThrow(() -> {
            Plan.getPlan("FREE");
        });
        Assertions.assertEquals(Plan.PREMIUM, Plan.getPlan("PREMIUM"));
    }
}
