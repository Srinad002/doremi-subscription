package com.geektrust.backend.command;

import com.geektrust.backend.service.SubscriptionDetailsService;

import java.util.List;

/**
 * Command to print the renewal details
 */
public class PrintRenewalDetailsCommand implements ICommand {

    private SubscriptionDetailsService subscriptionDetailsService;

    public PrintRenewalDetailsCommand(SubscriptionDetailsService subscriptionDetailsService) {
        this.subscriptionDetailsService = subscriptionDetailsService;
    }

    /**
     * Executes command with data in format [PRINT_RENEWAL_DETAILS]
     *
     * @param data
     */
    @Override
    public void execute(List<String> data) {
        String output = subscriptionDetailsService.getRenewalDetails();
        System.out.println(output);
    }
}
