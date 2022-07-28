package com.geektrust.backend.service;

import com.geektrust.backend.entity.Category;
import com.geektrust.backend.entity.Plan;
import com.geektrust.backend.entity.SubscriptionDetails;
import com.geektrust.backend.entity.TopUp;
import com.geektrust.backend.exception.AddSubscriptionException;
import com.geektrust.backend.exception.AddTopUpException;
import com.geektrust.backend.exception.InvalidDateException;
import com.geektrust.backend.repository.PlanDuration;
import com.geektrust.backend.repository.PriceData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@DisplayName("SubscriptionDetailsServiceTest")
@ExtendWith(MockitoExtension.class)
public class SubscriptionDetailsServiceTest {

    @Mock
    private PriceData priceData;

    @Mock
    private PlanDuration planDuration;

    @Mock
    private SubscriptionDetails subscriptionDetails;

    @InjectMocks
    SubscriptionDetailsService subscriptionDetailsService;

    @DisplayName("Start subscription successful")
    @Test
    public void startSubscription() {
        Assertions.assertDoesNotThrow(() -> {
            subscriptionDetailsService.startSubscription("29-02-2020");
        });
    }

    @DisplayName("Start subscription throws exception")
    @Test
    public void startSubscriptionThrowsException() {
        Assertions.assertThrows(InvalidDateException.class, () -> {
            subscriptionDetailsService.startSubscription("29-02-2021");
        });
    }

