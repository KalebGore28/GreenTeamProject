import javax.swing.*;
import java.awt.*;

public class EmployeeDemographicsEditPanel extends BasePanel {
    private Employee employee; // Current employee whose demographics are being edited

    // Fields for editing demographics
    private JTextField birthDateField;
    private JTextField genderField;
    private JTextField ethnicityField;
    private JTextField employmentStatusField;

    public EmployeeDemographicsEditPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Title
        JLabel titleLabel = new JLabel("Edit Employee Demographics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add fields to the form
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Birth Date (YYYY-MM-DD):"), gbc);
        birthDateField = new JTextField(20);
        styleTextField(birthDateField);
        gbc.gridx = 1;
        formPanel.add(birthDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Gender:"), gbc);
        genderField = new JTextField(20);
        styleTextField(genderField);
        gbc.gridx = 1;
        formPanel.add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Ethnicity:"), gbc);
        ethnicityField = new JTextField(20);
        styleTextField(ethnicityField);
        gbc.gridx = 1;
        formPanel.add(ethnicityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Employment Status:"), gbc);
        employmentStatusField = new JTextField(20);
        styleTextField(employmentStatusField);
        gbc.gridx = 1;
        formPanel.add(employmentStatusField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(_ -> saveDemographics());

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(_ -> navigateBack());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (employee != null) {
            // Populate the fields with existing demographics data
            EmployeeDemographic demographics = employee.getDemographic();
            birthDateField.setText(demographics.getBirthDate());
            genderField.setText(demographics.getGender());
            ethnicityField.setText(demographics.getEthnicity());
            employmentStatusField.setText(demographics.getEmploymentStatus());
        } else {
            // Clear fields if no employee is set
            birthDateField.setText("");
            genderField.setText("");
            ethnicityField.setText("");
            employmentStatusField.setText("");
        }
    }

    private void saveDemographics() {
        if (employee != null) {
            // Validate and save the data
            try {
                String birthDate = birthDateField.getText().trim();
                String gender = genderField.getText().trim();
                String ethnicity = ethnicityField.getText().trim();
                String employmentStatus = employmentStatusField.getText().trim();

                if (birthDate.isEmpty() || gender.isEmpty() || ethnicity.isEmpty() || employmentStatus.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the employee's demographics
                EmployeeDemographic demographics = employee.getDemographic();
                demographics.setBirthDate(birthDate);
                demographics.setGender(gender);
                demographics.setEthnicity(ethnicity);
                demographics.setEmploymentStatus(employmentStatus);

                // Save to the database or file
                employee.updateDemographic(demographics);
                JOptionPane.showMessageDialog(this, "Demographics updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                navigateBack();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while saving the demographics.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}