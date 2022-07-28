package com.geektrust.backend.command;

import com.geektrust.backend.entity.TopUp;
import com.geektrust.backend.exception.AddTopUpException;
import com.geektrust.backend.service.SubscriptionDetailsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("AddTopUpCommandTest")
@ExtendWith(MockitoExtension.class)
public class AddTopUpCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @InjectMocks
    private AddTopUpCommand addTopUpCommand;

    @Mock
    private SubscriptionDetailsService subscriptionDetailsService;

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
    public void executeSuccessful() {
        addTopUpCommand.execute(Arrays.asList("ADD_TOPUP", "FOUR_DEVICE", "3"));
        Assertions.assertEquals("", outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).addTopUp(TopUp.FOUR_DEVICE, 3);
    }

    @DisplayName("Execute on exception")
    @Test
    public void executeThrowsException() {
        String expected = "ADD_TOPUP_FAILED DUPLICATE_TOPUP";
        doThrow(new AddTopUpException(expected)).when(subscriptionDetailsService).addTopUp(TopUp.FOUR_DEVICE, 3);
        addTopUpCommand.execute(Arrays.asList("ADD_TOPUP", "FOUR_DEVICE", "3"));
        Assertions.assertEquals(expected, outputStream.toString().trim());
        verify(subscriptionDetailsService, times(1)).addTopUp(TopUp.FOUR_DEVICE, 3);
    }

}
