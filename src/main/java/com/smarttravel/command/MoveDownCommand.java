package com.smarttravel.command;

import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

public class MoveDownCommand implements Command {

    private final ActivityPlan parentPlan;
    private final TripComponent component;

    public MoveDownCommand(ActivityPlan parentPlan, TripComponent component) {
        this.parentPlan = parentPlan;
        this.component = component;
    }

    @Override
    public void execute() {
        parentPlan.moveDown(component);
    }

    @Override
    public void undo() {
        parentPlan.moveUp(component);
    }
}