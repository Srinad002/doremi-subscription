package com.geektrust.backend.command;

import com.geektrust.backend.exception.InvalidDateException;
import com.geektrust.backend.service.SubscriptionDetailsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@DisplayName("StartSubscriptionCommandTest")
@ExtendWith(MockitoExtension.class)
public class StartSubscriptionCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @InjectMocks
    StartSubscriptionCommand startSubscriptionCommand;

    @Mock
    SubscriptionDetailsService subscriptionDetailsService;

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
    public void executeSuccessful() {
        startSubscriptionCommand.execute(Arrays.asList("START_SUBSCRIPTION", "12-12-2021"));
        Assertions.assertEquals("", outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).startSubscription("12-12-2021");
    }

    @DisplayName("Execute throws exception")
    @Test
    public void executeThrowsException() {
        String expected = "INVALID_DATE";
        doThrow(new InvalidDateException(expected)).when(subscriptionDetailsService).startSubscription("12-14-20010");
        startSubscriptionCommand.execute(Arrays.asList("START_SUBSCRIPTION", "12-14-20010"));
        Assertions.assertEquals(expected, outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).startSubscription("12-14-20010");
    }

}
