package com.smarttravel.decorator;

public abstract class CityVisitDecorator implements CityVisitComponent {

    protected CityVisitComponent cityVisit;

    public CityVisitDecorator(CityVisitComponent cityVisit) {
        this.cityVisit = cityVisit;
    }

    @Override
    public String getDescription() {
        return cityVisit.getDescription();
    }

    @Override
    public double getCost() {
        return cityVisit.getCost();
    }

    @Override
    public double getRequiredTime() {
        return cityVisit.getRequiredTime();
    }
}