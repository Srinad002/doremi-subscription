package com.geektrust.backend.command;

import com.geektrust.backend.entity.Category;
import com.geektrust.backend.entity.Plan;
import com.geektrust.backend.exception.AddSubscriptionException;
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

@DisplayName("AddSubscriptionCommandTest")
@ExtendWith(MockitoExtension.class)
public class AddSubscriptionCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Mock
    SubscriptionDetailsService subscriptionDetailsService;

    @InjectMocks
    AddSubscriptionCommand addSubscriptionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("Execute successful")
    @Test
    public void execute() {
        String expected = "";
        addSubscriptionCommand.execute(Arrays.asList("ADD_SUBSCRIPTION", "MUSIC", "PREMIUM"));
        Assertions.assertEquals(expected, outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).addSubscription(Category.valueOf("MUSIC"), Plan.valueOf("PREMIUM"));
    }

    @DisplayName("Execute throws invalid date exception")
    @Test
    public void executeThrowsInvalidDateException() {
        String expected = "ADD_SUBSCRIPTION_FAILED INVALID_DATE";
        doThrow(new AddSubscriptionException(expected)).when(subscriptionDetailsService).addSubscription(Category.valueOf("VIDEO"), Plan.valueOf("PERSONAL"));
        addSubscriptionCommand.execute(Arrays.asList("ADD_SUBSCRIPTION", "VIDEO", "PERSONAL"));
        Assertions.assertEquals(expected, outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).addSubscription(Category.valueOf("VIDEO"), Plan.valueOf("PERSONAL"));
    }



}
