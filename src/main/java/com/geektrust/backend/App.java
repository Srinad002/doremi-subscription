package com.geektrust.backend;

import com.geektrust.backend.appconfig.ApplicationConfig;
import com.geektrust.backend.command.CommandInvoker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * App where the application starts from main method
 */
public class App {

    /**
     * Takes the main fileName as args and executes the project
     *
     * @param args
     */
    public static void main(String[] args) {
        String fileName;
        if (args.length != 0) {
            fileName = args[0];
            run(fileName);
        }
    }

    /**
     * Runs the project after by loading commands from file provided
     *
     * @param fileName
     */
    public static void run(String fileName) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.loadData();

        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();

        try {
            //read file
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                //split the data with empty space
                List<String> data = Arrays.asList(line.split(" "));
                commandInvoker.execute(data.get(0), data);
                // read next line
                line = br.readLine();
            }
            //close the buffered reader
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
