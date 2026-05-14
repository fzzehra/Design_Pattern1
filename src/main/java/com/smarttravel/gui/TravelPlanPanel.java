package com.smarttravel.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.smarttravel.command.AddActivityCommand;
import com.smarttravel.command.ClearPlanCommand;
import com.smarttravel.command.Command;
import com.smarttravel.command.CommandManager;
import com.smarttravel.command.MoveDownCommand;
import com.smarttravel.command.MoveUpCommand;
import com.smarttravel.command.RemoveActivityCommand;
import com.smarttravel.composite.Activity;
import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

import com.smarttravel.decorator.BasicCityVisit;
import com.smarttravel.decorator.CityCenterVisit;
import com.smarttravel.decorator.CityVisitComponent;
import com.smarttravel.decorator.MuseumVisit;
import com.smarttravel.decorator.ParkVisit;
import com.smarttravel.decorator.ShoppingMallVisit;
import com.smarttravel.model.City;



public class TravelPlanPanel extends JPanel {

    private ActivityPlan rootPlan = new ActivityPlan("My Travel Plan");
    private final CommandManager commandManager = new CommandManager();

    private DefaultMutableTreeNode rootNode =
        new DefaultMutableTreeNode(new TreeItem(rootPlan, null));

    private  JTree tree = new JTree(rootNode);
    
    private final JLabel totalCostLabel = new JLabel("Total Cost: 0");
    private final JLabel totalTimeLabel = new JLabel("Total Time: 0");
    private final JCheckBox museumBox = new JCheckBox("Museum Visit");
    private final JCheckBox shoppingBox = new JCheckBox("Shopping Mall Visit");
    private final JCheckBox parkBox = new JCheckBox("Park Visit");
    private final JCheckBox centerBox = new JCheckBox("City Center Visit");
    private final JLabel activeCityLabel = new JLabel("Selected City: None");

    private final Map<City, ActivityPlan> cityPlans = new HashMap<>();
    
    private ActivityPlan activePlan;


    public TravelPlanPanel() {
        setLayout(new BorderLayout(5, 5));

        tree.setRowHeight(28);
        tree.setFont(new Font("Arial", Font.PLAIN, 13));


        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JButton addButton = new JButton("Add Custom Activity");
        JButton addPlanButton = new JButton("Add Plan Node");
        JButton removeButton = new JButton("Remove");
        JButton upButton = new JButton("Move Up");
        JButton downButton = new JButton("Move Down");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton clearButton = new JButton("Clear Plan");

        buttonPanel.add(addPlanButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(clearButton);

        JPanel decoratorPanel = new JPanel();
        decoratorPanel.setLayout(new BoxLayout(decoratorPanel, BoxLayout.Y_AXIS));
        

        decoratorPanel.setBorder(
                BorderFactory.createTitledBorder("Available Activities")
        );

        decoratorPanel.add(museumBox);
        decoratorPanel.add(shoppingBox);
        decoratorPanel.add(parkBox);
        decoratorPanel.add(centerBox);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(activeCityLabel);
        infoPanel.add(totalCostLabel);
        infoPanel.add(totalTimeLabel);
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(
                BorderFactory.createTitledBorder("Planner")
        );

        leftPanel.add(buttonPanel, BorderLayout.NORTH);
        leftPanel.add(infoPanel, BorderLayout.SOUTH);

        JPanel treePanel = new JPanel(new BorderLayout(5, 5));
        treePanel.setBorder(
                BorderFactory.createTitledBorder("Activity Tree for Selected City")
        );

        tree.setBorder(
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.add(treePanel, BorderLayout.CENTER);
        rightPanel.add(decoratorPanel, BorderLayout.EAST);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                leftPanel,
                rightPanel
        );

        splitPane.setResizeWeight(0.28);

    add(splitPane, BorderLayout.CENTER);

        addPlanButton.addActionListener(e -> addPlanNode());
        addButton.addActionListener(e -> addActivity());
        removeButton.addActionListener(e -> removeSelected());
        upButton.addActionListener(e -> moveSelectedUp());
        downButton.addActionListener(e -> moveSelectedDown());

        clearButton.addActionListener(e -> {

            Command clearCommand = new ClearPlanCommand(rootPlan);

            commandManager.executeCommand(clearCommand);

            refreshTree();
        });
        undoButton.addActionListener(e -> {
            commandManager.undo();
            refreshTree();
        });

        redoButton.addActionListener(e -> {
            commandManager.redo();
            refreshTree();
        });

        refreshTree();
    }

        private ActivityPlan getSelectedTargetPlan() {
        DefaultMutableTreeNode selected =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selected == null) {
            return rootPlan;
        }

        Object userObject = selected.getUserObject();

        if (userObject instanceof TreeItem item) {
            if (item.component instanceof ActivityPlan selectedPlan) {
                return selectedPlan;
            }

            if (item.parentPlan != null) {
                return item.parentPlan;
            }
        }

        return rootPlan;
    }
    private void addActivity() {
        String name = JOptionPane.showInputDialog(this, "Activity name:");

        if (name == null || name.isBlank()) {
            return;
        }

        String costText = JOptionPane.showInputDialog(this, "Cost:");

        if (costText == null || costText.isBlank()) {
            return;
        }

        String timeText = JOptionPane.showInputDialog(this, "Required time:");

        if (timeText == null || timeText.isBlank()) {
            return;
        }

        double cost = Double.parseDouble(costText);
        double time = Double.parseDouble(timeText);

        Activity activity = new Activity(name, cost, time);

        City dummyCity = new City(name, 0, 0, 0, null);

        CityVisitComponent visit =
                new BasicCityVisit(dummyCity);

        if (museumBox.isSelected()) {
            visit = new MuseumVisit(visit);
        }

        if (shoppingBox.isSelected()) {
            visit = new ShoppingMallVisit(visit);
        }

        if (parkBox.isSelected()) {
            visit = new ParkVisit(visit);
        }

        if (centerBox.isSelected()) {
            visit = new CityCenterVisit(visit);
        }

        activity = new Activity(
                visit.getDescription(),
                cost + visit.getCost(),
                time + visit.getRequiredTime()
        );

        ActivityPlan targetPlan = getSelectedTargetPlan();

        commandManager.executeCommand(
                new AddActivityCommand(targetPlan, activity)
        );

        refreshTree();
    }

