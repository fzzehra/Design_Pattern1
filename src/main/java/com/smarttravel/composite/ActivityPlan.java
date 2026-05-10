package com.smarttravel.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityPlan implements TripComponent {

    private final String name;
    private final List<TripComponent> children = new ArrayList<>();

    public ActivityPlan(String name) {
        this.name = name;
    }

    public void add(TripComponent component) {
        children.add(component);
    }

    public void add(int index, TripComponent component) {
        children.add(index, component);
    }

    public void remove(TripComponent component) {
        children.remove(component);
    }

    public int indexOf(TripComponent component) {
        return children.indexOf(component);
    }

    public List<TripComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void clear() {
        children.clear();
    }

    public void moveUp(TripComponent component) {
        int index = children.indexOf(component);

        if (index > 0) {
            Collections.swap(children, index, index - 1);
        }
    }

    public void moveDown(TripComponent component) {
        int index = children.indexOf(component);

        if (index >= 0 && index < children.size() - 1) {
            Collections.swap(children, index, index + 1);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCost() {
        return children.stream()
                .mapToDouble(TripComponent::getCost)
                .sum();
    }

    @Override
    public double getRequiredTime() {
        return children.stream()
                .mapToDouble(TripComponent::getRequiredTime)
                .sum();
    }

    @Override
    public String toString() {
        return name + " | Total Cost: " + getCost() + " TL | Total Time: " + getRequiredTime() + " h";
    }
}