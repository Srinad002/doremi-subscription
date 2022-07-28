package com.geektrust.backend.constants;

/**
 * Used to get default messages to when required
 */
public class DefaultMessage {

    /**
     * renewal notified before days
     */
    public static final int notifiedBeforeDays = 10;

    /**
     * renewal reminder message prefix
     */
    public static final String renewalReminderPrefix = "RENEWAL_REMINDER";

    /**
     * renewal amount message prefix
     */
    public static final String renewalAmountPrefix = "RENEWAL_AMOUNT";

    /**
     * Date formate for parse localDate
     */
    public static final String dateFormat = "dd-MM-uuuu";

    /**
     * Invalid date exception message
     */
    public static final String invalidDateExceptionMessage = "INVALID_DATE";

    /**
     * Add subscription exception message for invalid date
     */
    public static final String addSubscriptionExceptionMessageForInvalidDate = "ADD_SUBSCRIPTION_FAILED INVALID_DATE";

    /**
     * Add subscription exception message for duplicate category
     */
    public static final String addSubscriptionExceptionMessageForDuplicateCategory = "ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY";

    /**
     * Add topUp exception message for no subscriptions found
     */
    public static final String addTopUpExceptionMessageForNoSubscriptionsFound = "ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND";

    /**
     * Add topUp exception message for adding duplicate topUp
     */
    public static final String addTopUpExceptionMessageForDuplicateTopUp = "ADD_TOPUP_FAILED DUPLICATE_TOPUP";

    /**
     *
     */
    public static final String addTopUpExceptionMessageForInvalidDate = "ADD_TOPUP_FAILED INVALID_DATE";

    /**
     * topUp string for suffix
     */
    public static final String topUp = "TOPUP";

    /**
     * message for subscriptions not found
     */
    public static final String subscriptionsNotFound = "SUBSCRIPTIONS_NOT_FOUND";
}
