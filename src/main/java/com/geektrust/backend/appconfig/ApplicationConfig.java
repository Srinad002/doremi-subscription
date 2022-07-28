package com.geektrust.backend.appconfig;

import com.geektrust.backend.command.*;
import com.geektrust.backend.repository.IRepository;
import com.geektrust.backend.repository.PlanDuration;
import com.geektrust.backend.repository.PriceData;
import com.geektrust.backend.service.SubscriptionDetailsService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Initializes all Repositories, Services required for running project
 */
public class ApplicationConfig {

    private final IRepository priceData = new PriceData();
    private final IRepository planDuration = new PlanDuration();
    private final SubscriptionDetailsService subscriptionDetailsService = new SubscriptionDetailsService((PriceData) priceData, (PlanDuration) planDuration);
    private final StartSubscriptionCommand startSubscriptionCommand = new StartSubscriptionCommand(subscriptionDetailsService);
    private final AddSubscriptionCommand addSubscriptionCommand = new AddSubscriptionCommand(subscriptionDetailsService);
    private final AddTopUpCommand addTopUpCommand = new AddTopUpCommand(subscriptionDetailsService);
    private final PrintRenewalDetailsCommand printRenewalDetailsCommand = new PrintRenewalDetailsCommand(subscriptionDetailsService);

    CommandInvoker commandInvoker = new CommandInvoker();

    /**
     * Registers all commands
     * @return commandInvoker
     */
    public CommandInvoker getCommandInvoker() {
        commandInvoker.registerCommand("START_SUBSCRIPTION", startSubscriptionCommand);
        commandInvoker.registerCommand("ADD_SUBSCRIPTION", addSubscriptionCommand);
        commandInvoker.registerCommand("ADD_TOPUP", addTopUpCommand);
        commandInvoker.registerCommand("PRINT_RENEWAL_DETAILS", printRenewalDetailsCommand);
        return commandInvoker;
    }

    /**
     * Loads data in repos
     */
    public void loadData() {
        priceData.loadData();
        planDuration.loadData();
    }

    public IRepository getPriceData() {
        return priceData;
    }

    public IRepository getPlanDuration() {
        return planDuration;
    }


}
