import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class LoginPanel extends JPanel {
	public LoginPanel(JPanel mainPanel) {
		// Set layout and background color
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE); // Clean white background

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15); // Padding around components
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Add logo or title
		JLabel titleLabel = new JLabel("Welcome to Employee Management");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(titleLabel, gbc);

		// Add description
		JLabel descriptionLabel = new JLabel("Please select your role to proceed:");
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		descriptionLabel.setForeground(new Color(100, 100, 100)); // Medium gray text
		gbc.gridy = 1;
		add(descriptionLabel, gbc);

		// Add Employee button
		JButton employeeButton = new JButton("Employee");
		styleButton(employeeButton);
		employeeButton.addActionListener(_ -> promptForEmployeeID(mainPanel));
		gbc.gridx = 0; // Center horizontally
		gbc.gridy = 2; // Row for the Employee button
		gbc.gridwidth = 2; // Span two columns to center
		gbc.anchor = GridBagConstraints.CENTER; // Center alignment
		add(employeeButton, gbc);

		// Add Supervisor button
		JButton supervisorButton = new JButton("Supervisor");
		styleButton(supervisorButton);
		supervisorButton.addActionListener(_ -> promptForSupervisorPassword(mainPanel));
		gbc.gridx = 0; // Center horizontally
		gbc.gridy = 3; // Row for the Supervisor button
		gbc.gridwidth = 2; // Span two columns to center
		gbc.anchor = GridBagConstraints.CENTER; // Center alignment
		add(supervisorButton, gbc);

		// Add footer
		JLabel footerLabel = new JLabel("Powered by TechSolutions | Contact: techsolutions@example.com");
		footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		footerLabel.setForeground(new Color(150, 150, 150)); // Light gray text
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(footerLabel, gbc);
	}

	private void promptForEmployeeID(JPanel mainPanel) {
		String input = JOptionPane.showInputDialog(this, "Enter Employee ID:");

		if (input != null && !input.isEmpty()) {
			try {
				int employeeID = Integer.parseInt(input.trim());
				Employee employee = findEmployeeById(employeeID);

				if (employee != null) {
					// Show only the employee's detail panel
					EmployeeDetailPanel detailPanel = new EmployeeDetailPanel(employee, mainPanel);
					mainPanel.add(detailPanel, "EmployeeDetail");
					CardLayout layout = (CardLayout) mainPanel.getLayout();
					layout.show(mainPanel, "EmployeeDetail");
				} else {
					JOptionPane.showMessageDialog(this, "No employee found with ID: " + employeeID);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a numeric ID.");
			}
		}
	}

	private void promptForSupervisorPassword(JPanel mainPanel) {
		String input = JOptionPane.showInputDialog(this, "Enter Supervisor Password:");

		final int SUPERVISOR_PASSWORD = 1001;
		if (input != null && !input.isEmpty()) {
			try {
				int enteredPassword = Integer.parseInt(input.trim());
				if (enteredPassword == SUPERVISOR_PASSWORD) {
					CardLayout layout = (CardLayout) mainPanel.getLayout();
					layout.show(mainPanel, "EmployeeList");
				} else {
					JOptionPane.showMessageDialog(this, "Incorrect password.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid password format.");
			}
		}
	}

	private Employee findEmployeeById(int employeeID) {
		// Replace with actual logic to retrieve employees from the database
		return Employee.getEmployees().stream()
				.filter(emp -> emp.getId() == employeeID)
				.findFirst()
				.orElse(null);
	}

	// Style button
	private Border createRoundedBorder(int radius) {
		return BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(70, 130, 180), 2), // Border color
				BorderFactory.createEmptyBorder(radius, radius * 2, radius, radius * 2) // Padding
		);
	}

	private void styleButton(JButton button) {
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.setForeground(Color.WHITE); // White text for contrast
		button.setBackground(new Color(70, 130, 180)); // Steel Blue background
		button.setOpaque(false); // Allow for custom painting
		button.setContentAreaFilled(false); // Prevent default fill
		button.setFocusPainted(false); // Remove focus painting
		button.setBorderPainted(false); // Remove border painting

		// Add a rounded border
		button.setBorder(createRoundedBorder(10));

		// Add hover effect
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(100, 149, 237)); // Lighter blue on hover
				button.repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(70, 130, 180)); // Original color
				button.repaint();
			}
		});

		// Custom painting for rounded corners
		button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Paint background
				g2.setColor(button.getBackground());
				g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 20, 20);

				// Paint text
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(button.getForeground());
				String text = button.getText();
				int textX = (button.getWidth() - fm.stringWidth(text)) / 2;
				int textY = (button.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
				g2.drawString(text, textX, textY);
			}
		});
	}

}