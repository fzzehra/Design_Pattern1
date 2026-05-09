package com.smarttravel.chart;

import java.util.List;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.smarttravel.model.City;
import com.smarttravel.observer.WeatherObserver;

public class TemperatureBarChart implements WeatherObserver {

    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;

    public TemperatureBarChart() {
        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "City Temperatures",   // Başlık
                "City",                // X ekseni
                "Temperature (°C)",    // Y ekseni
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 250));
    }

    // Observer Pattern - WeatherProvider güncelleme yapınca bu çağrılır
    @Override
    public void onWeatherUpdated(List<City> cities) {
        // GUI thread'inde güvenli güncelleme
        SwingUtilities.invokeLater(() -> {
            dataset.clear();
            for (City city : cities) {
                dataset.addValue(
                        city.getCurrentTemperature(),
                        "Temperature",
                        city.getName()
                );
            }
        });
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}