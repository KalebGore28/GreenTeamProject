import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeSkillsEditPanel extends BasePanel {
    private Employee employee; // The employee whose skill is being edited
    private EmployeeSkill skill; // The specific skill being edited (null for new skill)

    // Fields for editing skill
    private JTextField skillNameField;
    private JTextField proficiencyField;
    private JTextField yearsOfExperienceField;
    private JTextField lastUsedDateField;

    public EmployeeSkillsEditPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setSkill(Employee employee, EmployeeSkill skill) {
        this.employee = employee;
        this.skill = skill;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Edit Employee Skill");
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

        // Skill Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Skill Name:"), gbc);
        skillNameField = new JTextField(20);
        styleTextField(skillNameField);
        gbc.gridx = 1;
        formPanel.add(skillNameField, gbc);

        // Proficiency
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Proficiency:"), gbc);
        proficiencyField = new JTextField(20);
        styleTextField(proficiencyField);
        gbc.gridx = 1;
        formPanel.add(proficiencyField, gbc);

        // Years of Experience
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Years of Experience:"), gbc);
        yearsOfExperienceField = new JTextField(20);
        styleTextField(yearsOfExperienceField);
        gbc.gridx = 1;
        formPanel.add(yearsOfExperienceField, gbc);

        // Last Used Date
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Last Used Date (YYYY-MM-DD):"), gbc);
        lastUsedDateField = new JTextField(20);
        styleTextField(lastUsedDateField);
        gbc.gridx = 1;
        formPanel.add(lastUsedDateField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(saveSkillAction());
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(_ -> navigateBack());
        buttonPanel.add(cancelButton);

		JButton deleteButton = new JButton("Delete");
		styleButton(deleteButton);
		deleteButton.addActionListener(_ -> {
			if (skill != null) {
				employee.deleteSkill(skill);
				JOptionPane.showMessageDialog(this, "Skill deleted successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				navigateBack();
			}
		});
		buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (skill != null) {
            // Populate fields with existing skill data
            skillNameField.setText(skill.getSkillName());
            proficiencyField.setText(skill.getProficiencyLevel());
            yearsOfExperienceField.setText(String.valueOf(skill.getYearsOfExperience()));
            lastUsedDateField.setText(skill.getLastUsedDate());
        } else {
            // Clear fields for new skill
            skillNameField.setText("");
            proficiencyField.setText("");
            yearsOfExperienceField.setText("");
            lastUsedDateField.setText("");
        }
    }

    private ActionListener saveSkillAction() {
        return _ -> {
            try {
                // Validate and save the skill
                String skillName = skillNameField.getText().trim();
                String proficiency = proficiencyField.getText().trim();
                int yearsOfExperience = Integer.parseInt(yearsOfExperienceField.getText().trim());
                String lastUsedDate = lastUsedDateField.getText().trim();

                if (skillName.isEmpty() || proficiency.isEmpty() || lastUsedDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled.",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (skill == null) {
                    // New skill
                    skill = new EmployeeSkill();
                    skill.setSkillName(skillName);
                    skill.setProficiencyLevel(proficiency);
                    skill.setYearsOfExperience(yearsOfExperience);
                    skill.setLastUsedDate(lastUsedDate);

                    // Save the new skill to the database
                    employee.saveSkill(skill);
                } else {
                    // Update existing skill
                    skill.setSkillName(skillName);
                    skill.setProficiencyLevel(proficiency);
                    skill.setYearsOfExperience(yearsOfExperience);
                    skill.setLastUsedDate(lastUsedDate);

                    // Update the skill in the database
                    employee.updateSkill(skill);
                }

                // Refresh the skills panel
                EmployeeSkillsPanel skillsPanel = findPanelByType(EmployeeSkillsPanel.class);
                skillsPanel.setEmployee(employee);

                JOptionPane.showMessageDialog(this, "Skill saved successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                navigateBack();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid years of experience. Please enter a valid number.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}