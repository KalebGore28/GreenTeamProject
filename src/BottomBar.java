import javax.swing.*;
import java.awt.*;

public class BottomBar extends BasePanel {

    public BottomBar(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Centered buttons with spacing
        setBackground(LIGHT_GRAY);

        // Back Button
        JButton backButton = new JButton();
        styleBottomBarButton(backButton);
        backButton.setIcon(loadIcon("/images/back.png", 24, 24));
        backButton.addActionListener(_ -> handleBackAction());
        add(backButton);

        // Home Button
        JButton homeButton = new JButton();
        styleBottomBarButton(homeButton);
        homeButton.setIcon(loadIcon("/images/home.png", 24, 24));
        homeButton.addActionListener(_ -> navigateToPanel("HomePanel"));
        add(homeButton);

        // Logout Button
        JButton logoutButton = new JButton();
        styleBottomBarButton(logoutButton);
        logoutButton.setIcon(loadIcon("/images/logout.png", 24, 24));
        logoutButton.addActionListener(_ -> handleLogout());
        add(logoutButton);
    }

    // Handles back button action based on AppState
    private void handleBackAction() {
        navigateBack();
    }

    // Handles logout functionality
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            AppState.setCurrentUser(null); // Clear the logged-in user
            navigateToPanel("LoginPanel"); // Redirect to the login panel
        }
    }

    // Local method to style bottom bar buttons
    private void styleBottomBarButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(ACCENT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(50, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_LIGHT);
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT);
                button.repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_DARK);
                button.repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_LIGHT);
                button.repaint();
            }
        });

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 20, 20);

                super.paint(g, c);
            }
        });
    }
}