package com.smarttravel.iterator;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;
import java.util.List;

public class CloudyCityIterator extends WeatherCityIterator {
    public CloudyCityIterator(List<City> cities) {
        super(cities, WeatherState.CLOUDY);
    }
}