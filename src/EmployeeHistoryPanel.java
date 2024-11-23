import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeHistoryPanel extends BasePanel {
    private Employee employee; // Employee whose history is being viewed
    private JPanel historyPanel; // Panel to hold the history cards

    public EmployeeHistoryPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    // Set the employee and refresh content
    public void setEmployee(Employee employee) {
        this.employee = employee;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Employee History");
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // History panel to display cards
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(WHITE);

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Panel to hold both the action bar and bottom bar
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout()); // Use BorderLayout for stacking
        southPanel.setBackground(WHITE); // Ensure consistent background color

        // Action bar with an "Add New History" button
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionBar.setBackground(WHITE);

        JButton addHistoryButton = new JButton("Add New History");
        styleButton(addHistoryButton);
        addHistoryButton.addActionListener(_ -> navigateToEditPanel(null));
        actionBar.add(addHistoryButton);

        // Add action bar to the top of the south panel
        southPanel.add(actionBar, BorderLayout.NORTH);

        // Bottom Bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        southPanel.add(bottomBar, BorderLayout.SOUTH);

        // Add the south panel to the main layout
        add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (employee == null) {
            // Clear panel if no employee is set
            historyPanel.removeAll();
            historyPanel.add(new JLabel("No Employee Selected", SwingConstants.CENTER));
            revalidate();
            repaint();
            return;
        }

        // Populate history cards
        historyPanel.removeAll();
        List<EmployeeHistory> histories = employee.getHistories();
        if (histories.isEmpty()) {
            historyPanel.add(new JLabel("No History Found", SwingConstants.CENTER));
        } else {
            for (EmployeeHistory history : histories) {
                historyPanel.add(createHistoryCard(history));
            }
        }
        revalidate();
        repaint();
    }

    private JPanel createHistoryCard(EmployeeHistory history) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_LIGHT),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Details of the history
        JLabel detailsLabel = new JLabel(String.format(
                "<html><b>Company:</b> %s<br><b>Position:</b> %s<br><b>Department:</b> %s<br><b>Dates:</b> %s - %s</html>",
                history.getCompany(), history.getPosition(), history.getDepartment(), history.getStartDate(),
                history.getEndDate() != "" ? history.getEndDate() : "Present"));
        detailsLabel.setForeground(BLACK);
        card.add(detailsLabel, BorderLayout.CENTER);

        // Edit button
        JButton editButton = new JButton("Edit");
        styleButton(editButton);
        editButton.addActionListener(_ -> navigateToEditPanel(history));
        card.add(editButton, BorderLayout.EAST);

        return card;
    }

    private void navigateToEditPanel(EmployeeHistory history) {
        // Navigate to EmployeeHistoryEditPanel with the given history
        EmployeeHistoryEditPanel editPanel = findPanelByType(EmployeeHistoryEditPanel.class);
        editPanel.setHistory(employee, history); // Set the employee and history
        navigateToPanel("EmployeeHistoryEdit");
    }
}