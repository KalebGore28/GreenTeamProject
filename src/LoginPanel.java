import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel {

    public LoginPanel(JPanel mainPanel) {
        super(mainPanel); 
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to Employee Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        JLabel descriptionLabel = new JLabel("Please select your role to proceed:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(100, 100, 100));
        gbc.gridy = 1;
        add(descriptionLabel, gbc);

        JButton employeeButton = new JButton("Employee");
        styleButton(employeeButton);
        employeeButton.addActionListener(_ -> promptForEmployeeID());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(employeeButton, gbc);

        JButton supervisorButton = new JButton("Supervisor");
        styleButton(supervisorButton);
        supervisorButton.addActionListener(_ -> promptForSupervisorPassword());
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(supervisorButton, gbc);

        JLabel footerLabel = new JLabel("Powered by TechSolutions | Contact: techsolutions@example.com");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(150, 150, 150));
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
                Employee employee = Employee.getEmployee(employeeID);
                System.out.println("Employee: " + employee);

                if (employee != null) {
                    AppState.setCurrentUser(employee); // Set current user
                    navigateToPanel("HomePanel");      // Navigate to the home page
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
                    AppState.setCurrentUser(new Supervisor()); // Set current user as supervisor
                    navigateToPanel("HomePanel");             // Navigate to the home page
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid password format.");
            }
        }
    }
}