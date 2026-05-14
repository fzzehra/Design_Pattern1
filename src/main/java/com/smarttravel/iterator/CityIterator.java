package com.smarttravel.iterator;

import com.smarttravel.model.City;

public interface CityIterator {
    boolean hasNext();
    City next();
}