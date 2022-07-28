package com.geektrust.backend.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository for priceData
 */
public class PriceData implements IRepository {

    /**
     * Map of priceData
     */
    private final Map<String, Integer> priceData;

    public PriceData() {
        priceData = new HashMap<>();
    }

    /**
     * @return the map of priceData
     */
    public Map<String, Integer> getPriceData() {
        return priceData;
    }

    /**
     * Loads data into repo
     */
    @Override
    public void loadData() {
        insertData("MUSIC_FREE", 0);
        insertData("MUSIC_PERSONAL", 100);
        insertData("MUSIC_PREMIUM", 250);
        insertData("VIDEO_FREE", 0);
        insertData("VIDEO_PERSONAL", 200);
        insertData("VIDEO_PREMIUM", 500);
        insertData("PODCAST_FREE", 0);
        insertData("PODCAST_PERSONAL", 100);
        insertData("PODCAST_PREMIUM", 300);
        insertData("TOPUP_FOUR_DEVICE", 50);
        insertData("TOPUP_TEN_DEVICE", 100);
    }

    /**
     * @param key
     * @return the value of provided key from priceData
     */
    @Override
    public Integer getValue(String key) {
        return priceData.get(key);
    }

    /**
     * Inserts the key, value into priceData
     * @param key
     * @param value
     */
    @Override
    public void insertData(String key, Integer value) {
        priceData.put(key, value);
    }

    /**
     * @return the size of priceData
     */
    @Override
    public long size() {
        return priceData.size();
    }
}
