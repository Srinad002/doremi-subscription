package com.geektrust.backend.entity;

/**
 * User who can subscribe plans from available categories
 */
public class User {
    /**
     * Name of the User
     */
    private String name;
    /**
     * User has subscriptionDetails
     */
    private SubscriptionDetails subscriptionDetails;

    public User(String name, SubscriptionDetails subscriptionDetails) {
        this.name = name;
        this.subscriptionDetails = subscriptionDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubscriptionDetails getSubscriptionDetails() {
        return subscriptionDetails;
    }

    public void setSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
        this.subscriptionDetails = subscriptionDetails;
    }
}
