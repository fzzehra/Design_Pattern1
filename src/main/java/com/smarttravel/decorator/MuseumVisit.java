package com.smarttravel.decorator;

public class MuseumVisit extends CityVisitDecorator {

    public MuseumVisit(CityVisitComponent cityVisit) {
        super(cityVisit);
    }

    @Override
    public String getDescription() {
        return cityVisit.getDescription() + " + Museum Visit";
    }

    @Override
    public double getCost() {
        return cityVisit.getCost() + 100;
    }

    @Override
    public double getRequiredTime() {
        return cityVisit.getRequiredTime() + 2;
    }
}