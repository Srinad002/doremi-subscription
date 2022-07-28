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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static com.geektrust.backend.constants.DefaultMessage.*;

/**
 * Perform subscription related services
 */
public class SubscriptionDetailsService implements ISubscriptionDetailsService {

    /**
     * can access price data of all categories plans
     */
    private PriceData priceData;
    /**
     * Subscription details entity
     */
    public SubscriptionDetails subscriptionDetails;
    /**
     * can access time duration of all plans
     */
    private PlanDuration planDuration;

    /**
     * Initializes priceData and planDuration
     *
     * @param priceData
     * @param planDuration
     */
    public SubscriptionDetailsService(PriceData priceData, PlanDuration planDuration) {
        this.priceData = priceData;
        this.planDuration = planDuration;
    }

    /**
     * Starts the subscription with provided date
     *
     * @param startDate
     * @throws InvalidDateException if provided date is not valid
     */
    @Override
    public void startSubscription(String startDate) throws InvalidDateException {
        LocalDate localDate = parseDate(startDate);
        //Starting the subscription by initializing subscriptionDetails
        subscriptionDetails = new SubscriptionDetails(localDate);
    }

    /**
     * Parse the date String to LocalDate
     *
     * @param date as string in pattern "dd-MM-uuuu"
     * @return date as LocalDate
     * @throws InvalidDateException if given date string is not valid
     */
    public LocalDate parseDate(String date) throws InvalidDateException {
        LocalDate localDate;
        try {
            //parse with DateTimeFormatter with resolverStyle Strict
            localDate = LocalDate.parse(date,
                    DateTimeFormatter.ofPattern(dateFormat).withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            throw new InvalidDateException(invalidDateExceptionMessage);
        }
        return localDate;
    }

    /**
     * Adds subscription to subscriptionDetails entity and updates renewal amount and renewal details
     *
     * @param category
     * @param plan
     * @throws AddSubscriptionException if subscriptionDetails is null or duplicate category used
     */
    @Override
    public void addSubscription(Category category, Plan plan) throws AddSubscriptionException {
        if (subscriptionDetails == null) {
            throw new AddSubscriptionException(addSubscriptionExceptionMessageForInvalidDate);
        }
        if (subscriptionDetails.isCategoryExists(category)) {
            throw new AddSubscriptionException(addSubscriptionExceptionMessageForDuplicateCategory);
        }
        subscriptionDetails.addCategory(category);
        String prefix = category.name();
        String suffix = plan.name();
        updateRenewalAmount(prefix, suffix, null);
        updateRenewalDetails(prefix, suffix);
    }

    /**
     * Updates renewal details of subscriptionDetails with renewalReminderDate
     *
     * @param prefix
     * @param suffix
     */
    public void updateRenewalDetails(String prefix, String suffix) {
        int months = planDuration.getValue(suffix);
        LocalDate renewalReminderDate = subscriptionDetails.getStartDate().plusMonths(months)
                .minusDays(notifiedBeforeDays);
        String message = renewalReminderPrefix + " " + prefix + " " + renewalReminderDate.format(
                DateTimeFormatter.ofPattern(dateFormat));
        subscriptionDetails.updateRenewalDetails(message);
    }

    /**
     * Updates renewal amount by calculating the current amount
     *
     * @param prefix
     * @param suffix
     * @param noOfMonths
     */
    public void updateRenewalAmount(String prefix, String suffix, Integer noOfMonths) {
        Integer amount = subscriptionDetails.getRenewalAmount();
        amount += calculateCurrentAmount(prefix, suffix, noOfMonths);
        subscriptionDetails.setRenewalAmount(amount);
    }

    /**
     * Calculates the current amount with noOfMonths if it is topUp
     *
     * @param prefix
     * @param suffix
     * @param noOfMonths
     * @return currentAmount
     */
    public Integer calculateCurrentAmount(String prefix, String suffix, Integer noOfMonths) {
        String key = keyFormat(prefix, suffix);
        Integer currentAmount = priceData.getValue(key);
        // if topUp is added for k months then amount updated by multiplying with k
        if (TopUp.isPresent(suffix) && noOfMonths != null) {
            currentAmount *= noOfMonths;
        }
        return currentAmount;
    }

    /**
     * Format for searching the price data
     *
     * @param prefix
     * @param suffix
     * @return key
     */
    public String keyFormat(String prefix, String suffix) {
        return prefix + "_" + suffix;
    }

    /**
     * Adds topUp and updates renewal amount
     *
     * @param devicesUpTo
     * @param noOfMonths
     * @throws AddTopUpException if no subscriptions found or duplicate topUp is added
     */
    @Override
    public void addTopUp(TopUp devicesUpTo, Integer noOfMonths) throws AddTopUpException {
        if (subscriptionDetails == null) {
            throw new AddTopUpException(addTopUpExceptionMessageForInvalidDate);
        }
        if (subscriptionDetails.isSubscriptionsEmpty()) {
            throw new AddTopUpException(addTopUpExceptionMessageForNoSubscriptionsFound);
        }
        if (subscriptionDetails.isTopUpAdded()) {
            throw new AddTopUpException(addTopUpExceptionMessageForDuplicateTopUp);
        }
        subscriptionDetails.setTopUpAdded(true);
        updateRenewalAmount(topUp, devicesUpTo.name(), noOfMonths);
    }

    /**
     * Prints the renewal details
     */
    @Override
    public String getRenewalDetails() {
        if (subscriptionDetails == null) {
            return subscriptionsNotFound;
        }
        if (subscriptionDetails.isSubscriptionsEmpty()) {
            return subscriptionsNotFound;
        }
        String renewalAmountMessage = renewalAmountPrefix + " " + subscriptionDetails.getRenewalAmount();
        subscriptionDetails.updateRenewalDetails(renewalAmountMessage);
        return subscriptionDetails.getRenewalReminder();
    }

}
