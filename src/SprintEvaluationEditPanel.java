import javax.swing.*;
import java.awt.*;

public class SprintEvaluationEditPanel extends BasePanel {
    private Sprint sprint;
    private SprintEvaluation evaluation;
    private JTextField dateField, ratingField;
    private JTextArea comment1Area, comment2Area, comment3Area;

    public SprintEvaluationEditPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setSprintAndEvaluation(Sprint sprint, SprintEvaluation evaluation) {
        this.sprint = sprint;
        this.evaluation = evaluation;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Edit Sprint Evaluation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        dateField = new JTextField(20);
        styleTextField(dateField);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        // Rating
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Rating (1-5):"), gbc);
        ratingField = new JTextField(20);
        styleTextField(ratingField);
        gbc.gridx = 1;
        formPanel.add(ratingField, gbc);

        // Comment 1
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("What did you accomplish this sprint?"), gbc);
        comment1Area = new JTextArea(3, 20);
        styleTextArea(comment1Area);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(comment1Area), gbc);

        // Comment 2
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("What is left to accomplish?"), gbc);
        comment2Area = new JTextArea(3, 20);
        styleTextArea(comment2Area);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(comment2Area), gbc);

        // Comment 3
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Are there any further comments?"), gbc);
        comment3Area = new JTextArea(3, 20);
        styleTextArea(comment3Area);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(comment3Area), gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(WHITE);

        JButton saveButton = new JButton("Save");
        styleButton(saveButton);
        saveButton.addActionListener(_ -> saveEvaluation());

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(_ -> navigateBack());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (evaluation != null) {
            dateField.setText(evaluation.getDate());
            ratingField.setText(String.valueOf(evaluation.getRating()));
            comment1Area.setText(evaluation.getComment1());
            comment2Area.setText(evaluation.getComment2());
            comment3Area.setText(evaluation.getComment3());
        } else {
            dateField.setText("");
            ratingField.setText("");
            comment1Area.setText("");
            comment2Area.setText("");
            comment3Area.setText("");
        }
    }

    private void saveEvaluation() {
        try {
            // Validate and save evaluation
            String date = dateField.getText();
            int rating = Integer.parseInt(ratingField.getText());
            String comment1 = comment1Area.getText();
            String comment2 = comment2Area.getText();
            String comment3 = comment3Area.getText();

            if (evaluation == null) {
                // Save new evaluation
                sprint.newEvaluation(AppState.getCurrentUser().getId(), date, rating, comment1, comment2, comment3);
            } else {
                // Update existing evaluation
                evaluation.setDate(date);
                evaluation.setRating(rating);
                evaluation.setComment1(comment1);
                evaluation.setComment2(comment2);
                evaluation.setComment3(comment3);

                sprint.updateEvaluation(evaluation);
            }

            JOptionPane.showMessageDialog(this, "Evaluation saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            navigateBack();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save evaluation. Please check your input.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_DARK, 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
}