    private void removeSelected() {
    DefaultMutableTreeNode selected =
            (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

    if (selected == null || selected == rootNode) {
        return;
    }

    Object userObject = selected.getUserObject();

    if (userObject instanceof TreeItem item && item.parentPlan != null) {
        commandManager.executeCommand(
                new RemoveActivityCommand(item.parentPlan, item.component)
        );
    }

    refreshTree();
}

    private void moveSelectedUp() {
    DefaultMutableTreeNode selected =
            (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

    if (selected == null || selected == rootNode) {
        return;
    }

    Object userObject = selected.getUserObject();

    if (userObject instanceof TreeItem item && item.parentPlan != null) {
        commandManager.executeCommand(
                new MoveUpCommand(item.parentPlan, item.component)
        );
    }

    refreshTree();
}

    private void moveSelectedDown() {
    DefaultMutableTreeNode selected =
            (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

    if (selected == null || selected == rootNode) {
        return;
    }

    Object userObject = selected.getUserObject();

    if (userObject instanceof TreeItem item && item.parentPlan != null) {
        commandManager.executeCommand(
                new MoveDownCommand(item.parentPlan, item.component)
        );
    }

    refreshTree();
}

    private void addPlanNode() {

    String planName = JOptionPane.showInputDialog(
            this,
            "Plan node name:"
    );

    if (planName == null || planName.isBlank()) {
        return;
    }

    ActivityPlan newPlan = new ActivityPlan(planName);

    ActivityPlan targetPlan = getSelectedTargetPlan();
    targetPlan.add(newPlan);

    refreshTree();
    }
    private DefaultMutableTreeNode buildTreeNode(TripComponent component, ActivityPlan parentPlan) {
    DefaultMutableTreeNode node =
            new DefaultMutableTreeNode(new TreeItem(component, parentPlan));

    if (component instanceof ActivityPlan plan) {
        for (TripComponent child : plan.getChildren()) {
            node.add(buildTreeNode(child, plan));
        }
    }

    return node;
}
public void setActiveCity(City city) {

    if (city == null) {
        return;
    }


    if (!cityPlans.containsKey(city)) {
        cityPlans.put(
                city,
                new ActivityPlan(city.getName() + " Root Plan")
        );
    }

    activePlan = cityPlans.get(city);
    rootPlan = activePlan;

    activeCityLabel.setText("Selected City: " + city.getName());

    refreshTree();
}
    private void refreshTree() {
    rootNode.setUserObject(new TreeItem(rootPlan, null));
    rootNode.removeAllChildren();

    for (TripComponent component : rootPlan.getChildren()) {
        rootNode.add(buildTreeNode(component, rootPlan));
    }

    totalCostLabel.setText("Total Cost: " + rootPlan.getCost());
    totalTimeLabel.setText("Total Time: " + rootPlan.getRequiredTime());

    tree.updateUI();

    for (int i = 0; i < tree.getRowCount(); i++) {
        tree.expandRow(i);
    }
}
    
    private static class TreeItem {
    TripComponent component;
    ActivityPlan parentPlan;

    TreeItem(TripComponent component, ActivityPlan parentPlan) {
        this.component = component;
        this.parentPlan = parentPlan;
    }

    @Override
    public String toString() {
        return component.toString();
    }
}

}