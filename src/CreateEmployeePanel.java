import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateEmployeePanel extends BasePanel {
    public CreateEmployeePanel(JPanel mainPanel) {
        super(mainPanel); // Pass mainPanel to BasePanel constructor
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title for the panel
        JLabel titleLabel = new JLabel("Create New Employee", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel for input fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Form fields
        addFormField(formPanel, gbc, "First Name:", new JTextField(20));
        addFormField(formPanel, gbc, "Last Name:", new JTextField(20));
        addFormField(formPanel, gbc, "Email:", new JTextField(20));
        addFormField(formPanel, gbc, "Department:", new JTextField(20));
        addFormField(formPanel, gbc, "Position:", new JTextField(20));
        addFormField(formPanel, gbc, "Salary:", new JTextField(20));

        // Add form panel to the center
        add(formPanel, BorderLayout.CENTER);

        // Submit and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(_ -> {
            try {
                // Logic for validating and saving a new employee
                JTextField firstNameField = (JTextField) formPanel.getComponent(1);
                JTextField lastNameField = (JTextField) formPanel.getComponent(3);
                JTextField emailField = (JTextField) formPanel.getComponent(5);
                JTextField departmentField = (JTextField) formPanel.getComponent(7);
                JTextField positionField = (JTextField) formPanel.getComponent(9);
                JTextField salaryField = (JTextField) formPanel.getComponent(11);

                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String email = emailField.getText().trim();
                String department = departmentField.getText().trim();
                String position = positionField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                        department.isEmpty() || position.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields except Salary must be filled.", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save the new employee
                Employee newEmployee = new Employee(firstName, lastName, email, department, position, salary);
                Employee.saveEmployee(newEmployee);

                JOptionPane.showMessageDialog(this, "Employee created successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Navigate back to EmployeeListPanel
                navigateBack();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary input. Please enter a valid number.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(_ -> navigateBack());

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        styleLabel(label);
        styleTextField(textField);

        gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        gbc.gridy++;
    }
}