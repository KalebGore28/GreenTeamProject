import javax.swing.*;

import java.awt.*;

public abstract class BasePanel extends JPanel {
	protected JPanel mainPanel; // Reference to the main container with CardLayout

	// Primary Colors
	public static final Color PRIMARY = new Color(131, 17, 0); // Dark Red
	public static final Color PRIMARY_LIGHT = new Color(200, 19, 2); // Lighter Red
	public static final Color PRIMARY_DARK = new Color(30, 10, 1); // Darkest Red

	// Accent Colors
	public static final Color ACCENT = new Color(0, 114, 131); // Teal
	public static final Color ACCENT_LIGHT = new Color(14, 180, 200); // Lighter Teal
	public static final Color ACCENT_DARK = new Color(14, 90, 100); // Darker Teal

	// Semantic Colors
	public static final Color SUCCESS = new Color(0, 159, 66); // Green for success
	public static final Color WARNING = new Color(240, 173, 78); // Yellow for warnings
	public static final Color DANGER = new Color(180, 28, 43); // Red for errors
	public static final Color INFO = new Color(56, 140, 250); // Blue for info

	// Neutral Colors
	public static final Color WHITE = new Color(255, 255, 255); // Pure white
	public static final Color LIGHT_GRAY = new Color(235, 235, 235); // Light gray
	public static final Color GRAY = new Color(145, 145, 145); // Medium gray
	public static final Color DARK_GRAY = new Color(94, 94, 94); // Dark gray
	public static final Color BLACK = new Color(0, 0, 0); // Pure black

	public BasePanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
		setLayout(new BorderLayout()); // Default layout
		setBackground(Color.WHITE); // Default background color
	}

	// Common method to style a button
	protected void styleButton(JButton button) {
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.setForeground(Color.WHITE); // White text for contrast
		button.setBackground(ACCENT); // Steel Blue background
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(false); // Prevent default fill
		button.setOpaque(false); // Ensure custom painting is applied

		// Hover effects
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(ACCENT_LIGHT); // Change background on hover
				button.repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(ACCENT); // Reset background
				button.repaint();
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				button.setBackground(ACCENT_DARK); // Darken background on click
				button.repaint();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				button.setBackground(ACCENT_LIGHT); // Reset background after click
				button.repaint();
			}
		});

		// Custom painting for rounded corners
		button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Paint rounded background
				g2.setColor(button.getBackground());
				g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 20, 20);

				// Paint button text
				FontMetrics fm = g2.getFontMetrics();
				g2.setColor(button.getForeground());
				String text = button.getText();
				int textX = (button.getWidth() - fm.stringWidth(text)) / 2;
				int textY = (button.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
				g2.drawString(text, textX, textY);
			}
		});
	}

	// Common method to style a label
	protected void styleLabel(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 20)); // Slightly larger and bold for better visibility
		label.setForeground(BLACK); // Use the predefined text color for consistency
		label.setHorizontalAlignment(SwingConstants.CENTER); // Center-align by default
		label.setOpaque(true); // Allow background to be painted
		label.setBackground(ACCENT); // Add a subtle background color for contrast
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding for a cleaner look
	}

	// Common method to style a text field
	protected void styleTextField(JTextField textField) {
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setBackground(Color.WHITE); // Set background color
		textField.setForeground(Color.BLACK); // Text color
		textField.setOpaque(true); // Ensure custom painting applies

		// Apply a rounded border with padding
		textField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ACCENT_DARK, 2, true), // Rounded border with primary color
				BorderFactory.createEmptyBorder(5, 10, 5, 10) // Inner padding
		));

		// Custom UI for rounded corners
		textField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
			@Override
			protected void paintSafely(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(textField.getBackground());

				// Draw rounded rectangle
				int arcWidth = 20, arcHeight = 20; // Rounded corner dimensions
				g2.fillRoundRect(0, 0, textField.getWidth(), textField.getHeight(), arcWidth, arcHeight);

				super.paintSafely(g); // Paint the text and caret
			}
		});
	}

	// Common method to load an icon and scale it to the specified dimensions
	protected ImageIcon loadIcon(String path, int width, int height) {
		// Load the resource
		java.net.URL iconURL = getClass().getResource(path);
		if (iconURL == null) {
			System.err.println("Icon not found: " + path);
			return null; // Return null if the resource doesn't exist
		}

		// Scale the icon
		ImageIcon icon = new ImageIcon(iconURL);
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImg);
	}

	// Common method to navigate between panels
	protected synchronized void navigateToPanel(String panelName) {
		// Avoid redundant navigation to the current panel
		if (panelName.equals(AppState.getCurrentPanelName())) {
			return;
		}

		// Set the current panel in AppState
		AppState.setCurrentPanelName(panelName);

		// Refresh the content of the panel being navigated to
		Component[] components = mainPanel.getComponents();
		for (Component component : components) {
			if (component instanceof BasePanel && panelName.equals(AppState.getCurrentPanelName())) {
				((BasePanel) component).refreshContent();
			}
		}

		// Navigate to the target panel
		CardLayout layout = (CardLayout) mainPanel.getLayout();
		layout.show(mainPanel, panelName);
	}

	// Common method to find panel by name
	@SuppressWarnings("unchecked")
	protected <T extends Component> T findPanelByType(Class<T> panelClass) {
		for (Component component : mainPanel.getComponents()) {
			if (panelClass.isInstance(component)) {
				return (T) component; // Return the panel if it's of the correct type
			}
		}
		throw new IllegalArgumentException("Panel of type " + panelClass.getSimpleName() + " not found.");
	}

	// Default implementation for refreshing content, can be overridden
	protected void refreshContent() {
	}

	// Abstract method to define custom content for child panels
	protected abstract void initializeContent();
}