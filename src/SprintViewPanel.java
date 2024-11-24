import javax.swing.*;
import java.awt.*;

public class SprintViewPanel extends BasePanel {
    private Sprint sprint; // The sprint being viewed

    public SprintViewPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Title
        JLabel titleLabel = new JLabel("Sprint Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel for sprint details
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // Add Sprint Information
        JLabel sprintInfoLabel = new JLabel();
        sprintInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        sprintInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sprintInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(sprintInfoLabel);

        // Separator
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(new JSeparator());

        // Cards for Assigned Employees and Evaluations
        contentPanel.add(createOptionCard("Assigned Employees", () -> {
            SprintAssignedEmployeesPanel assignedPanel = findPanelByType(SprintAssignedEmployeesPanel.class);
            assignedPanel.setSprint(sprint);
            navigateToPanel("SprintAssignedEmployees");
        }));

        contentPanel.add(Box.createVerticalStrut(20)); // Add spacing between cards

        contentPanel.add(createOptionCard("Sprint Evaluations", () -> {
            SprintEvaluationsPanel evaluationsPanel = findPanelByType(SprintEvaluationsPanel.class);
            evaluationsPanel.setSprint(sprint);
            navigateToPanel("SprintEvaluations");
        }));

        // Add the reusable bottom bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (sprint != null) {
            // Refresh sprint details
            String sprintInfo = String.format(
                "<html><b>Name:</b> %s<br><b>Start Date:</b> %s<br><b>End Date:</b> %s<br><b>Status:</b> %s<br><b>Velocity:</b> %d</html>",
                sprint.getName(), sprint.getStartDate(), sprint.getEndDate(), sprint.getStatus(), sprint.getVelocity()
            );

            ((JLabel) ((JPanel) getComponent(1)).getComponent(0)).setText(sprintInfo);
        }
    }

    /**
     * Helper to create a card for navigation options.
     */
    private JPanel createOptionCard(String title, Runnable onClick) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(ACCENT);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_LIGHT),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Title label
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(WHITE);

        card.add(titleLabel, BorderLayout.CENTER);

        // Add click listener
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(ACCENT_LIGHT);
                card.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(ACCENT);
                card.repaint();
            }
        });

        return card;
    }
}