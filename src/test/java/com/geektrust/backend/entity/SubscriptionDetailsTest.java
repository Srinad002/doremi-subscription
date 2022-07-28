package com.geektrust.backend.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@DisplayName("SubscriptionDetailsTest")
@ExtendWith(MockitoExtension.class)
public class SubscriptionDetailsTest {

    @InjectMocks
    SubscriptionDetails subscriptionDetails;

    @DisplayName("isSubscriptionsEmpty")
    @Test
    public void isSubscriptionsEmpty() {
        subscriptionDetails = new SubscriptionDetails(LocalDate.of(2022, 02, 20));
        Assertions.assertTrue(subscriptionDetails.isSubscriptionsEmpty());
    }

    @DisplayName("SubscriptionsNotEmpty")
    @Test
    public void isSubscriptionsNotEmpty() {
        subscriptionDetails = new SubscriptionDetails(LocalDate.of(2022, 02, 20));
        subscriptionDetails.addCategory(Category.MUSIC);
        Assertions.assertFalse(subscriptionDetails.isSubscriptionsEmpty());
    }

    @DisplayName("updateRenewalDetails")
    @Test
    public void updateRenewalDetails() {
        String expected = "RENEWAL_REMINDER MUSIC 20-02-2022";
        subscriptionDetails = new SubscriptionDetails(LocalDate.of(2022, 02, 20));
        subscriptionDetails.updateRenewalDetails(expected);
        Assertions.assertEquals(expected, subscriptionDetails.getRenewalReminder());
    }

    @DisplayName("isCategoryExists")
    @Test
    public void isCategoryExists() {
        subscriptionDetails = new SubscriptionDetails(LocalDate.of(2022, 02, 20));
        subscriptionDetails.addCategory(Category.PODCAST);
        subscriptionDetails.addCategory(Category.MUSIC);

        Assertions.assertTrue(subscriptionDetails.isCategoryExists(Category.MUSIC));
    }
}
