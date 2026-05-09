package com.smarttravel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.smarttravel.gui.WeatherPanel;
import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;

public class Main {

    public static void main(String[] args) {

        // Test için örnek şehirler
        // İleride Kişi 1 bunu JSON'dan okuyacak, şimdilik manuel ekliyoruz
        List<City> cities = new ArrayList<>();
        cities.add(new City("Ankara",    5700000, 2516.0, 22.5, WeatherState.CLOUDY));
        cities.add(new City("Istanbul",  15000000, 5461.0, 25.3, WeatherState.SUNNY));
        cities.add(new City("Izmir",     4400000, 2824.0, 28.1, WeatherState.SUNNY));
        cities.add(new City("Bursa",     3100000, 1036.0, 20.4, WeatherState.RAINY));
        cities.add(new City("Antalya",   2500000, 1417.0, 32.0, WeatherState.SUNNY));
        cities.add(new City("Adana",     2200000, 1254.0, 35.2, WeatherState.CLOUDY));
        cities.add(new City("Konya",     2200000, 2461.0, 18.7, WeatherState.SNOWY));
        cities.add(new City("Gaziantep", 2100000, 863.0,  26.4, WeatherState.RAINY));

        // GUI'yi EDT thread'inde başlat
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smart Travel Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 400);
            frame.setLocationRelativeTo(null);

            // Weather panelini ekle
            WeatherPanel weatherPanel = new WeatherPanel(cities);
            frame.add(weatherPanel);

            frame.setVisible(true);
        });
    }
}