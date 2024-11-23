import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeHistoryEditPanel extends BasePanel {
	private Employee employee; // The employee whose history is being edited
	private EmployeeHistory history; // The specific history being edited (null for new history)

	// Fields for editing history
	private JTextField companyField;
	private JTextField departmentField;
	private JTextField positionField;
	private JTextField startDateField;
	private JTextField endDateField;
	private JTextField salaryField;
	private JTextField reasonForLeavingField;

	public EmployeeHistoryEditPanel(JPanel mainPanel) {
		super(mainPanel);
		initializeContent();
	}

	public void setHistory(Employee employee, EmployeeHistory history) {
		this.employee = employee;
		this.history = history;
		refreshContent();
	}

	@Override
	protected void initializeContent() {
		setLayout(new BorderLayout());
		setBackground(WHITE);

		// Header
		JLabel titleLabel = new JLabel("Edit Employee History");
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

		// Company
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("Company:"), gbc);
		companyField = new JTextField(20);
		styleTextField(companyField);
		gbc.gridx = 1;
		formPanel.add(companyField, gbc);

		// Department
		gbc.gridx = 0;
		gbc.gridy++;
		formPanel.add(new JLabel("Department:"), gbc);
		departmentField = new JTextField(20);
		styleTextField(departmentField);
		gbc.gridx = 1;
		formPanel.add(departmentField, gbc);

		// Position
		gbc.gridx = 0;
		gbc.gridy++;
		formPanel.add(new JLabel("Position:"), gbc);
		positionField = new JTextField(20);
		styleTextField(positionField);
		gbc.gridx = 1;
		formPanel.add(positionField, gbc);

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

		// Salary
		gbc.gridx = 0;
		gbc.gridy++;
		formPanel.add(new JLabel("Salary:"), gbc);
		salaryField = new JTextField(20);
		styleTextField(salaryField);
		gbc.gridx = 1;
		formPanel.add(salaryField, gbc);

		// Reason for Leaving
		gbc.gridx = 0;
		gbc.gridy++;
		formPanel.add(new JLabel("Reason for Leaving:"), gbc);
		reasonForLeavingField = new JTextField(20);
		styleTextField(reasonForLeavingField);
		gbc.gridx = 1;
		formPanel.add(reasonForLeavingField, gbc);

		add(formPanel, BorderLayout.CENTER);

		// Buttons
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.setBackground(WHITE);

		JButton saveButton = new JButton("Save");
		styleButton(saveButton);
		saveButton.addActionListener(saveHistoryAction());
		buttonPanel.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		styleButton(cancelButton);
		cancelButton.addActionListener(_ -> navigateBack());
		buttonPanel.add(cancelButton);

		JButton deleteButton = new JButton("Delete");
		styleButton(deleteButton);
		deleteButton.addActionListener(_ -> {
			if (history != null) {
				employee.deleteHistory(history);
				EmployeeHistoryPanel historyPanel = findPanelByType(EmployeeHistoryPanel.class);
				if (historyPanel != null) {
					historyPanel.refreshContent();
				}
				JOptionPane.showMessageDialog(this, "History deleted successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				navigateBack();
			}
		});
		buttonPanel.add(deleteButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	protected void refreshContent() {
		if (history != null) {
			// Populate fields with existing history data
			companyField.setText(history.getCompany());
			departmentField.setText(history.getDepartment());
			positionField.setText(history.getPosition());
			startDateField.setText(history.getStartDate());
			endDateField.setText(history.getEndDate());
			salaryField.setText(String.valueOf(history.getSalary()));
			reasonForLeavingField.setText(history.getReasonForLeaving());
		} else {
			// Clear fields for new history
			companyField.setText("");
			departmentField.setText("");
			positionField.setText("");
			startDateField.setText("");
			endDateField.setText("");
			salaryField.setText("");
			reasonForLeavingField.setText("");
		}
	}

	private ActionListener saveHistoryAction() {
		return _ -> {
			try {
				// Validate and save the history
				String company = companyField.getText().trim();
				String department = departmentField.getText().trim();
				String position = positionField.getText().trim();
				String startDate = startDateField.getText().trim();
				String endDate = endDateField.getText().trim();
				double salary = Double.parseDouble(salaryField.getText().trim());
				String reasonForLeaving = reasonForLeavingField.getText().trim();

				if (company.isEmpty() || department.isEmpty() || position.isEmpty() ||
						startDate.isEmpty() || endDate.isEmpty()) {
					JOptionPane.showMessageDialog(this, "All fields except 'Reason for Leaving' must be filled.",
							"Validation Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (history == null) {
					// New history
					history = new EmployeeHistory();
					history.setCompany(company);
					history.setDepartment(department);
					history.setPosition(position);
					history.setStartDate(startDate);
					history.setEndDate(endDate);
					history.setSalary(salary);
					history.setReasonForLeaving(reasonForLeaving);

					// Save the new history to the database
					employee.newHistory(company, department, position, startDate, endDate, salary, reasonForLeaving);
				} else {
					// Update existing history
					history.setCompany(company);
					history.setDepartment(department);
					history.setPosition(position);
					history.setStartDate(startDate);
					history.setEndDate(endDate);
					history.setSalary(salary);
					history.setReasonForLeaving(reasonForLeaving);

					// Update the history in the database
					employee.updateHistory(history);
				}

				// Refresh the EmployeeHistory panel
				EmployeeHistoryPanel historyPanel = findPanelByType(EmployeeHistoryPanel.class);
				if (historyPanel != null) {
					historyPanel.refreshContent();
				}

				JOptionPane.showMessageDialog(this, "History saved successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);

				navigateBack();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid salary input. Please enter a valid number.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
		};
	}
}