    @DisplayName("Parse date successful")
    @Test
    public void parseDate() {
        LocalDate expected = LocalDate.of(2022, 02, 20);
        LocalDate actual = subscriptionDetailsService.parseDate("20-02-2022");
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @DisplayName("Parse date exception")
    @Test
    public void parseDateException() {
        Assertions.assertThrows(InvalidDateException.class, () -> {
           subscriptionDetailsService.parseDate("29-02-2022");
        });
    }

    @DisplayName("Add subscription successful")
    @Test
    public void addSubscription() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(priceData.getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);

        subscriptionDetailsService.startSubscription("29-02-2020");
        subscriptionDetailsService.addSubscription(Category.PODCAST, Plan.PERSONAL);

        Assertions.assertEquals(100, subscriptionDetailsService.subscriptionDetails.getRenewalAmount());
        Assertions.assertEquals("RENEWAL_REMINDER PODCAST 19-03-2020",
                subscriptionDetailsService.subscriptionDetails.getRenewalReminder());

        verify(priceData, times(1)).getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name());
        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
    }

    @DisplayName("Add two or more subscriptions successful")
    @Test
    public void addTwoOrMoreSubscriptions() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(planDuration.getValue(Plan.PREMIUM.name())).thenReturn(3);
        when(priceData.getValue(Category.MUSIC.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);
        when(priceData.getValue(Category.VIDEO.name() + "_" + Plan.PREMIUM.name())).thenReturn(500);
        when(priceData.getValue(Category.PODCAST.name() + "_" + Plan.PREMIUM.name())).thenReturn(300);

        subscriptionDetailsService.startSubscription("02-02-2022");
        subscriptionDetailsService.addSubscription(Category.MUSIC, Plan.PERSONAL);
        subscriptionDetailsService.addSubscription(Category.VIDEO, Plan.PREMIUM);
        subscriptionDetailsService.addSubscription(Category.PODCAST, Plan.PREMIUM);


        Assertions.assertEquals(900, subscriptionDetailsService.subscriptionDetails.getRenewalAmount());
        Assertions.assertEquals(
                "RENEWAL_REMINDER MUSIC 20-02-2022\nRENEWAL_REMINDER VIDEO 22-04-2022\nRENEWAL_REMINDER PODCAST 22-04-2022",
                subscriptionDetailsService.subscriptionDetails.getRenewalReminder());

        verify(priceData, times(1)).getValue(Category.MUSIC.name() + "_" + Plan.PERSONAL.name());
        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
        verify(priceData, times(1)).getValue(Category.PODCAST.name() + "_" + Plan.PREMIUM.name());
    }

    @DisplayName("Add subscription throws exception for invalid date exception")
    @Test
    public void addSubscriptionThrowsExceptionForInvalidDate() {
        Assertions.assertThrows(AddSubscriptionException.class, () -> {
            subscriptionDetailsService.addSubscription(Category.MUSIC, Plan.PERSONAL);
        });
    }

    @DisplayName("Add subscription throws exception for duplicate category")
    @Test
    public void addSubscriptionThrowsExceptionForDuplicateCategory() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(priceData.getValue(Category.MUSIC.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);

        subscriptionDetailsService.startSubscription("02-02-2022");
        subscriptionDetailsService.addSubscription(Category.MUSIC, Plan.PERSONAL);

        Assertions.assertThrows(AddSubscriptionException.class, () -> {
            subscriptionDetailsService.addSubscription(Category.MUSIC, Plan.PREMIUM);
        });

        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
        verify(priceData, times(1)).getValue(Category.MUSIC.name() + "_" + Plan.PERSONAL.name());

    }

    @DisplayName("updateRenewalDetails")
    @Test
    public void updateRenewalDetails() {
        subscriptionDetailsService.startSubscription("20-02-2022");
        when(planDuration.getValue("FREE")).thenReturn(1);
        String expected = "RENEWAL_REMINDER MUSIC 10-03-2022";
        subscriptionDetailsService.updateRenewalDetails("MUSIC", "FREE");

        Assertions.assertEquals(expected, subscriptionDetailsService.subscriptionDetails.getRenewalReminder());
        verify(planDuration, times(1)).getValue("FREE");
    }

    @DisplayName("updateRenewalAmount")
    @Test
    public void updateRenewalAmount() {
        subscriptionDetailsService.startSubscription("20-02-2022");
        when(subscriptionDetailsService.calculateCurrentAmount("MUSIC", "PERSONAL", null)).thenReturn(100);

        subscriptionDetailsService.updateRenewalAmount("MUSIC", "PERSONAL", null);

        Assertions.assertEquals(100, subscriptionDetailsService.subscriptionDetails.getRenewalAmount());
    }

    @DisplayName("calculateCurrentAmountForCategoryPlan")
    @Test
    public void calculateCurrentAmountForCategoryPlan() {
        when(priceData.getValue("MUSIC_PREMIUM")).thenReturn(250);
        Integer actual = subscriptionDetailsService.calculateCurrentAmount("MUSIC", "PREMIUM", null);
        Assertions.assertEquals(250, actual);
    }

    @DisplayName("calculateCurrentAmountForTopUp")
    @Test
    public void calculateCurrentAmountForTopUp() {
        when(priceData.getValue("TOPUP_FOUR_DEVICE")).thenReturn(50);
        Integer actual = subscriptionDetailsService.calculateCurrentAmount("TOPUP", "FOUR_DEVICE", 2);
        Assertions.assertEquals(100, actual);
    }

    @DisplayName("keyFormat")
    @Test
    public void keyFormat() {
        String expected = "MUSIC_FREE";
        Assertions.assertEquals(expected, subscriptionDetailsService.keyFormat("MUSIC", "FREE"));
    }

    @DisplayName("Add topUp successful")
    @Test
    public void addTopUp() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(priceData.getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);
        when(priceData.getValue("TOPUP_" + TopUp.TEN_DEVICE.name())).thenReturn(100);

        subscriptionDetailsService.startSubscription("29-02-2020");
        subscriptionDetailsService.addSubscription(Category.PODCAST, Plan.PERSONAL);
        subscriptionDetailsService.addTopUp(TopUp.TEN_DEVICE, 2);

        Assertions.assertEquals(300, subscriptionDetailsService.subscriptionDetails.getRenewalAmount());
        Assertions.assertEquals("RENEWAL_REMINDER PODCAST 19-03-2020",
                subscriptionDetailsService.subscriptionDetails.getRenewalReminder());

        verify(priceData, times(1)).getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name());
        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
        verify(priceData, times(1)).getValue("TOPUP_" + TopUp.TEN_DEVICE.name());
    }

    @DisplayName("Add topUp throws exception if no subscriptions found")
    @Test
    public void addTopUpThrowsExceptionForNoSubscriptions() {
        subscriptionDetailsService.startSubscription("29-02-2020");

        Assertions.assertThrows(AddTopUpException.class, () -> {
            subscriptionDetailsService.addTopUp(TopUp.FOUR_DEVICE, 1);
        });
    }

    @DisplayName("Add topUp throws exception if duplicate topUp added")
    @Test
    public void addTopUpThrowsExceptionForDuplicateTopUp() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(priceData.getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);
        when(priceData.getValue("TOPUP_" + TopUp.TEN_DEVICE.name())).thenReturn(100);

        subscriptionDetailsService.startSubscription("29-02-2020");
        subscriptionDetailsService.addSubscription(Category.PODCAST, Plan.PERSONAL);
        subscriptionDetailsService.addTopUp(TopUp.TEN_DEVICE, 2);

        Assertions.assertThrows(AddTopUpException.class, () -> {
            subscriptionDetailsService.addTopUp(TopUp.FOUR_DEVICE, 1);
        });

        Assertions.assertEquals(300, subscriptionDetailsService.subscriptionDetails.getRenewalAmount());
        Assertions.assertEquals("RENEWAL_REMINDER PODCAST 19-03-2020",
                subscriptionDetailsService.subscriptionDetails.getRenewalReminder());

        verify(priceData, times(1)).getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name());
        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
        verify(priceData, times(1)).getValue("TOPUP_" + TopUp.TEN_DEVICE.name());

    }

    @DisplayName("Print renewal details with all details")
    @Test
    public void printRenewalDetails() {
        when(planDuration.getValue(Plan.PERSONAL.name())).thenReturn(1);
        when(priceData.getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name())).thenReturn(100);
        when(priceData.getValue("TOPUP_" + TopUp.TEN_DEVICE.name())).thenReturn(100);

        subscriptionDetailsService.startSubscription("29-02-2020");
        subscriptionDetailsService.addSubscription(Category.PODCAST, Plan.PERSONAL);
        subscriptionDetailsService.addTopUp(TopUp.TEN_DEVICE, 2);

        Assertions.assertEquals("RENEWAL_REMINDER PODCAST 19-03-2020\nRENEWAL_AMOUNT 300",
                subscriptionDetailsService.getRenewalDetails());

        verify(priceData, times(1)).getValue(Category.PODCAST.name() + "_" + Plan.PERSONAL.name());
        verify(planDuration, times(1)).getValue(Plan.PERSONAL.name());
        verify(priceData, times(1)).getValue("TOPUP_" + TopUp.TEN_DEVICE.name());
    }

    @DisplayName("Print renewal details when no subscriptions")
    @Test
    public void printRenewalDetailsWhenNoSubscriptions() {
        subscriptionDetailsService.startSubscription("29-02-2020");

        Assertions.assertEquals("SUBSCRIPTIONS_NOT_FOUND", subscriptionDetailsService.getRenewalDetails());
    }
}
