package com.smarttravel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.smarttravel.gui.CityListPanel;
import com.smarttravel.gui.TravelPlanPanel;
import com.smarttravel.gui.WeatherPanel;
import com.smarttravel.model.City;
import com.smarttravel.repository.CityRepository;

public class Main {

    public static void main(String[] args) {

        List<City> cities = CityRepository.getInstance().getCities();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smart Travel Planner Platform");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1700, 980);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout(5, 5));

            CityListPanel cityListPanel = new CityListPanel(cities);

            WeatherPanel weatherPanel = new WeatherPanel(cities);
            weatherPanel.setAfterUpdate(cityListPanel::refreshWeatherFilteredList);

            TravelPlanPanel travelPlanPanel = new TravelPlanPanel();

            cityListPanel.getCityJList().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                City selectedCity =
                        cityListPanel.getCityJList().getSelectedValue();

                travelPlanPanel.setActiveCity(selectedCity);
            }
        });

            JSplitPane topSplit = new JSplitPane(
                    JSplitPane.HORIZONTAL_SPLIT,
                    cityListPanel,
                    travelPlanPanel
            );
            topSplit.setResizeWeight(0.45);

            JSplitPane mainSplit = new JSplitPane(
                    JSplitPane.VERTICAL_SPLIT,
                    topSplit,
                    weatherPanel
            );
            mainSplit.setResizeWeight(0.55);

            JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
            mainPanel.add(mainSplit, BorderLayout.CENTER);

            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}