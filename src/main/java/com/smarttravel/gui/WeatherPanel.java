package com.smarttravel.gui;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.smarttravel.chart.TemperatureBarChart;
import com.smarttravel.chart.WeatherPieChart;
import com.smarttravel.model.City;
import com.smarttravel.observer.WeatherProvider;
import com.smarttravel.observer.WeatherService;

public class WeatherPanel extends JPanel {

    private WeatherProvider weatherProvider;
    private TemperatureBarChart barChart;
    private WeatherPieChart pieChart;
    private WeatherService weatherService;
    private Thread weatherThread;

    public WeatherPanel(List<City> cities) {
        this.weatherProvider = new WeatherProvider(cities);

        // Chart'ları oluştur
        barChart = new TemperatureBarChart();
        pieChart = new WeatherPieChart();

        // Chart'ları Observer olarak kaydet
        weatherProvider.addObserver(barChart);
        weatherProvider.addObserver(pieChart);

        // Panel düzeni
        setLayout(new GridLayout(1, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Weather Charts"));
        setPreferredSize(new Dimension(1000, 300));
        
        // Chart panel'lerini ekle
        add(barChart.getChartPanel());
        add(pieChart.getChartPanel());

        // İlk veriyi hemen göster
        weatherProvider.updateWeather();

        // Thread'i başlat
        weatherService = new WeatherService(weatherProvider, null);
        weatherThread = new Thread(weatherService);
        weatherThread.setDaemon(true); // Program kapanınca thread de kapansın
        weatherThread.start();

        
    }

    // WeatherProvider'a dışarıdan erişim (Kişi 1 için - şehir listesi güncelleme)
    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }
    
    public void setAfterUpdate(Runnable afterUpdate) {
    weatherService = new WeatherService(weatherProvider, afterUpdate);

    weatherThread = new Thread(weatherService);
    weatherThread.setDaemon(true);
    weatherThread.start();
}
    // Servisi durdur
    public void stopService() {
        weatherService.stop();
    }
}