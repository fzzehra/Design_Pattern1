# Smart Travel Planner Platform

SENG 324 – Software Design Patterns / Term Project  
Ankara Science University · 2025–2026 Spring

A desktop Java application that lets users explore cities, monitor live weather updates,
and build hierarchical travel plans. The project demonstrates the meaningful use of
**seven classical design patterns**.

---

## 1. Requirements

- **Java 21** (or higher)
- **Maven 3.6+** (for building from source)
- A desktop OS that supports Java Swing (Windows / macOS / Linux)

---

## 2. How to Run

### Option A — Run the packaged fat JAR (recommended)

```bash
java -jar SmartTravelPlanner-1.0-SNAPSHOT-jar-with-dependencies.jar
```

> Make sure the `cities.json` file is located **in the same directory** as the JAR,
> since the application loads it from the working directory at startup.

### Option B — Run from source with Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.smarttravel.Main"
```

### Option C — Build the fat JAR yourself

```bash
mvn clean package
```

The packaged JAR will be created under the `target/` folder.

---

## 3. Project Structure

```
com.smarttravel
├── Main.java                  -- application entry point
├── model/
│   ├── City.java
│   └── WeatherState.java      -- enum: SUNNY, CLOUDY, RAINY, SNOWY
├── repository/
│   └── CityRepository.java    -- Singleton, loads cities.json
├── strategy/                  -- Strategy Pattern
│   ├── CitySortStrategy.java
│   ├── CitySorter.java
│   ├── SortByNameStrategy.java
│   ├── SortByPopulationStrategy.java
│   └── SortByAreaStrategy.java
├── iterator/                  -- Iterator Pattern
│   ├── CityIterator.java
│   ├── WeatherCityIterator.java
│   ├── SunnyCityIterator.java
│   ├── CloudyCityIterator.java
│   ├── RainyCityIterator.java
│   └── SnowyCityIterator.java
├── observer/                  -- Observer Pattern
│   ├── WeatherObserver.java
│   ├── WeatherProvider.java
│   └── WeatherService.java
├── decorator/                 -- Decorator Pattern
│   ├── CityVisitComponent.java
│   ├── CityVisitDecorator.java
│   ├── BasicCityVisit.java
│   ├── MuseumVisit.java
│   ├── ShoppingMallVisit.java
│   ├── ParkVisit.java
│   └── CityCenterVisit.java
├── composite/                 -- Composite Pattern
│   ├── TripComponent.java
│   ├── Activity.java          -- leaf
│   └── ActivityPlan.java      -- composite
├── command/                   -- Command Pattern
│   ├── Command.java
│   ├── CommandManager.java
│   ├── AddActivityCommand.java
│   ├── RemoveActivityCommand.java
│   ├── ClearPlanCommand.java
│   ├── MoveUpCommand.java
│   └── MoveDownCommand.java
├── chart/
│   ├── TemperatureBarChart.java
│   └── WeatherPieChart.java
└── gui/
    ├── CityListPanel.java
    ├── WeatherPanel.java
    └── TravelPlanPanel.java
```

---

## 4. Design Patterns Used

| Pattern    | Role                | Key Classes                                                                |
|------------|---------------------|----------------------------------------------------------------------------|
| Singleton  | Centralized data    | `CityRepository`                                                           |
| Strategy   | Interchangeable sort| `CitySortStrategy`, `CitySorter`, `SortByName/Population/AreaStrategy`     |
| Iterator   | Weather filtering   | `CityIterator`, `WeatherCityIterator`, `Sunny/Cloudy/Rainy/SnowyCityIterator` |
| Observer   | Live chart updates  | `WeatherObserver`, `WeatherProvider`, `WeatherService`, `TemperatureBarChart`, `WeatherPieChart` |
| Decorator  | Visit add-ons       | `CityVisitComponent`, `BasicCityVisit`, `CityVisitDecorator`, `Museum/ShoppingMall/Park/CityCenterVisit` |
| Composite  | Hierarchical plans  | `TripComponent`, `Activity` (leaf), `ActivityPlan` (composite)             |
| Command    | Undo/redo actions   | `Command`, `CommandManager`, `Add/Remove/Clear/MoveUp/MoveDownCommand`     |

---

## 5. How to Use the GUI

The main window contains three primary panels and two charts:

### Top-left – City List Panel
- **Sort by** combo box (Name / Population / Area) — applies the selected `CitySortStrategy`.
- **Filter by Weather** combo box (All / Sunny / Cloudy / Rainy / Snowy) — uses the matching `WeatherCityIterator` to filter cities.
- Click on a city to make it the *active* city for planning.

### Top-right – Weather Charts
- **Temperature Bar Chart** — shows the current temperature of every city.
- **Weather Pie Chart** — shows the distribution of weather states.
- Both charts update automatically every **3 seconds** via `WeatherService` (Observer pattern).

### Middle – Planner Panel (Decorator)
- Check the activity boxes (Museum, Shopping Mall, Park, City Center) to wrap the selected city in decorators.
- The total cost and total required hours appear at the bottom.

### Right – Travel Plan Panel (Composite + Command)
- **Add Plan Node** — creates a new composite `ActivityPlan` under the active node.
- **Add Selected Activities** — adds the chosen activities (leaves) under the currently active plan node.
- **Remove Selected Node** — removes the highlighted node from the tree.
- **Move Up / Move Down** — re-orders activities.
- **Clear Active City Tree** — empties the entire plan for the active city.
- **Undo / Redo** — reverses or replays the most recent action.

> Each city has its own independent activity plan tree. Switching the active city
> automatically swaps the displayed tree.

---

## 6. Input File: cities.json

The application loads city data from `cities.json` at startup. The expected format:

```json
[
  {
    "name": "Ankara",
    "population": 5337215,
    "area": 25670,
    "currentTemperature": 15.0,
    "currentWeatherState": "SUNNY"
  }
]
```

Allowed values for `currentWeatherState`: `SUNNY`, `CLOUDY`, `RAINY`, `SNOWY`.

---

## 7. Notes

- The weather update thread runs in the background and stops gracefully when the
  application window is closed.
- All user actions go through the `CommandManager`, so virtually every change can be
  undone with the Undo button.