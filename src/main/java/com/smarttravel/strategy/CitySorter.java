package com.smarttravel.strategy;

import com.smarttravel.model.City;
import java.util.List;

public class CitySorter {

    private CitySortStrategy strategy;

    public void setStrategy(CitySortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<City> sort(List<City> cities) {
        if (strategy == null) {
            throw new IllegalStateException("Sorting strategy is not selected.");
        }

        return strategy.sort(cities);
    }
}