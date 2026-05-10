package com.smarttravel.command;

import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

import java.util.ArrayList;
import java.util.List;

public class ClearPlanCommand implements Command {

    private final ActivityPlan plan;
    private List<TripComponent> backup;

    public ClearPlanCommand(ActivityPlan plan) {
        this.plan = plan;
    }

    @Override
    public void execute() {
        backup = new ArrayList<>(plan.getChildren());
        plan.clear();
    }

    @Override
    public void undo() {
        for (TripComponent component : backup) {
            plan.add(component);
        }
    }
}