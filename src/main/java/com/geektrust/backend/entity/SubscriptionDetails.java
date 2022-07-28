package com.geektrust.backend.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Subscription details of a User
 */
public class SubscriptionDetails {

    /**
     * Set of subscribed categories
     */
    private Set<Category> subscribedCategories;

    /**
     * Start date of subscriptions
     */
    private LocalDate startDate;

    /**
     * Renewal reminder of subscriptions
     */
    private String renewalReminder = "";

    /**
     * Boolean to find whether topUp added
     */
    private boolean isTopUpAdded = false;

    /**
     * Renewal amount required to renewal the subscriptions
     */
    private Integer renewalAmount = 0;


    /**
     * Initializes subscribedCategories and startDate
     * @param startDate
     */
    public SubscriptionDetails(LocalDate startDate) {
        this.startDate = startDate;
        subscribedCategories = new HashSet<>();
    }


    /**
     * Checks whether any subscribed categories present or not
     * @return true/false
     */
    public boolean isSubscriptionsEmpty() {
        return subscribedCategories.isEmpty();
    }


    /**
     * Checks provided category present in subscribedCategories
     * @param category
     * @return boolean true/false
     */
    public boolean isCategoryExists(Category category) {
        return subscribedCategories.contains(category);
    }


    /**
     * Adds category to subscribedCategories
     * @param category
     */
    public void addCategory(Category category) {
        subscribedCategories.add(category);
    }


    /**
     * updates renewal details with given message by adding new line if it is not empty
     * @param message
     */
    public void updateRenewalDetails(String message) {
        if(!renewalReminder.equals("")) {
            renewalReminder += "\n";
        }
        renewalReminder += message;
    }

    public Set<Category> getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(Set<Category> subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getRenewalReminder() {
        return renewalReminder;
    }

    public void setRenewalReminder(String renewalReminder) {
        this.renewalReminder = renewalReminder;
    }

    public boolean isTopUpAdded() {
        return isTopUpAdded;
    }

    public void setTopUpAdded(boolean topUpAdded) {
        isTopUpAdded = topUpAdded;
    }

    public Integer getRenewalAmount() {
        return renewalAmount;
    }

    public void setRenewalAmount(Integer renewalAmount) {
        this.renewalAmount = renewalAmount;
    }

}
