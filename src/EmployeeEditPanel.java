import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeEditPanel extends BasePanel {
    private Employee employee; // Current employee to display/edit
    private JTextField firstNameField, lastNameField, emailField, departmentField, positionField, salaryField;

    public EmployeeEditPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Title
        JLabel titleLabel = new JLabel("Employee Info", SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		titleLabel.setBackground(LIGHT_GRAY);
		titleLabel.setForeground(ACCENT);
		titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        firstNameField = createTextField("First Name:");
        lastNameField = createTextField("Last Name:");
        emailField = createTextField("Email:");
        departmentField = createTextField("Department:");
        positionField = createTextField("Position:");
        salaryField = createTextField("Salary:");

        // Add fields to content panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(departmentField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(positionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        contentPanel.add(salaryField, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // Bottom bar with Save and Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEmployeeInfo();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateBack();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Populate the form with employee details.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        departmentField.setText(employee.getDepartment());
        positionField.setText(employee.getPosition());
        salaryField.setText(String.valueOf(employee.getSalary()));

        boolean isSupervisor = AppState.getCurrentUser() instanceof Supervisor;

        // Set fields as editable based on user role
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        emailField.setEditable(true);
        departmentField.setEditable(isSupervisor);
        positionField.setEditable(isSupervisor);
        salaryField.setEditable(isSupervisor);
    }

    /**
     * Save the employee info based on edited fields.
     */
    private void saveEmployeeInfo() {
        if (employee != null) {
            employee.setFirstName(firstNameField.getText().trim());
            employee.setLastName(lastNameField.getText().trim());
            employee.setEmail(emailField.getText().trim());

            if (AppState.getCurrentUser() instanceof Supervisor) {
                employee.setDepartment(departmentField.getText().trim());
                employee.setPosition(positionField.getText().trim());
                try {
                    employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid salary value.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Save employee details to the database or file
            Employee.updateEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee information saved successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            navigateBack();
        }
    }

    /**
     * Create a styled text field for the form.
     */
    private JTextField createTextField(String labelText) {
        JTextField textField = new JTextField(20);
        styleTextField(textField);
        return textField;
    }
}