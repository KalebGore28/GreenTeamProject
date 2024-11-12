import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeListPanel extends JPanel {
	public EmployeeListPanel(JPanel mainPanel) {
		setLayout(new BorderLayout());

		// Create a panel with a BoxLayout for vertically stacking employee entries
		JPanel employeePanel = new JPanel();
		employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

		// Retrieve employees from the database
		List<Employee> employees = Employee.getEmployees();

		// Populate the panel with employee data and add a "More Info" button
		for (Employee employee : employees) {
			JPanel employeeInfoPanel = new JPanel();
			employeeInfoPanel.setLayout(new BorderLayout());
			employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			// Display employee basic information
			JLabel employeeLabel = new JLabel(
					employee.getId() + " \t " + employee.getFirstName() + " " + employee.getLastName());
			employeeLabel.setHorizontalAlignment(SwingConstants.LEFT);
			employeeInfoPanel.add(employeeLabel, BorderLayout.CENTER);

			// "More Info" button
			JButton moreInfoButton = new JButton("More Info");
			moreInfoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EmployeeDetailPanel detailPanel = new EmployeeDetailPanel(employee, mainPanel);
					mainPanel.add(detailPanel, "EmployeeDetail");
					CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
					cardLayout.show(mainPanel, "EmployeeDetail");
				}
			});
			employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

			employeePanel.add(employeeInfoPanel);

			// Add a horizontal separator between employees
			JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
			employeePanel.add(separator);
		}

		// Add scroll pane to make the list scrollable
		JScrollPane scrollPane = new JScrollPane(employeePanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set the scroll speed

		add(scrollPane, BorderLayout.CENTER);
	}
}