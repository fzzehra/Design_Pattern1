package com.smarttravel.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.smarttravel.command.AddActivityCommand;
import com.smarttravel.command.CommandManager;
import com.smarttravel.command.MoveDownCommand;
import com.smarttravel.command.MoveUpCommand;
import com.smarttravel.command.RemoveActivityCommand;
import com.smarttravel.composite.Activity;
import com.smarttravel.composite.ActivityPlan;
import com.smarttravel.composite.TripComponent;

public class TravelPlanPanel extends JPanel {

    private final ActivityPlan rootPlan = new ActivityPlan("My Travel Plan");
    private final CommandManager commandManager = new CommandManager();

    private final DefaultMutableTreeNode rootNode =
            new DefaultMutableTreeNode("My Travel Plan");

    private final JTree tree = new JTree(rootNode);

    private final JLabel totalCostLabel = new JLabel("Total Cost: 0");
    private final JLabel totalTimeLabel = new JLabel("Total Time: 0");

    public TravelPlanPanel() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JButton addButton = new JButton("Add Activity");
        JButton removeButton = new JButton("Remove");
        JButton upButton = new JButton("Move Up");
        JButton downButton = new JButton("Move Down");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(totalCostLabel);
        infoPanel.add(totalTimeLabel);

        add(new JScrollPane(tree), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addActivity());
        removeButton.addActionListener(e -> removeSelected());
        upButton.addActionListener(e -> moveSelectedUp());
        downButton.addActionListener(e -> moveSelectedDown());

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

        commandManager.executeCommand(
                new AddActivityCommand(rootPlan, activity)
        );

        refreshTree();
    }

    private void removeSelected() {
        DefaultMutableTreeNode selected =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selected == null || selected == rootNode) {
            return;
        }

        String selectedName = selected.toString();

        for (TripComponent component : rootPlan.getChildren()) {
            if (component.getName().equals(selectedName)) {
                commandManager.executeCommand(
                        new RemoveActivityCommand(rootPlan, component)
                );
                break;
            }
        }

        refreshTree();
    }

    private void moveSelectedUp() {
        DefaultMutableTreeNode selected =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selected == null || selected == rootNode) {
            return;
        }

        String selectedName = selected.toString();

        for (TripComponent component : rootPlan.getChildren()) {
            if (component.getName().equals(selectedName)) {
                commandManager.executeCommand(
                        new MoveUpCommand(rootPlan, component)
                );
                break;
            }
        }

        refreshTree();
    }

    private void moveSelectedDown() {
        DefaultMutableTreeNode selected =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selected == null || selected == rootNode) {
            return;
        }

        String selectedName = selected.toString();

        for (TripComponent component : rootPlan.getChildren()) {
            if (component.getName().equals(selectedName)) {
                commandManager.executeCommand(
                        new MoveDownCommand(rootPlan, component)
                );
                break;
            }
        }

        refreshTree();
    }

    private void refreshTree() {
        rootNode.removeAllChildren();

        for (TripComponent component : rootPlan.getChildren()) {
            rootNode.add(new DefaultMutableTreeNode(component.getName()));
        }

        totalCostLabel.setText("Total Cost: " + rootPlan.getCost());
        totalTimeLabel.setText("Total Time: " + rootPlan.getRequiredTime());

        tree.updateUI();
    }
}