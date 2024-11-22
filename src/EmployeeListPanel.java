import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeListPanel extends JPanel {
	private JPanel employeePanel; // Panel to display employees
	private JTextField searchField; // Search field for filtering employees
	private List<Employee> employees; // Full list of employees

	public EmployeeListPanel(JPanel mainPanel) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE); // Clean white background

		// Add component listener to refresh the list dynamically when shown
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				refreshEmployeeList(mainPanel); // Fetch and update the employee list dynamically
			}
		});

		// Top panel with Back button, search bar, and "Create Employee" button
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(Color.WHITE);

		// Back Button
		JButton backButton = new JButton("Back");
		styleButton(backButton);
		backButton.addActionListener(_ -> {
			CardLayout layout = (CardLayout) mainPanel.getLayout();
			layout.show(mainPanel, "LoginPanel"); // Navigate back to the LoginPanel
		});

		// Search bar
		searchField = new JTextField(20);
		styleSearchField(searchField); // Apply rounded styling to search field
		searchField.setToolTipText("Search by ID, first name, or last name");
		searchField.setText("Search employees..."); // Default text
		searchField.setForeground(Color.GRAY);

		// Focus listener for placeholder behavior
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (searchField.getText().isEmpty()) {
					searchField.setText("Search employees...");
					searchField.setForeground(Color.GRAY); // Show placeholder color
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (searchField.getText().equals("Search employees...")) {
					searchField.setText(" "); // Please don't remove the space, it breaks the search focus if removed
					searchField.setForeground(Color.BLACK); // Restore default text color
				}
			}
		});

		searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				filterEmployeeList();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				filterEmployeeList();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				filterEmployeeList();
			}
		});

		// "Create Employee" button
		JButton createEmployeeButton = new JButton("Create Employee");
		styleButton(createEmployeeButton);
		createEmployeeButton.addActionListener(_ -> {
			CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
			cardLayout.show(mainPanel, "CreateEmployee");
		});

		// Add Back button, search bar, and Create Employee button to the top panel
		JPanel buttonPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for precise spacing
		buttonPanel.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 10, 0, 10); // Add horizontal padding between components
		gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure components stretch horizontally
		gbc.weighty = 1.0; // Set vertical weight to balance alignment

		// Add Back button
		gbc.gridx = 0;
		gbc.weightx = 0.0; // No horizontal stretch for the button
		buttonPanel.add(backButton, gbc);

		// Add Search field
		gbc.gridx = 1;
		gbc.weightx = 1.0; // Allow the search field to stretch horizontally
		buttonPanel.add(searchField, gbc);

		// Add Create Employee button
		gbc.gridx = 2;
		gbc.weightx = 0.0; // No horizontal stretch for the button
		buttonPanel.add(createEmployeeButton, gbc);

		topPanel.add(buttonPanel, BorderLayout.CENTER);

		// Main employee list panel
		employeePanel = new JPanel();
		employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
		employeePanel.setBackground(Color.WHITE);

		// Retrieve employees from the database
		employees = Employee.getEmployees();
		populateEmployeeList(employees, mainPanel);

		// Scroll pane for the employee list
		JScrollPane scrollPane = new JScrollPane(employeePanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		// Add top panel and scroll pane to main layout
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	// Populate the employee list
	private void populateEmployeeList(List<Employee> employees, JPanel mainPanel) {
		employeePanel.removeAll(); // Clear current list

		for (Employee employee : employees) {
			JPanel employeeInfoPanel = new JPanel();
			employeeInfoPanel.setLayout(new BorderLayout());
			employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			employeeInfoPanel.setBackground(Color.WHITE);

			// Display employee information
			JLabel employeeLabel = new JLabel(
					employee.getId() + " - " + employee.getFirstName() + " " + employee.getLastName());
			employeeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
			employeeLabel.setHorizontalAlignment(SwingConstants.LEFT);
			employeeInfoPanel.add(employeeLabel, BorderLayout.CENTER);

			// "More Info" button
			JButton moreInfoButton = new JButton("More Info");
			styleButton(moreInfoButton);
			moreInfoButton.addActionListener(_ -> {
				// Explicitly transfer focus away from the search field
				searchField.setFocusable(false);
				searchField.setFocusable(true);

				// Navigate to the EmployeeDetailPanel
				EmployeeDetailPanel detailPanel = new EmployeeDetailPanel(employee, mainPanel);
				mainPanel.add(detailPanel, "EmployeeDetail");

				// Use CardLayout to switch panels
				CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
				cardLayout.show(mainPanel, "EmployeeDetail");

				// Revalidate and repaint the main panel
				mainPanel.revalidate();
				mainPanel.repaint();
			});
			employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

			employeePanel.add(employeeInfoPanel);

			// Separator between employees
			JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
			employeePanel.add(separator);
		}

		employeePanel.revalidate();
		employeePanel.repaint();
	}

	// Filter the employee list based on search field input
	private void filterEmployeeList() {
		String query = searchField.getText().trim().toLowerCase();

		// Skip filtering if the search field is empty or showing the placeholder
		if (query.isEmpty() || query.equals("search employees...")) {
			populateEmployeeList(employees, getParentPanel());
			return;
		}

		// Perform filtering based on the query
		List<Employee> filteredEmployees = employees.stream()
				.filter(emp -> emp.getFirstName().toLowerCase().contains(query) ||
						emp.getLastName().toLowerCase().contains(query) ||
						String.valueOf(emp.getId()).contains(query))
				.collect(Collectors.toList());

		populateEmployeeList(filteredEmployees, getParentPanel());
	}

	// Style button
	private Border createRoundedBorder(int radius) {
		return BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(70, 130, 180), 2), // Border color
				BorderFactory.createEmptyBorder(radius, radius * 2, radius, radius * 2) // Padding
		);
	}

	// Style search field with proper rounded corners
	private void styleSearchField(JTextField textField) {
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setBackground(Color.WHITE); // Set background color
		textField.setForeground(Color.BLACK); // Text color
		textField.setOpaque(false); // Allow for custom painting

		// Custom rounded border with higher corner radius
		textField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(70, 130, 180), 4, true), // Rounded border
				BorderFactory.createEmptyBorder(5, 10, 5, 10) // Inner padding
		));

		// Add custom rendering to support highly rounded visuals
		textField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
			@Override
			protected void paintSafely(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(textField.getBackground());

				// Adjust the arc width and height for more rounded corners
				int arcWidth = 30; // Higher value for more rounded corners
				int arcHeight = 30; // Higher value for more rounded corners

				g2.fillRoundRect(0, 0, textField.getWidth(), textField.getHeight(), arcWidth, arcHeight);
				super.paintSafely(g);
			}
		});
	}

	// Style a button for a modern look
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

	// Helper method to retrieve the parent panel (for layout updates)
	private JPanel getParentPanel() {
		return (JPanel) this.getParent();
	}

	// Dynamically refresh the employee list
	private void refreshEmployeeList(JPanel mainPanel) {
		employees = Employee.getEmployees(); // Fetch updated employee list
		populateEmployeeList(employees, mainPanel); // Populate the employee list dynamically
	}
}