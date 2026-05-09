package com.smarttravel.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.smarttravel.model.City;
import com.smarttravel.model.WeatherState;

public class WeatherProvider {

    // Observer listesi - dinleyiciler buraya kaydolur
    private List<WeatherObserver> observers = new ArrayList<>();

    // Şehir listesi
    private List<City> cities;

    private Random random = new Random();

    public WeatherProvider(List<City> cities) {
        this.cities = cities;
    }

    // Observer ekle
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    // Observer çıkar
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    // Tüm observer'ları bilgilendir
    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.onWeatherUpdated(cities);
        }
    }

    // Hava durumunu rastgele güncelle
    public void updateWeather() {
        WeatherState[] states = WeatherState.values();

        for (City city : cities) {
            // Rastgele sıcaklık: -10 ile 45 derece arası
            double newTemp = -10 + (55 * random.nextDouble());
            newTemp = Math.round(newTemp * 10.0) / 10.0;

            // Rastgele hava durumu
            WeatherState newState = states[random.nextInt(states.length)];

            city.setCurrentTemperature(newTemp);
            city.setCurrentWeatherState(newState);
        }

        // Güncelleme bitti, tüm observer'lara haber ver
        notifyObservers();
    }

    public List<City> getCities() {
        return cities;
    }
}