package com.smarttravel.decorator;

public class ParkVisit extends CityVisitDecorator {

    public ParkVisit(CityVisitComponent cityVisit) {
        super(cityVisit);
    }

    @Override
    public String getDescription() {
        return cityVisit.getDescription() + " + Park Visit";
    }

    @Override
    public double getCost() {
        return cityVisit.getCost() + 50;
    }

    @Override
    public double getRequiredTime() {
        return cityVisit.getRequiredTime() + 1.5;
    }
}