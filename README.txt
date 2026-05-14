# Strategy Pattern and City Management Subsystem

## Overview
This subsystem is responsible for dynamic city management and sorting operations in the Smart Travel Planner application.

The subsystem was implemented using the Strategy Design Pattern to support interchangeable sorting algorithms that can be selected dynamically during runtime through the graphical user interface.

---

## Responsibilities

This subsystem includes:

- Dynamic city sorting system
- Strategy Pattern implementation
- GUI integration for city visualization
- Runtime sorting selection using JComboBox
- City list rendering and updating
- Integration support with the main application

---

## Implemented Design Pattern

### Strategy Pattern
The Strategy Pattern was used to encapsulate different sorting algorithms into separate interchangeable strategy classes.

This design allows the application to switch sorting behavior dynamically without modifying existing code.

---

## Implemented Sorting Strategies

### SortByNameStrategy
Sorts cities alphabetically by name.

### SortByPopulationStrategy
Sorts cities according to population values.

### SortByAreaStrategy
Sorts cities according to area size.

- Implemented Iterator Pattern for filtering cities by weather condition
- Added weather condition ComboBox
- Added a second dynamic city list for weather-based filtering
- Integrated the filtered list with weather updates
---

## Main Classes

### CitySortStrategy
Defines the common sorting interface for all strategies.

### CitySorter
Acts as the context class that dynamically changes the active sorting strategy.

### CityListPanel
Graphical user interface component responsible for:
- displaying city information,
- managing sorting interactions,
- updating the city list dynamically.

---

## GUI Features

- Scrollable city list
- Dynamic sorting selection
- Real-time list updates
- Runtime strategy switching

---

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- Design Patterns

---

## How to Use

1. Launch the application.
2. Open the City List section.
3. Select a sorting method from the combo box:
   - Sort by Name
   - Sort by Population
   - Sort by Area
4. The city list updates automatically according to the selected strategy.

---

## UML Diagram

The UML class diagram for this subsystem is included in the project submission files.
