package com.geektrust.backend;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@DisplayName("AppTest")
class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("Integration Test")
    @Test
    public void run_1() {
        String expected = "RENEWAL_REMINDER MUSIC 10-03-2022\n"
                + "RENEWAL_REMINDER VIDEO 10-05-2022\n"
                + "RENEWAL_REMINDER PODCAST 10-03-2022\n"
                + "RENEWAL_AMOUNT 750";

        App.run("input.txt");

        Assertions.assertEquals(expected, outputStream.toString().trim());

    }

    @DisplayName("Integration Test_2")
    @Test
    public void run_2() {
        String expected = "ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND\n"
                + "SUBSCRIPTIONS_NOT_FOUND";
        App.run("input_2.txt");
        Assertions.assertEquals(expected, outputStream.toString().trim());
    }

}
