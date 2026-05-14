Smart Travel Planner Platform
Overview

Smart Travel Planner Platform is a Java Swing application developed for demonstrating multiple software design patterns within a single integrated system.

The application allows users to:

dynamically sort cities,
filter cities according to weather conditions,
monitor live weather updates,
visualize weather statistics with charts,
create hierarchical travel plans,
manage activities using undo/redo operations,
create city-specific trip plans.

The project integrates several design patterns together in a practical GUI-based travel planning system.

Implemented Design Patterns
1. Strategy Pattern

Used for dynamic city sorting operations.

Implemented Strategies
SortByNameStrategy
SortByPopulationStrategy
SortByAreaStrategy
Purpose

Allows runtime switching between different sorting algorithms without modifying existing code.

2. Iterator Pattern

Used for filtering cities according to weather conditions.

Implemented Iterators
SunnyCityIterator
RainyCityIterator
CloudyCityIterator
SnowyCityIterator
Features
Weather filtering ComboBox
Dynamic filtered city list
Automatic filtered list refresh
3. Observer Pattern

Used for live weather updates and automatic GUI synchronization.

Responsibilities
Dynamic weather updates
Temperature updates
GUI synchronization
Real-time chart refresh
Charts
Temperature Bar Chart
Weather Distribution Pie Chart
4. Singleton Pattern

Used in:

CityRepository
Purpose

Ensures that city data is loaded only once globally from the JSON source.

5. Decorator Pattern

Used for dynamically extending city visit activities.

Implemented Decorators
MuseumVisit
ShoppingMallVisit
ParkVisit
CityCenterVisit
Purpose

Allows optional travel activities to be added without modifying the original city visit structure.

6. Composite Pattern

Used for hierarchical travel plan management.

Components
ActivityPlan (Composite)
Activity (Leaf)
Features
Recursive activity structures
Nested travel plans
Composite activity groups
City-specific activity trees
7. Command Pattern

Used for encapsulating GUI operations.

Implemented Commands
AddActivityCommand
RemoveActivityCommand
MoveUpCommand
MoveDownCommand
ClearPlanCommand
Features
Undo support
Redo support
Reversible GUI operations
Main GUI Components
City Management Section

Features:

Scrollable city list
Runtime sorting selection
Dynamic weather filtering
Strategy switching using ComboBox
Travel Planner Section

Features:

Add composite plan nodes
Add standard activities
Add custom activities
Remove activities
Move activities up/down
Clear active city tree
Undo/Redo operations
City-specific travel plans
Weather Visualization Section

Features:

Temperature bar chart
Weather distribution pie chart
Real-time chart updates
Main Classes
CitySortStrategy

Common strategy interface for sorting algorithms.

CitySorter

Context class responsible for dynamically switching sorting strategies.

CityListPanel

GUI panel responsible for city visualization and sorting operations.

WeatherPanel

Handles weather filtering and observer-based updates.

TravelPlanPanel

Manages travel planning GUI and command-based actions.

ActivityPlan

Composite class representing recursive activity groups.

Activity

Leaf node representing individual travel activities.

Technologies Used
Java
Java Swing
Gson
JFreeChart
Object-Oriented Programming
Design Patterns
How to Run
Run from IDE

Execute:

Main.java
Run Packaged Jar
java -jar SmartTravelPlanner.jar
Project Structure
com.smarttravel
│
├── command
├── composite
├── decorator
├── gui
├── iterator
├── model
├── observer
├── repository
├── strategy
└── Main.java
Input File

The application loads city data from:

cities.json
Additional Features
Dynamic weather updates every 3 seconds
Independent activity tree for each city
Recursive composite activity system
Real-time GUI synchronization
Undoable user operations
Dynamic weather charts
UML Diagram

The UML class diagram for the project is included in the submission files.