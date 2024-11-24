import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeAssignedSprintsPanel extends BasePanel {
    private JPanel sprintListPanel; // Panel to dynamically display sprint cards

    public EmployeeAssignedSprintsPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Title
        JLabel titleLabel = new JLabel("My Sprints", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setOpaque(true);
        titleLabel.setForeground(ACCENT);
        add(titleLabel, BorderLayout.NORTH);

        // Sprint list panel (initially empty)
        sprintListPanel = new JPanel();
        sprintListPanel.setLayout(new BoxLayout(sprintListPanel, BoxLayout.Y_AXIS));
        sprintListPanel.setBackground(WHITE);

        // Scroll pane for sprint list
        JScrollPane scrollPane = new JScrollPane(sprintListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        add(bottomBar, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        // Clear the existing content
        sprintListPanel.removeAll();

        // Get the current user
        User currentUser = AppState.getCurrentUser();

        if (currentUser == null) {
            JLabel errorLabel = new JLabel("Error: No user is logged in.", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);
            sprintListPanel.add(errorLabel);
        } else {
            // Fetch assigned sprints
            List<Sprint> assignedSprints = Sprint.getSprintsForEmployee(currentUser.getId());

            if (assignedSprints.isEmpty()) {
                JLabel noSprintsLabel = new JLabel("You are not assigned to any sprints.", SwingConstants.CENTER);
                noSprintsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                noSprintsLabel.setForeground(GRAY);
                sprintListPanel.add(noSprintsLabel);
            } else {
                for (Sprint sprint : assignedSprints) {
                    sprintListPanel.add(createSprintCard(sprint));
                    sprintListPanel.add(Box.createVerticalStrut(10)); // Add spacing between cards
                }
            }
        }

        sprintListPanel.revalidate();
        sprintListPanel.repaint();
    }

    /**
     * Creates a card for a sprint with an "Add Evaluation" button.
     */
    private JPanel createSprintCard(Sprint sprint) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        card.setBackground(WHITE);

        // Panel for sprint and evaluation details
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(WHITE);

        // Sprint name and date
        JLabel nameLabel = new JLabel(sprint.getName(), SwingConstants.LEFT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(BLACK);
        textPanel.add(nameLabel);

        JLabel dateLabel = new JLabel("Start: " + sprint.getStartDate() + " | End: " + sprint.getEndDate(),
                SwingConstants.LEFT);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(GRAY);
        textPanel.add(dateLabel);

        // Add a separator
        textPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        // Show all sprint evaluation info
        SprintEvaluation sprintEvaluation = sprint.getEvaluation(AppState.getCurrentUser().getId());
        if (sprintEvaluation != null) {
            JLabel evaluationTitleLabel = new JLabel("Evaluation", SwingConstants.LEFT);
            evaluationTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            evaluationTitleLabel.setForeground(ACCENT);
            textPanel.add(evaluationTitleLabel);

            JLabel evaluationLabel = new JLabel("Evaluation Date: " + sprintEvaluation.getDate(), SwingConstants.LEFT);
            evaluationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            evaluationLabel.setForeground(GRAY);
            textPanel.add(evaluationLabel);

            JLabel evaluationScoreLabel = new JLabel("Rating: " + sprintEvaluation.getRating(), SwingConstants.LEFT);
            evaluationScoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            evaluationScoreLabel.setForeground(GRAY);
            textPanel.add(evaluationScoreLabel);

            JLabel evaluationCommentLabel = new JLabel("Question 1: " + sprintEvaluation.getComment1(),
                    SwingConstants.LEFT);
            evaluationCommentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            evaluationCommentLabel.setForeground(GRAY);
            textPanel.add(evaluationCommentLabel);

            JLabel evaluationComment2Label = new JLabel("Question 2: " + sprintEvaluation.getComment2(),
                    SwingConstants.LEFT);
            evaluationComment2Label.setFont(new Font("Arial", Font.PLAIN, 14));
            evaluationComment2Label.setForeground(GRAY);
            textPanel.add(evaluationComment2Label);

            JLabel evaluationComment3Label = new JLabel("Question 3: " + sprintEvaluation.getComment3(),
                    SwingConstants.LEFT);
            evaluationComment3Label.setFont(new Font("Arial", Font.PLAIN, 14));
            evaluationComment3Label.setForeground(GRAY);
            textPanel.add(evaluationComment3Label);
        }

        // Dynamic button
        JButton evaluationButton = new JButton();
        styleButton(evaluationButton);

        // Check if evaluation exists for the current user and sprint
        User currentUser = AppState.getCurrentUser();
        if (currentUser != null && sprint.getEvaluation(currentUser.getId()) != null) {
            evaluationButton.setText("Edit Evaluation");
            evaluationButton.addActionListener(_ -> {
                // Logic for editing evaluation
                SprintEvaluationEditPanel evaluationEditPanel = findPanelByType(SprintEvaluationEditPanel.class);
                evaluationEditPanel.setSprintAndEvaluation(sprint, sprintEvaluation);
                navigateToPanel("SprintEvaluationEdit");
            });
        } else {
            evaluationButton.setText("Add Evaluation");
            evaluationButton.addActionListener(_ -> {
                // Logic for adding evaluation
                SprintEvaluationEditPanel evaluationEditPanel = findPanelByType(SprintEvaluationEditPanel.class);
                evaluationEditPanel.setSprintAndEvaluation(sprint, null);
                navigateToPanel("SprintEvaluationEdit");
            });
        }

        // Layout for the card
        card.add(textPanel, BorderLayout.CENTER);
        card.add(evaluationButton, BorderLayout.EAST);

        return card;
    }
}