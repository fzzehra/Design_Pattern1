package com.smarttravel.iterator;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;
import java.util.List;

public class SnowyCityIterator extends WeatherCityIterator {
    public SnowyCityIterator(List<City> cities) {
        super(cities, WeatherState.SNOWY);
    }
}