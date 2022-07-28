package com.geektrust.backend.appconfig;

import com.geektrust.backend.repository.IRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ApplicationConfigTest")
@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @InjectMocks
    ApplicationConfig applicationConfig;

    @DisplayName("loadData for priceData")
    @Test
    public void loadDataForPriceData() {
        applicationConfig.loadData();
        IRepository priceData = applicationConfig.getPriceData();
        Assertions.assertEquals(11, priceData.size());
    }

    @DisplayName("loadData for planDuration")
    @Test
    public void loadDataForPlanDuration() {
        applicationConfig.loadData();
        IRepository planDuration = applicationConfig.getPlanDuration();
        Assertions.assertEquals(4, planDuration.size());
    }
}
