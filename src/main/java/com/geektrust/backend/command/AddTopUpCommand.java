package com.geektrust.backend.command;

import com.geektrust.backend.entity.TopUp;
import com.geektrust.backend.service.SubscriptionDetailsService;

import java.util.List;

/**
 * Adds topUp to subscription details
 */
public class AddTopUpCommand implements  ICommand {

    private SubscriptionDetailsService subscriptionDetailsService;

    public AddTopUpCommand(SubscriptionDetailsService subscriptionDetailsService) {
        this.subscriptionDetailsService = subscriptionDetailsService;
    }

    /**
     * Executes commands with data provided in format [ADD_TOPUP, FOUR_DEVICE, 3]
     * @param data
     */
    @Override
    public void execute(List<String> data) {
        try {
            TopUp topUp = TopUp.valueOf(data.get(1));
            Integer noOfMonths = Integer.parseInt(data.get(2));
            subscriptionDetailsService.addTopUp(topUp, noOfMonths);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
