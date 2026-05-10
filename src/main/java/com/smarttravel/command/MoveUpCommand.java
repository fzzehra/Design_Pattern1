package com.smarttravel.command;

import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

public class MoveUpCommand implements Command {

    private final ActivityPlan parentPlan;
    private final TripComponent component;

    public MoveUpCommand(ActivityPlan parentPlan, TripComponent component) {
        this.parentPlan = parentPlan;
        this.component = component;
    }

    @Override
    public void execute() {
        parentPlan.moveUp(component);
    }

    @Override
    public void undo() {
        parentPlan.moveDown(component);
    }
}