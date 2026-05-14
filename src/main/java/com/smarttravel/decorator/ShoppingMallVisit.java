package com.smarttravel.decorator;

public class ShoppingMallVisit extends CityVisitDecorator {

    public ShoppingMallVisit(CityVisitComponent cityVisit) {
        super(cityVisit);
    }

    @Override
    public String getDescription() {
        return cityVisit.getDescription() + " + Shopping Mall Visit";
    }

    @Override
    public double getCost() {
        return cityVisit.getCost() + 150;
    }

    @Override
    public double getRequiredTime() {
        return cityVisit.getRequiredTime() + 3;
    }
}