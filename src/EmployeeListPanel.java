import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeListPanel extends BasePanel {
	private JPanel employeePanel; // Panel to display employees
	private JTextField searchField; // Search field for filtering employees
	private List<Employee> employees; // Full list of employees

	public EmployeeListPanel(JPanel mainPanel) {
		super(mainPanel); // Pass mainPanel to BasePanel constructor
		initializeContent();
	}

	@Override
	protected void initializeContent() {
		setLayout(new BorderLayout());
		setBackground(WHITE);

		// Add component listener to refresh the list dynamically when shown
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				refreshEmployeeList(); // Fetch and update the employee list dynamically
			}
		});

		// Top panel for search bar and buttons
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		topPanel.setBackground(LIGHT_GRAY);
		topPanel.setOpaque(true);

		// Search bar with placeholder and clear button
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBackground(WHITE);

		searchField = new JTextField(20);
		styleCustomTextField(searchField);
		searchField.setToolTipText("Search by ID, first name, or last name");
		searchField.setText("Search employees...");
		searchField.addFocusListener(new PlaceholderFocusAdapter("Search employees...", searchField));
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

		JButton clearButton = new JButton("X");
		styleClearButton(clearButton);
		clearButton.addActionListener(_ -> {
			searchField.setText("Search employees...");
			searchField.setForeground(Color.GRAY);
			searchField.transferFocus(); // Remove focus from the search field
			refreshEmployeeList(); // Reset the list
		});

		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(clearButton, BorderLayout.EAST);

		// Create Employee button
		JButton createEmployeeButton = new JButton("Create Employee");
		styleButton(createEmployeeButton);
		createEmployeeButton.addActionListener(_ -> navigateToPanel("CreateEmployee"));

		// Arrange search bar and Create Employee button
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(LIGHT_GRAY);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.weightx = 1.0;
		buttonPanel.add(searchPanel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.0;
		buttonPanel.add(createEmployeeButton, gbc);

		topPanel.add(buttonPanel, BorderLayout.CENTER);

		// Main employee list panel
		employeePanel = new JPanel();
		employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
		employeePanel.setBackground(WHITE);

		JScrollPane scrollPane = new JScrollPane(employeePanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		// Add the reusable bottom bar
		BottomBar bottomBar = new BottomBar(mainPanel);
		add(bottomBar, BorderLayout.SOUTH);
	}

	// Helper for placeholder focus behavior
	private static class PlaceholderFocusAdapter extends FocusAdapter {
		private final String placeholder;
		private final JTextField field;

		public PlaceholderFocusAdapter(String placeholder, JTextField field) {
			this.placeholder = placeholder;
			this.field = field;
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (field.getText().equals(placeholder)) {
				field.setText("");
				field.setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (field.getText().isEmpty()) {
				field.setText(placeholder);
				field.setForeground(Color.GRAY);
			}
		}
	}

	// Styling for clear button
	private void styleClearButton(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setForeground(WHITE);
		button.setBackground(ACCENT);
		button.setOpaque(false); // Allows custom painting
		button.setContentAreaFilled(false); // Prevents default painting
		button.setBorderPainted(false); // Prevents default border painting
		button.setPreferredSize(new Dimension(40, 40));
		button.setFocusPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Custom painting for rounded corners only on the right side
		button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Paint the parent background (light_gray) behind the button
				g2.setColor(LIGHT_GRAY);
				g2.fillRect(0, 0, button.getWidth(), button.getHeight());

				// Paint the button background with rounded corners on the right side
				g2.setColor(button.getBackground());
				int arcWidth = 20;
				int arcHeight = 20;

				// Draw rounded corners on the top-right and bottom-right
				g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), arcWidth * 2, arcHeight * 2);

				// Clip the left side to make it squared
				g2.fillRect(0, 0, button.getWidth() - arcWidth, button.getHeight());

				// Draw the text
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(button.getForeground());
				String text = button.getText();
				int textX = (button.getWidth() - fm.stringWidth(text)) / 2;
				int textY = (button.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
				g2.drawString(text, textX, textY);
			}
		});

		// Hover effects
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(ACCENT_LIGHT);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(ACCENT);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				button.setBackground(ACCENT_DARK);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				button.setBackground(ACCENT_LIGHT);
			}
		});
	}

	// Styling for text field
	private void styleCustomTextField(JTextField textField) {
		textField.setFont(new Font("Arial", Font.PLAIN, 16));
		textField.setForeground(Color.GRAY);
		textField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ACCENT, 2, true),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		textField.addFocusListener(new PlaceholderFocusAdapter("Search employees...", textField));
	}

	// Dynamically refresh the employee list
	private void refreshEmployeeList() {
		System.out.println("Refreshing employee list..."); // Debug message for tracking
		try {
			employees = Employee.getEmployees(); // Fetch the latest employee list
			if (employees == null || employees.isEmpty()) {
				showNoEmployeesMessage();
			} else {
				populateEmployeeList(employees);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Failed to fetch employee data. Please try again.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Show a message when there are no employees to display
	private void showNoEmployeesMessage() {
		employeePanel.removeAll(); // Clear the panel
		JLabel noEmployeesLabel = new JLabel("No employees available to display.", SwingConstants.CENTER);
		noEmployeesLabel.setForeground(Color.GRAY);
		noEmployeesLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		employeePanel.add(noEmployeesLabel); // Add the message to the employee panel
		employeePanel.revalidate();
		employeePanel.repaint();
	}

	// Populate the employee list
	private void populateEmployeeList(List<Employee> employees) {
		employeePanel.removeAll(); // Clear the existing list
		for (Employee employee : employees) {
			employeePanel.add(createEmployeeCard(employee)); // Add individual employee cards
			employeePanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator
		}
		employeePanel.revalidate();
		employeePanel.repaint();
	}

	// Create a styled card for each employee
	private JPanel createEmployeeCard(Employee employee) {
		JPanel employeeCard = new JPanel(new BorderLayout());
		employeeCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		employeeCard.setBackground(WHITE); // Use consistent color from BasePanel

		// Employee Label
		JLabel employeeLabel = new JLabel(
				employee.getId() + " - " + employee.getFirstName() + " " + employee.getLastName());

		// More Info Button
		JButton moreInfoButton = new JButton("More Info");
		styleButton(moreInfoButton);
		moreInfoButton.addActionListener(_ -> {
			EmployeeViewPanel viewPanel = findPanelByType(EmployeeViewPanel.class);
			viewPanel.setEmployee(employee);
			navigateToPanel("EmployeeView");
		});

		// Add components to the card
		employeeCard.add(employeeLabel, BorderLayout.CENTER);
		employeeCard.add(moreInfoButton, BorderLayout.EAST);

		return employeeCard;
	}

	// Filter the employee list based on search field input
	private void filterEmployeeList() {
		String query = searchField.getText().trim().toLowerCase();

		// Skip filtering if the search field is empty or showing the placeholder
		if (query.isEmpty() || query.equals("search employees...")) {
			populateEmployeeList(employees);
			return;
		}

		// Perform filtering across multiple fields
		List<Employee> filteredEmployees = employees.stream()
				.filter(emp -> emp.getFirstName().toLowerCase().contains(query) ||
						emp.getLastName().toLowerCase().contains(query) ||
						String.valueOf(emp.getId()).contains(query) ||
						(emp.getDepartment() != null && emp.getDepartment().toLowerCase().contains(query)) ||
						(emp.getPosition() != null && emp.getPosition().toLowerCase().contains(query)))
				.collect(Collectors.toList());

		// Display the filtered list or a "No employees found" message
		if (filteredEmployees.isEmpty()) {
			showNoEmployeesMessage();
		} else {
			populateEmployeeList(filteredEmployees);
		}
	}

	@Override
	protected void refreshContent() {
		refreshEmployeeList(); // Refresh the employee list when the panel is shown
	}
}