package com.smarttravel.strategy;

import com.smarttravel.model.City;
import java.util.List;

public interface CitySortStrategy {
    List<City> sort(List<City> cities);
}