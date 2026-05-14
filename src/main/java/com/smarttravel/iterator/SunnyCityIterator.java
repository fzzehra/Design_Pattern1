package com.smarttravel.iterator;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;
import java.util.List;

public class SunnyCityIterator extends WeatherCityIterator {
    public SunnyCityIterator(List<City> cities) {
        super(cities, WeatherState.SUNNY);
    }
}