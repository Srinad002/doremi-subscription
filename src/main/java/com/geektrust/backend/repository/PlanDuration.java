package com.geektrust.backend.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository for planDuration
 */
public class PlanDuration implements IRepository {

    /**
     * Map of planDuration
     */
    private final Map<String, Integer> planDurationMap;

    public  PlanDuration() {
        planDurationMap = new HashMap<>();
    }

    /**
     * @return map of planDuration
     */
    public Map<String, Integer> getPlanDurationMap() {
        return planDurationMap;
    }

    /**
     * loads data into repo
     */
    @Override
    public void loadData() {
        insertData("FREE", 1);
        insertData("PERSONAL", 1);
        insertData("PREMIUM", 3);
        insertData("TOPUP", 1);
    }

    /**
     * @param key
     * @return value of provided key from planDurationMap
     */
    @Override
    public Integer getValue(String key) {
        return planDurationMap.get(key);
    }

    /**
     * Inserts the key, value into planDurationMap
     * @param key
     * @param value
     */
    @Override
    public void insertData(String key, Integer value) {
        planDurationMap.put(key, value);
    }

    /**
     * @return size of planDurationMap
     */
    @Override
    public long size() {
        return planDurationMap.size();
    }
}
