package com.geektrust.backend.command;

import java.util.List;

/**
 * Realizes by all commands
 */
public interface ICommand {

    public void execute(List<String> data);
}
