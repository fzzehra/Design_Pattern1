package com.smarttravel.decorator;

public class CityCenterVisit extends CityVisitDecorator {

    public CityCenterVisit(CityVisitComponent cityVisit) {
        super(cityVisit);
    }

    @Override
    public String getDescription() {
        return cityVisit.getDescription() + " + City Center Visit";
    }

    @Override
    public double getCost() {
        return cityVisit.getCost() + 80;
    }

    @Override
    public double getRequiredTime() {
        return cityVisit.getRequiredTime() + 2.5;
    }
}