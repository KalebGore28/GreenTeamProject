import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class SprintListPanel extends BasePanel {
	private JPanel sprintPanel; // Panel to display sprints
	private JTextField searchField; // Search field for filtering sprints
	private List<Sprint> sprints; // Full list of sprints
	private Sprint activeSprint; // Currently active sprint

	public SprintListPanel(JPanel mainPanel) {
		super(mainPanel);
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
				refreshSprintList(); // Fetch and update the sprint list dynamically
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
		searchField.setToolTipText("Search by Sprint ID or Name");
		searchField.setText("Search sprints...");
		searchField.addFocusListener(new PlaceholderFocusAdapter("Search sprints...", searchField));
		searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				filterSprintList();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				filterSprintList();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				filterSprintList();
			}
		});

		JButton clearButton = new JButton("X");
		styleClearButton(clearButton);
		clearButton.addActionListener(_ -> {
			searchField.setText("Search sprints...");
			searchField.setForeground(Color.GRAY);
			searchField.transferFocus(); // Remove focus from the search field
			refreshSprintList(); // Reset the list
		});

		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(clearButton, BorderLayout.EAST);

		// Create Employee button
		JButton createEmployeeButton = new JButton("Create Sprint");
		styleButton(createEmployeeButton);
		createEmployeeButton.addActionListener(_ -> navigateToPanel("CreateSprint"));

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

		// Main sprint list panel
		sprintPanel = new JPanel();
		sprintPanel.setLayout(new BoxLayout(sprintPanel, BoxLayout.Y_AXIS));
		sprintPanel.setBackground(WHITE);

		JScrollPane scrollPane = new JScrollPane(sprintPanel);
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
		textField.addFocusListener(new PlaceholderFocusAdapter("Search sprints...", textField));
	}

	// Refresh the sprint list dynamically
	private void refreshSprintList() {
		try {
			sprints = Sprint.getSprints(); // Fetch the latest sprint list
			if (sprints == null || sprints.isEmpty()) {
				showNoSprintsMessage();
			} else {
				populateSprintList(sprints);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Failed to fetch sprint data. Please try again.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Show a message when there are no sprints to display
	private void showNoSprintsMessage() {
		sprintPanel.removeAll(); // Clear the panel
		JLabel noSprintsLabel = new JLabel("No sprints available to display.", SwingConstants.CENTER);
		noSprintsLabel.setForeground(Color.GRAY);
		noSprintsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		sprintPanel.add(noSprintsLabel); // Add the message to the sprint panel
		sprintPanel.revalidate();
		sprintPanel.repaint();
	}

	// Populate the sprint list
	private void populateSprintList(List<Sprint> sprints) {
		sprintPanel.removeAll(); // Clear the existing list
		for (Sprint sprint : sprints) {
			sprintPanel.add(createSprintCard(sprint)); // Add individual sprint cards
			sprintPanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator
		}
		sprintPanel.revalidate();
		sprintPanel.repaint();
	}

	// Create a styled card for each sprint
	private JPanel createSprintCard(Sprint sprint) {
		JPanel sprintCard = new JPanel(new BorderLayout());
		sprintCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		sprintCard.setBackground(WHITE);

		String status = sprint.getId() == activeSprint.getId() ? "(Active)" : sprint.getId() < activeSprint.getId() ? "(Completed)" : "(Upcoming)";

		JLabel sprintLabel = new JLabel(sprint.getId() + " - " + sprint.getName() + " - " + status);

		if(status == "(Active)") {
			sprintLabel.setForeground(ACCENT);
			sprintLabel.setFont(new Font("Arial", Font.BOLD, 16));
		} else if(status == "(Completed)") {
			sprintLabel.setForeground(Color.GRAY);
		} else {
			sprintLabel.setForeground(Color.BLACK);
		}

		JButton moreInfoButton = new JButton("More Info");
		styleButton(moreInfoButton);
		moreInfoButton.addActionListener(_ -> {
			SprintViewPanel viewPanel = findPanelByType(SprintViewPanel.class);
			viewPanel.setSprint(sprint);
			navigateToPanel("SprintView");
		});

		sprintCard.add(sprintLabel, BorderLayout.CENTER);
		sprintCard.add(moreInfoButton, BorderLayout.EAST);

		return sprintCard;
	}

	// Filter the sprint list based on search input
	private void filterSprintList() {
		String query = searchField.getText().trim().toLowerCase();

		if (query.isEmpty() || query.equals("search sprints...")) {
			populateSprintList(sprints);
			return;
		}

		List<Sprint> filteredSprints = sprints.stream()
				.filter(sprint -> sprint.getName().toLowerCase().contains(query) ||
						String.valueOf(sprint.getId()).contains(query))
				.collect(Collectors.toList());

		if (filteredSprints.isEmpty()) {
			showNoSprintsMessage();
		} else {
			populateSprintList(filteredSprints);
		}
	}

	@Override
	protected void refreshContent() {
		activeSprint = Sprint.getActiveSprint(); // Fetch the current sprint
		refreshSprintList(); // Refresh the sprint list when the panel is shown
	}
}