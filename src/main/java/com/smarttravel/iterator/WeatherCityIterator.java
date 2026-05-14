package com.smarttravel.iterator;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;

import java.util.List;

public abstract class WeatherCityIterator implements CityIterator {

    protected List<City> cities;
    protected int position = 0;
    protected WeatherState targetWeather;

    public WeatherCityIterator(List<City> cities, WeatherState targetWeather) {
        this.cities = cities;
        this.targetWeather = targetWeather;
    }

    @Override
    public boolean hasNext() {
        while (position < cities.size()) {
            if (cities.get(position).getCurrentWeatherState() == targetWeather) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public City next() {
        if (hasNext()) {
            return cities.get(position++);
        }
        return null;
    }
}