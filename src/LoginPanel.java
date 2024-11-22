import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel {
    public LoginPanel(JPanel mainPanel) {
        super(mainPanel); // Pass mainPanel to the BasePanel constructor
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        // Set layout and background
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add logo or title
        JLabel titleLabel = new JLabel("Welcome to Employee Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Add description
        JLabel descriptionLabel = new JLabel("Please select your role to proceed:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(100, 100, 100)); // Medium gray text
        gbc.gridy = 1;
        add(descriptionLabel, gbc);

        // Add Employee button
        JButton employeeButton = new JButton("Employee");
        styleButton(employeeButton);
        employeeButton.addActionListener(_ -> promptForEmployeeID());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(employeeButton, gbc);

        // Add Supervisor button
        JButton supervisorButton = new JButton("Supervisor");
        styleButton(supervisorButton);
        supervisorButton.addActionListener(_ -> promptForSupervisorPassword());
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(supervisorButton, gbc);

        // Add footer
        JLabel footerLabel = new JLabel("Powered by TechSolutions | Contact: techsolutions@example.com");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(150, 150, 150)); // Light gray text
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(footerLabel, gbc);
    }

    private void promptForEmployeeID() {
        String input = JOptionPane.showInputDialog(this, "Enter Employee ID:");

        if (input != null && !input.isEmpty()) {
            try {
                int employeeID = Integer.parseInt(input.trim());
                Employee employee = findEmployeeById(employeeID);

                if (employee != null) {
                    // Show only the employee's detail panel
                    EmployeeDetailPanel detailPanel = new EmployeeDetailPanel(employee, mainPanel);
                    mainPanel.add(detailPanel, "EmployeeDetail");
                    navigateToPanel("EmployeeDetail");
                } else {
                    JOptionPane.showMessageDialog(this, "No employee found with ID: " + employeeID);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a numeric ID.");
            }
        }
    }

    private void promptForSupervisorPassword() {
        String input = JOptionPane.showInputDialog(this, "Enter Supervisor Password:");

        final int SUPERVISOR_PASSWORD = 1001;
        if (input != null && !input.isEmpty()) {
            try {
                int enteredPassword = Integer.parseInt(input.trim());
                if (enteredPassword == SUPERVISOR_PASSWORD) {
                    navigateToPanel("EmployeeList");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid password format.");
            }
        }
    }

    private Employee findEmployeeById(int employeeID) {
        // Replace with actual logic to retrieve employees from the database
        return Employee.getEmployees().stream()
                .filter(emp -> emp.getId() == employeeID)
                .findFirst()
                .orElse(null);
    }
}