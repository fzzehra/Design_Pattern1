package com.smarttravel.command;

import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

public class RemoveActivityCommand implements Command {

    private final ActivityPlan parentPlan;
    private final TripComponent component;
    private int oldIndex;

    public RemoveActivityCommand(ActivityPlan parentPlan, TripComponent component) {
        this.parentPlan = parentPlan;
        this.component = component;
    }

    @Override
    public void execute() {
        oldIndex = parentPlan.indexOf(component);
        parentPlan.remove(component);
    }

    @Override
    public void undo() {
        if (oldIndex >= 0) {
            parentPlan.add(oldIndex, component);
        } else {
            parentPlan.add(component);
        }
    }
}