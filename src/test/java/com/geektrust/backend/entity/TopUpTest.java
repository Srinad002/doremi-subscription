package com.geektrust.backend.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TopUpTest")
public class TopUpTest {

    @DisplayName("getTopUp")
    @Test
    public void getTopUp() {
        Assertions.assertDoesNotThrow(()->{
            TopUp.getTopUp("FOUR_DEVICE");
        });
        Assertions.assertEquals(TopUp.TEN_DEVICE, TopUp.getTopUp("TEN_DEVICE"));
    }

    @DisplayName("isTopUpPresentAssertTrue")
    @Test
    public void isTopUpPresentAssertTrue() {
        Assertions.assertTrue(TopUp.isPresent("FOUR_DEVICE"));
    }

    @DisplayName("isTopUpPresentAssertFalse")
    @Test
    public void isTopUpPresentAssertFalse() {
        Assertions.assertFalse(TopUp.isPresent("FiVE_DEVICE"));
    }


}
