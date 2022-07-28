package com.geektrust.backend.command;

import com.geektrust.backend.service.SubscriptionDetailsService;

import java.util.List;

/**
 * Command used to start subscription
 */
public class StartSubscriptionCommand implements ICommand {

    /**
     * Used to call service methods
     */
    private SubscriptionDetailsService subscriptionDetailsService;

    /**
     * Initializes subscriptionDetailsService
     *
     * @param subscriptionDetailsService
     */
    public StartSubscriptionCommand(SubscriptionDetailsService subscriptionDetailsService) {
        this.subscriptionDetailsService = subscriptionDetailsService;
    }

    /**
     * Takes the input data as [START_SUBSCRIPTION, DD-MM-YYYY] and starts subscription
     *
     * @param data
     */
    @Override
    public void execute(List<String> data) {
        try {
            subscriptionDetailsService.startSubscription(data.get(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
