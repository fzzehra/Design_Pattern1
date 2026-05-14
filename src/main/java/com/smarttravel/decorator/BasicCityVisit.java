package com.smarttravel.decorator;

import com.smarttravel.model.City;

public class BasicCityVisit implements CityVisitComponent {

    private final City city;

    public BasicCityVisit(City city) {
        this.city = city;
    }

    @Override
    public String getDescription() {
        return city.getName();
    }

    @Override
    public double getCost() {
        return 0;
    }

    @Override
    public double getRequiredTime() {
        return 0;
    }
}