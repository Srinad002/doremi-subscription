package com.geektrust.backend.command;

import com.geektrust.backend.entity.Category;
import com.geektrust.backend.entity.Plan;
import com.geektrust.backend.service.SubscriptionDetailsService;

import java.util.List;

/**
 * Command used for adding subscription
 */
public class AddSubscriptionCommand implements ICommand {

    private SubscriptionDetailsService subscriptionDetailsService;

    public AddSubscriptionCommand(SubscriptionDetailsService subscriptionDetailsService) {
        this.subscriptionDetailsService = subscriptionDetailsService;
    }

    /**
     * executes command with data in format [ADD_SUBSCRIPTION, MUSIC, PREMIUM]
     * @param data
     */
    @Override
    public void execute(List<String> data) {
        try {
            Category category = Category.valueOf(data.get(1));
            Plan plan = Plan.valueOf(data.get(2));
            subscriptionDetailsService.addSubscription(category, plan);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
