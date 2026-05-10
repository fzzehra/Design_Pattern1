package com.smarttravel.composite;

public class Activity implements TripComponent {

    private final String name;
    private final double cost;
    private final double requiredTime;

    public Activity(String name, double cost, double requiredTime) {
        this.name = name;
        this.cost = cost;
        this.requiredTime = requiredTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double getRequiredTime() {
        return requiredTime;
    }

    @Override
    public String toString() {
        return name + " | Cost: " + cost + " TL | Time: " + requiredTime + " h";
    }
}