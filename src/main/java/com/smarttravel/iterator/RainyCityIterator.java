package com.smarttravel.iterator;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;
import java.util.List;

public class RainyCityIterator extends WeatherCityIterator {
    public RainyCityIterator(List<City> cities) {
        super(cities, WeatherState.RAINY);
    }
}