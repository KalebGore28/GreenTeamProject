import javax.swing.*;
import java.awt.*;

public class HomePanel extends BasePanel {

    private JPanel contentPanel; // Panel for dynamic content

    public HomePanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header Panel for the Welcome Message
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LIGHT_GRAY); // Steel Blue background

        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE); // White text
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Content Panel for cards
        contentPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // Vertical layout with spacing
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove border for cleaner look
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        add(scrollPane, BorderLayout.CENTER);

        // Bottom Bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        contentPanel.removeAll(); // Clear existing components

        User currentUser = AppState.getCurrentUser();
        if (currentUser == null) {
            navigateToPanel("LoginPanel");
            return;
        }

        // Update the welcome message in the header
        String welcomeMessage = "Welcome, " + currentUser.getName() + "!";
        JLabel welcomeLabel = (JLabel) ((JPanel) getComponent(0)).getComponent(0);
        welcomeLabel.setText(welcomeMessage);
        welcomeLabel.setForeground(ACCENT);


        // Add user-specific cards
        if (currentUser instanceof Employee) {
            JButton tasksButton = new JButton("My Tasks");
            styleButton(tasksButton);
            tasksButton.addActionListener(_ -> {
                EmployeeTasksPanel employeeTasksPanel = findPanelByType(EmployeeTasksPanel.class);
                employeeTasksPanel.setEmployee((Employee) currentUser);
                navigateToPanel("EmployeeTasks");
            });
            contentPanel.add(tasksButton);

            JButton employeeViewButton = new JButton("My Info");
            styleButton(employeeViewButton);
            employeeViewButton.addActionListener(_ -> {
                EmployeeViewPanel employeeViewPanel = findPanelByType(EmployeeViewPanel.class);
                employeeViewPanel.setEmployee((Employee) currentUser);
                navigateToPanel("EmployeeView");
            });
            contentPanel.add(employeeViewButton);

        } else if (currentUser instanceof Supervisor) {
            contentPanel.add(createCard("Manage Employees", "EmployeeList"));
            contentPanel.add(createCard("Manage Sprints", "SprintList"));
        }

        // Update the UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Creates a styled card (button) with the specified label and target panel.
     *
     * @param label     The text to display on the card.
     * @param panelName The name of the panel to navigate to when clicked.
     * @return The styled JButton.
     */
    private JButton createCard(String label, String panelName) {
        JButton button = new JButton(label);
        styleButton(button);
        button.addActionListener(_ -> navigateToPanel(panelName));
        return button;
    }

}