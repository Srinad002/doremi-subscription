package com.geektrust.backend.repository;

/**
 * Contract for repositories
 */
public interface IRepository {

    public void loadData();

    public Integer getValue(String key);

    public void insertData(String key, Integer value);

    public long size();
}
