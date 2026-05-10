package com.smarttravel.command;

import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

public class AddActivityCommand implements Command {

    private final ActivityPlan parentPlan;
    private final TripComponent component;

    public AddActivityCommand(ActivityPlan parentPlan, TripComponent component) {
        this.parentPlan = parentPlan;
        this.component = component;
    }

    @Override
    public void execute() {
        parentPlan.add(component);
    }

    @Override
    public void undo() {
        parentPlan.remove(component);
    }
}