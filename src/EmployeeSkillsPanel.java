import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeSkillsPanel extends BasePanel {
	private Employee employee; // The employee whose skills are being viewed
	private JPanel skillsPanel; // Container for displaying skill cards

	public EmployeeSkillsPanel(JPanel mainPanel) {
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

		// Header
		JLabel titleLabel = new JLabel("Employee Skills");
		titleLabel.setBackground(LIGHT_GRAY);
		titleLabel.setForeground(ACCENT);
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		add(titleLabel, BorderLayout.NORTH);

		// Skills panel
		skillsPanel = new JPanel();
		skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
		skillsPanel.setBackground(WHITE);

		JScrollPane scrollPane = new JScrollPane(skillsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);

		// Action bar with an "Add New Skill" button
		JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		actionBar.setBackground(WHITE);

		JButton addSkillButton = new JButton("Add New Skill");
		styleButton(addSkillButton);
		addSkillButton.addActionListener(_ -> navigateToEditPanel(null)); // Add a new skill
		actionBar.add(addSkillButton);

		// Place action bar above the bottom bar
		JPanel footerPanel = new JPanel(new BorderLayout());
		footerPanel.add(actionBar, BorderLayout.NORTH);

		// Add bottom navigation bar
		BottomBar bottomBar = new BottomBar(mainPanel);
		footerPanel.add(bottomBar, BorderLayout.SOUTH);

		add(footerPanel, BorderLayout.SOUTH);
	}

	@Override
	protected void refreshContent() {
		if (employee == null) {
			skillsPanel.removeAll();
			skillsPanel.add(new JLabel("No employee selected."));
			skillsPanel.revalidate();
			skillsPanel.repaint();
			return;
		}

		List<EmployeeSkill> skills = EmployeeSkill.getSkills();
		skillsPanel.removeAll();

		if (skills.isEmpty()) {
			JLabel noSkillsLabel = new JLabel("No skills found.");
			noSkillsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
			noSkillsLabel.setForeground(Color.GRAY);
			noSkillsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			skillsPanel.add(noSkillsLabel);
		} else {
			for (EmployeeSkill skill : skills) {
				if (skill.getEmployeeId() == employee.getId()) {
					JPanel skillCard = createSkillCard(skill);
					skillsPanel.add(skillCard);
				}
			}
		}

		skillsPanel.revalidate();
		skillsPanel.repaint();
	}

	private JPanel createSkillCard(EmployeeSkill skill) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ACCENT_LIGHT),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel skillLabel = new JLabel(String.format(
				"<html><b>Skill Name:</b> %s<br><b>Proficiency:</b> %s<br><b>Years of Experience:</b> %d<br><b>Last Used:</b> %s</html>",
				skill.getSkillName(),
				skill.getProficiencyLevel(),
				skill.getYearsOfExperience(),
				skill.getLastUsedDate()));
		skillLabel.setFont(new Font("Arial", Font.BOLD, 16));
		skillLabel.setForeground(BLACK);

		card.add(skillLabel, BorderLayout.CENTER);

		// Edit button
		JButton editButton = new JButton("Edit");
		styleButton(editButton);
		editButton.addActionListener(_ -> navigateToEditPanel(skill));
		card.add(editButton, BorderLayout.EAST);

		return card;
	}

	private void navigateToEditPanel(EmployeeSkill skill) {
		EmployeeSkillsEditPanel editPanel = findPanelByType(EmployeeSkillsEditPanel.class);
		editPanel.setSkill(employee, skill);
		navigateToPanel("EmployeeSkillsEdit");
	}
}