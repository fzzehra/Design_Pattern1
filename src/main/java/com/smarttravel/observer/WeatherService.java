package com.smarttravel.observer;

public class WeatherService implements Runnable {

    private WeatherProvider weatherProvider;
    private volatile boolean running = true;

    public WeatherService(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // 3 saniye bekle
                Thread.sleep(3000);

                // Hava durumunu güncelle (bu otomatik notify eder)
                weatherProvider.updateWeather();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    // Servisi durdur
    public void stop() {
        running = false;
    }
}