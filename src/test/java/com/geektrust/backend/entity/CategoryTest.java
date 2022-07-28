package com.geektrust.backend.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CategoryTest")
public class CategoryTest {

    @DisplayName("getCategory")
    @Test
    public void getCategory() {
        Assertions.assertDoesNotThrow(()->{
            Category.getCategory("MUSIC");
        });
        Assertions.assertEquals(Category.MUSIC, Category.getCategory("MUSIC"));
    }

    @DisplayName("isCategoryPresentAssertTrue")
    @Test
    public void isCategoryPresentAssertTrue() {
        Assertions.assertTrue(Category.isPresent("VIDEO"));
    }

    @DisplayName("isCategoryPresentAssertFalse")
    @Test
    public void isCategoryPresentAssertFalse() {
        Assertions.assertFalse(Category.isPresent("CHAT"));
    }

}
