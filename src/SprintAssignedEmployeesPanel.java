import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SprintAssignedEmployeesPanel extends BasePanel {
    private Sprint sprint; // The sprint whose employees are displayed
    private JPanel employeePanel; // Panel to hold the employee cards

    public SprintAssignedEmployeesPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Assigned Employees", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        // Employee list panel
        employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
        employeePanel.setBackground(WHITE);

        // Scroll pane for employee list
        JScrollPane scrollPane = new JScrollPane(employeePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);

        // Panel to hold both the action bar and bottom bar
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout()); // Use BorderLayout for stacking
        southPanel.setBackground(WHITE); // Ensure consistent background color

        // Action bar with an "Assign Employee" button and a "Unassign Employee" button
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionBar.setBackground(WHITE);

        JButton assignEmployeeButton = new JButton("Assign Employee");
        styleButton(assignEmployeeButton);
        assignEmployeeButton.addActionListener(_ -> {
            // Fetch the list of unassigned employees
            List<Employee> unassignedEmployees = Employee.getEmployees(); // Replace with method to fetch all employees
            unassignedEmployees.removeAll(sprint.getAssignedEmployees()); // Exclude already assigned employees

            if (unassignedEmployees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No unassigned employees available.", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Display a dialog to select an employee
            Employee selectedEmployee = promptForEmployeeSelection(unassignedEmployees);

            if (selectedEmployee != null) {
                sprint.assignEmployee(selectedEmployee); // Assign the selected employee to the sprint
                Sprint.updateSprint(sprint); // Update the sprint in the database
                refreshContent(); // Refresh the panel to show the updated list
                JOptionPane.showMessageDialog(this, "Employee assigned successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actionBar.add(assignEmployeeButton);

        JButton unassignEmployeeButton = new JButton("Unassign Employee");
        styleButton(unassignEmployeeButton);
        unassignEmployeeButton.addActionListener(_ -> {
            List<Employee> assignedEmployees = sprint.getAssignedEmployees();

            if (assignedEmployees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employees are currently assigned to this sprint.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Display a dialog to select an employee
            Employee selectedEmployee = promptForEmployeeSelection(assignedEmployees);

            if (selectedEmployee != null) {
                sprint.unassignEmployee(selectedEmployee); // Unassign the employee from the sprint
                Sprint.updateSprint(sprint); // Update the sprint in the database
                refreshContent(); // Refresh the panel to show the updated list
                JOptionPane.showMessageDialog(this, "Employee unassigned successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actionBar.add(unassignEmployeeButton);

        // Add action bar to the top of the south panel
        southPanel.add(actionBar, BorderLayout.NORTH);

        // Add reusable bottom bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        southPanel.add(bottomBar, BorderLayout.SOUTH);

        // Add the south panel to the main layout
        add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        employeePanel.removeAll();

        if (sprint != null) {
            List<Employee> assignedEmployees = sprint.getAssignedEmployees();

            if (assignedEmployees.isEmpty()) {
                JLabel noEmployeesLabel = new JLabel("No employees assigned to this sprint.", SwingConstants.CENTER);
                noEmployeesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                noEmployeesLabel.setForeground(GRAY);
                employeePanel.add(noEmployeesLabel);
            } else {
                for (Employee employee : assignedEmployees) {
                    employeePanel.add(createEmployeeCard(employee));
                    employeePanel.add(Box.createVerticalStrut(10)); // Add spacing between cards
                }
            }
        } else {
            JLabel errorLabel = new JLabel("Error: Sprint not set.", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            employeePanel.add(errorLabel);
        }

        employeePanel.revalidate();
        employeePanel.repaint();
    }

    private Employee promptForEmployeeSelection(List<Employee> employees) {
        String[] employeeNames = employees.stream()
                .map(emp -> String.format("%s %s (ID: %d)", emp.getFirstName(), emp.getLastName(), emp.getId()))
                .toArray(String[]::new);

        String selectedName = (String) JOptionPane.showInputDialog(
                this,
                "Select an Employee:",
                "Employee Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                employeeNames,
                employeeNames[0]);

        if (selectedName != null) {
            // Find and return the selected employee
            return employees.stream()
                    .filter(emp -> selectedName.contains(String.valueOf(emp.getId())))
                    .findFirst()
                    .orElse(null);
        }

        return null; // No selection made
    }

    /**
     * Helper to create a card for an employee.
     */
    private JPanel createEmployeeCard(Employee employee) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Employee info
        JLabel employeeInfo = new JLabel(
                String.format("<html><b>%s %s</b><br>Email: %s<br>Department: %s</html>",
                        employee.getFirstName(), employee.getLastName(),
                        employee.getEmail(), employee.getDepartment()));
        employeeInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(employeeInfo, BorderLayout.CENTER);

        // Button to view more details
        JButton moreInfoButton = new JButton("More Info");
        styleButton(moreInfoButton);
        moreInfoButton.addActionListener(_ -> {
            EmployeeViewPanel viewPanel = findPanelByType(EmployeeViewPanel.class);
            viewPanel.setEmployee(employee);
            navigateToPanel("EmployeeView");
        });

        card.add(moreInfoButton, BorderLayout.EAST);

        return card;
    }
}