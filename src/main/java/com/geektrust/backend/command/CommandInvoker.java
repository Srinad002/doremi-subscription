package com.geektrust.backend.command;

import com.geektrust.backend.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registers and executes commands
 */
public class CommandInvoker {

    /**
     * Map of all registered commands
     */
    private final Map<String, ICommand> commandMap;

    public CommandInvoker() {
        commandMap = new HashMap<>();
    }

    /**
     * Registers commands by inserting them into commandMap
     *
     * @param commandName
     * @param command
     */
    public void registerCommand(String commandName, ICommand command) {
        commandMap.put(commandName, command);
    }

    /**
     * Fetches the command entity from commandMap
     *
     * @param commandName
     * @return Command entity
     */
    private ICommand getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    /**
     * Executes the command with list of data by fetching command entity from commandMap
     *
     * @param commandName
     * @param data
     */
    public void execute(String commandName, List<String> data) {
        ICommand command = getCommand(commandName);
        if (command == null) {
            throw new CommandNotFoundException("Command not found : " + commandName);
        }
        command.execute(data);
    }

}
