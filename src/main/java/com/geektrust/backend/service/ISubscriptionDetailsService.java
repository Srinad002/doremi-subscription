package com.geektrust.backend.service;

import com.geektrust.backend.entity.Category;
import com.geektrust.backend.entity.Plan;
import com.geektrust.backend.entity.TopUp;
import com.geektrust.backend.exception.AddSubscriptionException;
import com.geektrust.backend.exception.AddTopUpException;

/**
 * Contract for SubscriptionDetailsService
 */
public interface ISubscriptionDetailsService {

    public void startSubscription(String startDate);

    public void addSubscription(Category category, Plan plan) throws AddSubscriptionException;

    public void addTopUp(TopUp devicesUpTo, Integer noOfMonths) throws AddTopUpException;

    public String getRenewalDetails();
}
