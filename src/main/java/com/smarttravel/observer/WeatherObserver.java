package com.smarttravel.observer;

import java.util.List;

import com.smarttravel.model.City;

public interface WeatherObserver {
    void onWeatherUpdated(List<City> cities);
}