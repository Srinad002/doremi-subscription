package com.geektrust.backend.command;

import com.geektrust.backend.entity.SubscriptionDetails;
import com.geektrust.backend.service.SubscriptionDetailsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@DisplayName("PrintRenewalDetailsCommandTest")
@ExtendWith(MockitoExtension.class)
public class PrintRenewalDetailsCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Mock
    private SubscriptionDetailsService subscriptionDetailsService;

    @InjectMocks
    private PrintRenewalDetailsCommand printRenewalDetailsCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("Execute successful")
    @Test
    public void execute() {
        String expected = "RENEWAL_REMINDER MUSIC 10-03-2022\n"
                + "RENEWAL_REMINDER VIDEO 10-05-2022\n"
                + "RENEWAL_REMINDER PODCAST 10-03-2022\n"
                + "RENEWAL_AMOUNT 750";
        when(subscriptionDetailsService.getRenewalDetails()).thenReturn(expected);

        printRenewalDetailsCommand.execute(Arrays.asList("PRINT_RENEWAL_DETAILS"));

        Assertions.assertEquals(expected, outputStream.toString().trim());
    }
}

