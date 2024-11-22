import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeDetailPanel extends JPanel {
	public EmployeeDetailPanel(Employee employee, JPanel mainPanel) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);


		// Title Label
		JLabel titleLabel = new JLabel("Employee Details");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		add(titleLabel, BorderLayout.NORTH);

		// Main container for stacking all sections
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		mainContainer.setBackground(Color.WHITE);

		// Add modernized sections
		mainContainer.add(createDetailsSection(employee));
		// mainContainer.add(Box.createVerticalStrut(20)); // Add spacing between sections
		mainContainer.add(createSkillsSection(employee));
		// mainContainer.add(Box.createVerticalStrut(20));
		mainContainer.add(createHistorySection(employee));
		// mainContainer.add(Box.createVerticalStrut(20));
		mainContainer.add(createTasksSection(employee));

		// Wrap mainContainer in JScrollPane
		JScrollPane scrollPane = new JScrollPane(mainContainer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for modern look
		scrollPane.getViewport().setBackground(Color.WHITE); // Ensure the viewport matches the background
		add(scrollPane, BorderLayout.CENTER);

		// Back Button
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.PLAIN, 16));
		backButton.setBackground(new Color(52, 152, 219)); // Modern button color
		backButton.setForeground(Color.WHITE);
		backButton.setFocusPainted(false);
		backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		backButton.addActionListener(_ -> {
			CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
			cardLayout.show(mainPanel, "EmployeeList");
		});

		// Button Panel for spacing and alignment
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(245, 245, 245));
		buttonPanel.add(backButton);
		add(buttonPanel, BorderLayout.PAGE_END);
	}

	// Create Details Section
	private JPanel createDetailsSection(Employee employee) {
		JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Information"));
		detailsPanel.setBackground(Color.WHITE);
		detailsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
						"Basic Information"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		addDetailRow(detailsPanel, "ID:", String.valueOf(employee.getId()));
		addDetailRow(detailsPanel, "Name:", employee.getFirstName() + " " + employee.getLastName());
		addDetailRow(detailsPanel, "Email:", employee.getEmail());
		addDetailRow(detailsPanel, "Department:", employee.getDepartment());
		addDetailRow(detailsPanel, "Position:", employee.getPosition());
		addDetailRow(detailsPanel, "Salary:", String.format("$%,.2f", employee.getSalary()));

		// Edit Button
		JButton editButton = createEditButton("Edit Information", () -> openEditDialog(employee));
		detailsPanel.add(new JLabel()); // Empty cell for alignment
		detailsPanel.add(editButton);

		return detailsPanel;
	}

	// Create Skills Section
	private JPanel createSkillsSection(Employee employee) {
		JPanel skillsPanel = new JPanel();
		skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
		skillsPanel.setBackground(Color.WHITE);
		skillsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Skills"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		List<EmployeeSkill> skills = employee.getSkills();
		if (skills != null && !skills.isEmpty()) {
			for (EmployeeSkill skill : skills) {
				JLabel skillLabel = new JLabel(skill.getSkillName() + " - " + skill.getProficiencyLevel());
				skillLabel.setFont(new Font("Arial", Font.PLAIN, 14));
				skillsPanel.add(skillLabel);
			}
		} else {
			skillsPanel.add(new JLabel("No skills available"));
		}

		// Edit Button
		JButton editButton = createEditButton("Edit Skills", () -> openSkillsEditDialog(employee));
		skillsPanel.add(Box.createVerticalStrut(10));
		skillsPanel.add(editButton);

		return skillsPanel;
	}

	// Create History Section
	private JPanel createHistorySection(Employee employee) {
		JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
		historyPanel.setBackground(Color.WHITE);
		historyPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "History"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		List<EmployeeHistory> histories = employee.getHistories();
		if (histories != null && !histories.isEmpty()) {
			for (EmployeeHistory history : histories) {
				JLabel historyLabel = new JLabel(history.getCompany() + " - " + history.getPosition());
				historyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
				historyPanel.add(historyLabel);
			}
		} else {
			historyPanel.add(new JLabel("No history available"));
		}

		// Edit Button
		JButton editButton = createEditButton("Edit History", () -> openHistoryEditDialog(employee));
		historyPanel.add(Box.createVerticalStrut(10));
		historyPanel.add(editButton);

		return historyPanel;
	}

	// Create Tasks Section
	private JPanel createTasksSection(Employee employee) {
		JPanel tasksPanel = new JPanel();
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		tasksPanel.setBackground(Color.WHITE);
		tasksPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Tasks"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		List<EmployeeTask> tasks = employee.getTasks();
		if (tasks != null && !tasks.isEmpty()) {
			for (EmployeeTask task : tasks) {
				JLabel taskLabel = new JLabel(task.getName() + " - " + task.getStatus());
				taskLabel.setFont(new Font("Arial", Font.PLAIN, 14));
				tasksPanel.add(taskLabel);
			}
		} else {
			tasksPanel.add(new JLabel("No tasks available"));
		}

		// Edit Button
		JButton editButton = createEditButton("Edit Tasks", () -> openTasksEditDialog(employee));
		tasksPanel.add(Box.createVerticalStrut(10));
		tasksPanel.add(editButton);

		return tasksPanel;
	}

	// Helper method to add detail rows
	private void addDetailRow(JPanel panel, String label, String value) {
		JLabel labelComponent = new JLabel(label);
		labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(labelComponent);

		JLabel valueComponent = new JLabel(value);
		valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(valueComponent);
	}

	// Create a modern "Edit" button
	private JButton createEditButton(String text, Runnable onClick) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setBackground(new Color(52, 152, 219));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		button.addActionListener(_ -> onClick.run());
		return button;
	}

	// Open a dialog to edit basic information
	private void openEditDialog(Employee employee) {
		// Create and show an edit dialog for employee information
		JFrame editFrame = new JFrame("Edit Employee Information");
		editFrame.setSize(400, 300);
		editFrame.setLayout(new GridLayout(0, 2, 10, 10));

		JTextField nameField = new JTextField(employee.getFirstName() + " " + employee.getLastName());
		JTextField emailField = new JTextField(employee.getEmail());
		JTextField departmentField = new JTextField(employee.getDepartment());
		JTextField positionField = new JTextField(employee.getPosition());
		JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));

		editFrame.add(new JLabel("Name:"));
		editFrame.add(nameField);
		editFrame.add(new JLabel("Email:"));
		editFrame.add(emailField);
		editFrame.add(new JLabel("Department:"));
		editFrame.add(departmentField);
		editFrame.add(new JLabel("Position:"));
		editFrame.add(positionField);
		editFrame.add(new JLabel("Salary:"));
		editFrame.add(salaryField);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(_ -> {
			employee.setFirstName(nameField.getText());
			employee.setEmail(emailField.getText());
			employee.setDepartment(departmentField.getText());
			employee.setPosition(positionField.getText());
			employee.setSalary(Double.parseDouble(salaryField.getText()));
			JOptionPane.showMessageDialog(editFrame, "Employee information updated!");
			editFrame.dispose();
		});

		editFrame.add(saveButton);
		editFrame.setVisible(true);
	}

	private void openSkillsEditDialog(Employee employee) {
		// Implementation from the original code
	}

	private void openHistoryEditDialog(Employee employee) {
		// Implementation from the original code
	}

	private void openTasksEditDialog(Employee employee) {
		// Implementation from the original code
	}
}