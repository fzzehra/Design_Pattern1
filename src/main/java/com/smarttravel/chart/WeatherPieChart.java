package com.smarttravel.chart;

import java.util.List;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.smarttravel.model.City;
import com.smarttravel.observer.WeatherObserver;

public class WeatherPieChart implements WeatherObserver {

    private DefaultPieDataset<String> dataset;
    private ChartPanel chartPanel;

    public WeatherPieChart() {
        dataset = new DefaultPieDataset<>();

        JFreeChart chart = ChartFactory.createPieChart(
                "Weather Distribution",  // Başlık
                dataset,
                true,   // Legend göster
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 250));
    }

    // Observer Pattern - WeatherProvider güncelleme yapınca bu çağrılır
    @Override
    public void onWeatherUpdated(List<City> cities) {
        SwingUtilities.invokeLater(() -> {
            // Her hava durumu için sayaç
            int sunny = 0, cloudy = 0, rainy = 0, snowy = 0;

            for (City city : cities) {
                switch (city.getCurrentWeatherState()) {
                    case SUNNY  -> sunny++;
                    case CLOUDY -> cloudy++;
                    case RAINY  -> rainy++;
                    case SNOWY  -> snowy++;
                }
            }

            // Dataset güncelle
            dataset.clear();
            if (sunny > 0)  dataset.setValue("SUNNY ("  + sunny  + ")", sunny);
            if (cloudy > 0) dataset.setValue("CLOUDY (" + cloudy + ")", cloudy);
            if (rainy > 0)  dataset.setValue("RAINY ("  + rainy  + ")", rainy);
            if (snowy > 0)  dataset.setValue("SNOWY ("  + snowy  + ")", snowy);
        });
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}