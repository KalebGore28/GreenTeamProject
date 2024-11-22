import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public abstract class BasePanel extends JPanel {
	protected JPanel mainPanel; // Reference to the main container with CardLayout

	public BasePanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
		setLayout(new BorderLayout()); // Default layout
		setBackground(Color.WHITE); // Default background color
	}

	// Common method to style a button
	protected void styleButton(JButton button) {
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

	// Common method to create a rounded border
	protected Border createRoundedBorder(int radius) {
		return BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1), // Outer border
				BorderFactory.createEmptyBorder(radius, radius, radius, radius) // Padding
		);
	}

	// Common method to style a label
	protected void styleLabel(JLabel label) {
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setForeground(Color.BLACK); // Black text for contrast
	}

	// Common method to style a text field
	protected void styleTextField(JTextField textField) {
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

	// Common method to navigate between panels
	protected void navigateToPanel(String panelName) {
		CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		cardLayout.show(mainPanel, panelName);
	}

	// Abstract method to define custom content for child panels
	protected abstract void initializeContent();
}