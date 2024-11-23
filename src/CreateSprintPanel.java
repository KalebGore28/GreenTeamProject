import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateSprintPanel extends BasePanel {
    public CreateSprintPanel(JPanel mainPanel) {
        super(mainPanel); // Pass mainPanel to BasePanel constructor
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title for the panel
        JLabel titleLabel = new JLabel("Create New Sprint", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel for input fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add fields with placeholders
        addFormField(formPanel, gbc, "Name:", "Enter sprint name...");
        addFormField(formPanel, gbc, "Start Date (YYYY-MM-DD):", "Enter start date...");
        addFormField(formPanel, gbc, "End Date (YYYY-MM-DD):", "Enter end date...");
        addFormField(formPanel, gbc, "Status:", "Enter status...");
        addFormField(formPanel, gbc, "Velocity:", "Enter velocity...");

        // Add form panel to the center
        add(formPanel, BorderLayout.CENTER);

        // Submit and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(_ -> {
            // Validation and saving logic
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(_ -> navigateBack());

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, String placeholder) {
        // Add the label
        JLabel label = new JLabel(labelText);
        styleLabel(label); // Use the existing method to style the label
        gbc.gridx = 0; // Start a new row
        gbc.gridy++; // Move to the next row for the label
        gbc.gridwidth = 2; // Span across the full width
        panel.add(label, gbc);

        // Add the text field
        JTextField textField = createCustomTextField(placeholder); // Create the custom-styled text field
        gbc.gridy++; // Move to the next row for the text field
        gbc.gridwidth = 2; // Span across the full width
        panel.add(textField, gbc);
    }

    private JTextField createCustomTextField(String placeholder) {
        JTextField textField = new JTextField(20);

        // Set the font and foreground color
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(Color.DARK_GRAY);

        // Set a placeholder-like effect
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        // Apply custom styling for the border and padding
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 150, 200), 2, true), // Rounded border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));

        // Ensure smooth corners by overriding painting
        textField.setOpaque(false);
        textField.setUI(new javax.swing.plaf.basic.BasicTextFieldUI() {
            @Override
            protected void paintSafely(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, textField.getWidth(), textField.getHeight(), 20, 20); // Rounded background
                super.paintSafely(g); // Draw text and caret
            }
        });

        return textField;
    }
}