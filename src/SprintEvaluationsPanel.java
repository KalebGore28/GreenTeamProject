import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SprintEvaluationsPanel extends BasePanel {
	private Sprint sprint; // The sprint whose evaluations are displayed
	private JPanel evaluationPanel; // Panel to hold the evaluation cards

	public SprintEvaluationsPanel(JPanel mainPanel) {
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

		// Header
		JLabel titleLabel = new JLabel("Sprint Evaluations", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		titleLabel.setBackground(LIGHT_GRAY);
		titleLabel.setForeground(ACCENT);
		titleLabel.setOpaque(true);
		add(titleLabel, BorderLayout.NORTH);

		// Evaluation list panel
		evaluationPanel = new JPanel();
		evaluationPanel.setLayout(new BoxLayout(evaluationPanel, BoxLayout.Y_AXIS));
		evaluationPanel.setBackground(WHITE);

		// Scroll pane for evaluation list
		JScrollPane scrollPane = new JScrollPane(evaluationPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		add(scrollPane, BorderLayout.CENTER);

		// Add reusable bottom bar
		BottomBar bottomBar = new BottomBar(mainPanel);
		add(bottomBar, BorderLayout.SOUTH);
	}

	@Override
	protected void refreshContent() {
		evaluationPanel.removeAll();

		if (sprint != null) {
			List<SprintEvaluation> evaluations = sprint.getEvaluations();

			if (evaluations.isEmpty()) {
				JLabel noEvaluationsLabel = new JLabel("No evaluations available for this sprint.",
						SwingConstants.CENTER);
				noEvaluationsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
				noEvaluationsLabel.setForeground(GRAY);
				evaluationPanel.add(noEvaluationsLabel);
			} else {
				for (SprintEvaluation evaluation : evaluations) {
					evaluationPanel.add(createEvaluationCard(evaluation));
					evaluationPanel.add(Box.createVerticalStrut(10)); // Add spacing between cards
				}
			}
		} else {
			JLabel errorLabel = new JLabel("Error: Sprint not set.", SwingConstants.CENTER);
			errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
			errorLabel.setForeground(Color.RED);
			evaluationPanel.add(errorLabel);
		}

		evaluationPanel.revalidate();
		evaluationPanel.repaint();
	}

	/**
	 * Helper to create a card for an evaluation.
	 */
	private JPanel createEvaluationCard(SprintEvaluation evaluation) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(ACCENT),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// Evaluation info with questions as labels
		JLabel evaluationInfo = new JLabel(
				String.format("<html>"
						+ "<b>Evaluator:</b> %s<br>"
						+ "<b>Date:</b> %s<br>"
						+ "<b>Rating:</b> %d<br>"
						+ "<b>What did you accomplish this sprint?</b><br>%s<br>"
						+ "<b>What is left to accomplish?</b><br>%s<br>"
						+ "<b>Are there any further comments?</b><br>%s"
						+ "</html>",
						Employee.getEmployee(evaluation.getEmployeeId()).getFirstName() + " "
								+ Employee.getEmployee(evaluation.getEmployeeId()).getLastName(),
						evaluation.getDate(),
						evaluation.getRating(),
						evaluation.getComment1(),
						evaluation.getComment2(),
						evaluation.getComment3()));
		evaluationInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		card.add(evaluationInfo, BorderLayout.CENTER);
		return card;
	}
}