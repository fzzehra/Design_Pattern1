package com.smarttravel.gui;

import com.smarttravel.iterator.CityIterator;
import com.smarttravel.iterator.CloudyCityIterator;
import com.smarttravel.iterator.RainyCityIterator;
import com.smarttravel.iterator.SnowyCityIterator;
import com.smarttravel.iterator.SunnyCityIterator;
import com.smarttravel.model.City;
import com.smarttravel.strategy.CitySorter;
import com.smarttravel.strategy.SortByAreaStrategy;
import com.smarttravel.strategy.SortByNameStrategy;
import com.smarttravel.strategy.SortByPopulationStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CityListPanel extends JPanel {

    private final List<City> allCities;

    private final DefaultListModel<City> cityListModel;
    private final JList<City> cityJList;
    private final JComboBox<String> sortComboBox;
    private final CitySorter citySorter;

    private final DefaultListModel<City> filteredCityListModel;
    private final JList<City> filteredCityJList;
    private final JComboBox<String> weatherComboBox;

    public CityListPanel(List<City> cities) {
        this.allCities = cities;

        this.cityListModel = new DefaultListModel<>();
        this.cityJList = new JList<>(cityListModel);
        this.sortComboBox = new JComboBox<>();
        this.citySorter = new CitySorter();

        this.filteredCityListModel = new DefaultListModel<>();
        this.filteredCityJList = new JList<>(filteredCityListModel);
        this.weatherComboBox = new JComboBox<>();

        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(720, 560));

        sortComboBox.addItem("Sort by Name");
        sortComboBox.addItem("Sort by Population");
        sortComboBox.addItem("Sort by Area");

        weatherComboBox.addItem("ALL");
        weatherComboBox.addItem("SUNNY");
        weatherComboBox.addItem("CLOUDY");
        weatherComboBox.addItem("RAINY");
        weatherComboBox.addItem("SNOWY");

        cityJList.setCellRenderer(new CityCellRenderer());
        filteredCityJList.setCellRenderer(new CityCellRenderer());

        JPanel controlsPanel = new JPanel(new GridLayout(5, 1, 2, 2));
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        controlsPanel.add(new JLabel("Sort Cities:"));
        controlsPanel.add(sortComboBox);
        controlsPanel.add(new JLabel("Filter by Weather:"));
        controlsPanel.add(weatherComboBox);

        JPanel sortedPanel = new JPanel(new BorderLayout(5, 5));
        sortedPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "All Cities (resorted only when sort type changes)"
                )
        );
        sortedPanel.add(new JScrollPane(cityJList), BorderLayout.CENTER);

        JPanel filteredPanel = new JPanel(new BorderLayout(5, 5));
        filteredPanel.setBorder(BorderFactory.createTitledBorder("Cities by Weather"));
        filteredPanel.add(new JScrollPane(filteredCityJList), BorderLayout.CENTER);

        JSplitPane listSplitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                sortedPanel,
                filteredPanel
        );
        listSplitPane.setResizeWeight(0.5);

        add(controlsPanel, BorderLayout.NORTH);
        add(listSplitPane, BorderLayout.CENTER);

        loadCities(cities);
        configureSorting();
        configureWeatherFiltering();
        loadFilteredCities("ALL");
    }

    private void loadCities(List<City> cities) {
        cityListModel.clear();

        for (City city : cities) {
            cityListModel.addElement(city);
        }
    }

    private void loadFilteredCities(String weather) {
        filteredCityListModel.clear();

        if ("ALL".equals(weather)) {
            for (City city : allCities) {
                filteredCityListModel.addElement(city);
            }
            return;
        }

        CityIterator iterator;

        if ("SUNNY".equals(weather)) {
            iterator = new SunnyCityIterator(allCities);
        } else if ("CLOUDY".equals(weather)) {
            iterator = new CloudyCityIterator(allCities);
        } else if ("RAINY".equals(weather)) {
            iterator = new RainyCityIterator(allCities);
        } else {
            iterator = new SnowyCityIterator(allCities);
        }

        while (iterator.hasNext()) {
            filteredCityListModel.addElement(iterator.next());
        }
    }

    private void configureWeatherFiltering() {
        weatherComboBox.addActionListener(e -> {
            String selectedWeather = (String) weatherComboBox.getSelectedItem();
            loadFilteredCities(selectedWeather);
        });
    }

    private void configureSorting() {
        sortComboBox.addActionListener(e -> {
            String selected = (String) sortComboBox.getSelectedItem();

            if ("Sort by Name".equals(selected)) {
                citySorter.setStrategy(new SortByNameStrategy());
            } else if ("Sort by Population".equals(selected)) {
                citySorter.setStrategy(new SortByPopulationStrategy());
            } else if ("Sort by Area".equals(selected)) {
                citySorter.setStrategy(new SortByAreaStrategy());
            }

            List<City> currentCities =
                    java.util.Collections.list(cityListModel.elements());

            List<City> sortedCities = citySorter.sort(currentCities);
            loadCities(sortedCities);
        });

        citySorter.setStrategy(new SortByNameStrategy());

        List<City> currentCities =
                java.util.Collections.list(cityListModel.elements());

        loadCities(citySorter.sort(currentCities));
    }

    public JList<City> getCityJList() {
        return cityJList;
    }

    public void refreshWeatherFilteredList() {
        String selectedWeather = (String) weatherComboBox.getSelectedItem();
        loadFilteredCities(selectedWeather);
    }

    private static class CityCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
            );

            if (value instanceof City city) {
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                setFont(new Font("Arial", Font.PLAIN, 12));

                setText(city.getName()
                        + " | Population: " + city.getPopulation()
                        + " | Area: " + city.getArea() + " km²"
                        + " | Temp: " + city.getCurrentTemperature()
                        + "°C | " + city.getCurrentWeatherState());
            }

            return this;
        }
    }
}