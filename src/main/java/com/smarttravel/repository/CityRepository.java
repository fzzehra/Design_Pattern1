package com.smarttravel.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarttravel.model.City;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private static CityRepository instance;
    private List<City> cities;

    private CityRepository() {
        loadCitiesFromJson();
    }

    public static CityRepository getInstance() {
        if (instance == null) {
            instance = new CityRepository();
        }
        return instance;
    }

    private void loadCitiesFromJson() {
        try {
            Gson gson = new Gson();
            Type cityListType = new TypeToken<ArrayList<City>>() {}.getType();

            FileReader reader = new FileReader("cities.json");
            cities = gson.fromJson(reader, cityListType);
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            cities = new ArrayList<>();
        }
    }

    public List<City> getCities() {
        return cities;
    }
} 