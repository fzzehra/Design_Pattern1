package com.smarttravel.observer;

import javax.swing.SwingUtilities;

public class WeatherService implements Runnable {

    private WeatherProvider weatherProvider;
    private volatile boolean running = true;

    private Runnable afterUpdate;

    public WeatherService(WeatherProvider weatherProvider, Runnable afterUpdate) {
        this.weatherProvider = weatherProvider;
        this.afterUpdate = afterUpdate;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(3000);

                weatherProvider.updateWeather();

                if (afterUpdate != null) {
                    SwingUtilities.invokeLater(afterUpdate);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}