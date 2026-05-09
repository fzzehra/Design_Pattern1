package com.smarttravel.gui;

import com.smarttravel.model.City;
import com.smarttravel.strategy.CitySorter;
import com.smarttravel.strategy.SortByAreaStrategy;
import com.smarttravel.strategy.SortByNameStrategy;
import com.smarttravel.strategy.SortByPopulationStrategy;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.util.List;

public class CityListPanel extends JPanel {

    private final DefaultListModel<City> cityListModel;
    private final JList<City> cityJList;
    private final JComboBox<String> sortComboBox;
    private final CitySorter citySorter;

    public CityListPanel(List<City> cities) {
        this.cityListModel = new DefaultListModel<>();
        this.cityJList = new JList<>(cityListModel);
        this.sortComboBox = new JComboBox<>();
        this.citySorter = new CitySorter();

        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(420, 400));

        JLabel titleLabel = new JLabel("City List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        sortComboBox.addItem("Sort by Name");
        sortComboBox.addItem("Sort by Population");
        sortComboBox.addItem("Sort by Area");

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(sortComboBox, BorderLayout.SOUTH);

        cityJList.setCellRenderer(new CityCellRenderer());

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(cityJList), BorderLayout.CENTER);

        loadCities(cities);
        configureSorting();
    }

    private void loadCities(List<City> cities) {
        cityListModel.clear();

        for (City city : cities) {
            cityListModel.addElement(city);
        }
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

            List<City> currentCities = java.util.Collections.list(cityListModel.elements());
            List<City> sortedCities = citySorter.sort(currentCities);

            loadCities(sortedCities);
        });

        citySorter.setStrategy(new SortByNameStrategy());
        List<City> currentCities = java.util.Collections.list(cityListModel.elements());
        loadCities(citySorter.sort(currentCities));
    }

    public JList<City> getCityJList() {
        return cityJList;
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
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof City city) {

                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                setFont(new Font("Arial", Font.PLAIN, 14));

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