package com.hand.Dao;

import java.util.List;

import com.hand.Entity.City;

public interface CityDao {
    List<City> findCity(int country_id);
}