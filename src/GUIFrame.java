import java.awt.*;
import javax.swing.*;

public class GUIFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Employee View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(540, 960);

		// Main panel that switches between list view and detail view
		JPanel mainPanel = new JPanel(new CardLayout());

		// Add the EmployeeListPanel
		EmployeeListPanel listPanel = new EmployeeListPanel(mainPanel);
		mainPanel.add(listPanel, "EmployeeList");

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}