import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeTaskEditPanel extends BasePanel {
    private Employee employee; // The employee associated with the task
    private EmployeeTask task; // The task being edited (null for a new task)

    // Fields for task details
    private JTextField titleField;
    private JTextArea descriptionField;
    private JCheckBox completeCheckBox;
    private JTextField startDateField;
    private JTextField endDateField;

    public EmployeeTaskEditPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setTask(Employee employee, EmployeeTask task) {
        this.employee = employee;
        this.task = task;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Edit Task");
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Task Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Task Title:"), gbc);
        titleField = new JTextField(20);
        styleTextField(titleField);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        // Task Description
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);
        descriptionField = new JTextArea(5, 20);
        styleTextArea(descriptionField);
        JScrollPane scrollPane = new JScrollPane(descriptionField);
        gbc.gridx = 1;
        formPanel.add(scrollPane, gbc);

        // Task Complete
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Completed:"), gbc);
        completeCheckBox = new JCheckBox();
        completeCheckBox.setBackground(WHITE);
        gbc.gridx = 1;
        formPanel.add(completeCheckBox, gbc);

        // Start Date
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        startDateField = new JTextField(20);
        styleTextField(startDateField);
        gbc.gridx = 1;
        formPanel.add(startDateField, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        endDateField = new JTextField(20);
        styleTextField(endDateField);
        gbc.gridx = 1;
        formPanel.add(endDateField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(saveTaskAction());
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(_ -> navigateBack());
        buttonPanel.add(cancelButton);

        JButton deleteButton = new JButton("Delete");
        styleButton(deleteButton);
        deleteButton.addActionListener(_ -> {
            if (task != null) {
                employee.deleteTask(task);
                JOptionPane.showMessageDialog(this, "Task deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                navigateBack();
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (task != null) {
            titleField.setText(task.getName());
            descriptionField.setText(task.getDescription());
            completeCheckBox.setSelected(task.getStatus());
            startDateField.setText(task.getStartDate());
            endDateField.setText(task.getEndDate());
        } else {
            titleField.setText("");
            descriptionField.setText("");
            completeCheckBox.setSelected(false);
            startDateField.setText("");
            endDateField.setText("");
        }
    }

    private ActionListener saveTaskAction() {
        return _ -> {
            try {
                String title = titleField.getText().trim();
                String description = descriptionField.getText().trim();
                boolean isComplete = completeCheckBox.isSelected();
                String startDate = startDateField.getText().trim();
                String endDate = endDateField.getText().trim();

                if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled.",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (task == null) {
                    // Save the new task to the employee
                    employee.newTask(title, description, isComplete, startDate, endDate);
                } else {
                    // Update the existing task
                    task.setName(title);
                    task.setDescription(description);
                    task.setStatus(isComplete);
                    task.setStartDate(startDate);
                    task.setEndDate(endDate);

                    // Save updates to the database
                    employee.updateTask(task);
                }

                JOptionPane.showMessageDialog(this, "Task saved successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                navigateBack();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred while saving the task.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}