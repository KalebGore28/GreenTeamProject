import javax.swing.*;
import java.awt.*;

public class EmployeeViewPanel extends BasePanel {
	private Employee employee; // Current employee being viewed

	public EmployeeViewPanel(JPanel mainPanel) {
		super(mainPanel); // No need to pass an employee during construction
		initializeContent(); // Set up the layout and static components
	}

	// Setter to update the employee and refresh the content
	public void setEmployee(Employee employee) {
		this.employee = employee;
		refreshContent(); // Reload the panel content
	}

	@Override
	protected void initializeContent() {
		setLayout(new BorderLayout());
		setBackground(WHITE);

		// Header
		JLabel titleLabel = new JLabel();
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		titleLabel.setBackground(LIGHT_GRAY);
		titleLabel.setForeground(ACCENT);
		titleLabel.setOpaque(true);
		add(titleLabel, BorderLayout.NORTH);

		// Main content panel for navigation options
		JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 20, 20)); // 2x2 grid
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		optionsPanel.setBackground(WHITE);

		// Add cards for each section
		optionsPanel.add(createOptionCard("Basic Info", () -> navigateToInfoPanel()));
		optionsPanel.add(createOptionCard("History", () -> navigateToHistoryPanel()));
		optionsPanel.add(createOptionCard("Skills", () -> navigateToSkillsPanel()));
		optionsPanel.add(createOptionCard("Tasks", () -> navigateToTasksPanel()));

		// Scrollable wrapper for the options
		JScrollPane scrollPane = new JScrollPane(optionsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);

		// Add bottom navigation bar
		BottomBar bottomBar = new BottomBar(mainPanel);
		add(bottomBar, BorderLayout.SOUTH);
	}

	@Override
	protected void refreshContent() {
		if (employee == null) {
			// Clear content if no employee is set
			((JLabel) getComponent(0)).setText("No Employee Selected");
			return;
		}

		// Update header
		String headerText = "Employee View: " + employee.getFirstName() + " " + employee.getLastName();
		((JLabel) getComponent(0)).setText(headerText);

		// Add any specific logic for refreshing cards, if necessary
		revalidate();
		repaint();
	}

	private JPanel createOptionCard(String title, Runnable onClick) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(ACCENT);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ACCENT_LIGHT),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Title label
		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titleLabel.setForeground(WHITE);

		card.add(titleLabel, BorderLayout.CENTER);

		// Add click listener
		card.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				card.setBackground(ACCENT_LIGHT); // Change background on hover
				card.repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				card.setBackground(ACCENT); // Reset background
				card.repaint();
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				card.setBackground(ACCENT_DARK); // Darken background on click
				card.repaint();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				card.setBackground(ACCENT_LIGHT); // Reset background after click
				card.repaint();
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				onClick.run(); // Call the provided function
			}
		});

		return card;
	}

	private void navigateToInfoPanel() {
		// Navigate to EmployeeInfoPanel with the current employee
		EmployeeEditPanel infoPanel = findPanelByType(EmployeeEditPanel.class);
		infoPanel.setEmployee(employee);
		navigateToPanel("EmployeeEdit");
	}

	private void navigateToHistoryPanel() {
		EmployeeHistoryPanel historyPanel = findPanelByType(EmployeeHistoryPanel.class);
		historyPanel.setEmployee(employee);
		navigateToPanel("EmployeeHistory");
	}

	private void navigateToSkillsPanel() {
		EmployeeSkillsPanel skillsPanel = findPanelByType(EmployeeSkillsPanel.class);
		skillsPanel.setEmployee(employee);
		navigateToPanel("EmployeeSkills");
	}

	private void navigateToTasksPanel() {
		// EmployeeTasksPanel tasksPanel = findPanelByType(EmployeeTasksPanel.class);
		// tasksPanel.setEmployee(employee);
		// navigateToPanel("EmployeeTasks");
	}
}