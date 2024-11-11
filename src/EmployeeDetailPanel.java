import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeDetailPanel extends JPanel {
	public EmployeeDetailPanel(Employee employee, JPanel mainPanel) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Title Label
		JLabel titleLabel = new JLabel("Employee Details");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// Details Panel for Basic Information
		JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Information"));
		addDetailRow(detailsPanel, "ID:", String.valueOf(employee.getId()));
		addDetailRow(detailsPanel, "Name:", employee.getFirstName() + " " + employee.getLastName());
		addDetailRow(detailsPanel, "Email:", employee.getEmail());
		addDetailRow(detailsPanel, "Department:", employee.getDepartment());
		addDetailRow(detailsPanel, "Position:", employee.getPosition());
		addDetailRow(detailsPanel, "Salary:", String.format("$%,.2f", employee.getSalary()));
		detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

		// Skills Panel for displaying employee skills
		JPanel skillsPanel = new JPanel();
		skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
		skillsPanel.setBorder(BorderFactory.createTitledBorder("Skills"));
		skillsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

		List<EmployeeSkill> skills = employee.getSkills();
		for (EmployeeSkill skill : skills) {
			JPanel skillEntry = new JPanel(new GridLayout(0, 2, 10, 5));
			addDetailRow(skillEntry, "Skill:", skill.getSkillName());
			addDetailRow(skillEntry, "Level:", skill.getProficiencyLevel());
			addDetailRow(skillEntry, "Experience:", skill.getYearsOfExperience() + " years");
			addDetailRow(skillEntry, "Last Used:", skill.getLastUsedDate());
			skillEntry.setAlignmentX(Component.LEFT_ALIGNMENT);
			skillsPanel.add(skillEntry);
			skillsPanel.add(Box.createVerticalStrut(5)); // Space between entries
			skillsPanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator between skills
		}

		// History Panel for displaying employee job history
		JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
		historyPanel.setBorder(BorderFactory.createTitledBorder("History"));
		historyPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

		List<EmployeeHistory> history = employee.getHistories(); // Assuming this method exists
		for (EmployeeHistory record : history) {
			JPanel historyEntry = new JPanel(new GridLayout(0, 2, 10, 5));
			addDetailRow(historyEntry, "Company:", record.getCompany());
			addDetailRow(historyEntry, "Department:", record.getDepartment());
			addDetailRow(historyEntry, "Position:", record.getPosition());
			addDetailRow(historyEntry, "Salary:", String.format("$%,.2f", record.getSalary()));
			addDetailRow(historyEntry, "Start Date:", record.getStartDate());

			if (record.getEndDate() != "") {
				addDetailRow(historyEntry, "End Date:", record.getEndDate());
				addDetailRow(historyEntry, "Reason for Leaving:", record.getReasonForLeaving());
			}
			historyEntry.setAlignmentX(Component.LEFT_ALIGNMENT);
			historyPanel.add(historyEntry);
			historyPanel.add(Box.createVerticalStrut(5)); // Space between entries
			historyPanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator between history records
		}

		// Tasks Panel for displaying employee tasks
		JPanel tasksPanel = new JPanel();
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		tasksPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));
		tasksPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

		List<EmployeeTask> tasks = employee.getTasks(); // Assuming this method exists
		for (EmployeeTask task : tasks) {
			JPanel taskEntry = new JPanel(new GridLayout(0, 2, 10, 5));
			addDetailRow(taskEntry, "Task:", task.getName());
			addDetailRow(taskEntry, "Description:", task.getDescription());
			addDetailRow(taskEntry, "Start Date:", task.getStartDate());
			addDetailRow(taskEntry, "End Date:", task.getEndDate());
			addDetailRow(taskEntry, "Status:", task.getStatus());
			taskEntry.setAlignmentX(Component.LEFT_ALIGNMENT);
			tasksPanel.add(taskEntry);
			tasksPanel.add(Box.createVerticalStrut(5)); // Space between entries
			tasksPanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator between tasks
		}

		// Main container to stack detailsPanel, skillsPanel, historyPanel, and
		// tasksPanel
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		mainContainer.add(detailsPanel);
		mainContainer.add(Box.createVerticalStrut(10)); // Space between sections
		mainContainer.add(skillsPanel);
		mainContainer.add(Box.createVerticalStrut(10)); // Space between sections
		mainContainer.add(historyPanel);
		mainContainer.add(Box.createVerticalStrut(10)); // Space between sections
		mainContainer.add(tasksPanel);
		mainContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Wrap mainContainer in JScrollPane to handle overflow
		JScrollPane scrollPane = new JScrollPane(mainContainer);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set the scroll speed
		add(scrollPane, BorderLayout.CENTER);

		// Back Button
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.PLAIN, 14));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
				cardLayout.show(mainPanel, "EmployeeList");
			}
		});

		// Button Panel for spacing
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(backButton);
		add(buttonPanel, BorderLayout.PAGE_END);
	}

	// Helper method to add a row of details with label and value
	private void addDetailRow(JPanel panel, String label, String value) {
		JLabel labelComponent = new JLabel(label);
		labelComponent.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel valueComponent = new JLabel(value);
		valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
		valueComponent.setForeground(new Color(60, 60, 60));

		panel.add(labelComponent);
		panel.add(valueComponent);
	}
}