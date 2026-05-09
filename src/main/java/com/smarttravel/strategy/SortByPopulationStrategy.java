package com.smarttravel.strategy;

import com.smarttravel.model.City;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByPopulationStrategy implements CitySortStrategy {

    @Override
    public List<City> sort(List<City> cities) {
        return cities.stream()
                .sorted(Comparator.comparingLong(City::getPopulation))
                .collect(Collectors.toList());
    }
}