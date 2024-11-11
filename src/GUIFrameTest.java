import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class GUIFrameTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Employee View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.setLayout(new BorderLayout());

		// Main panel that switches between list view and detail view
		JPanel mainPanel = new JPanel(new CardLayout());
		JPanel employeeListPanel = createEmployeeListPanel(frame, mainPanel);

		mainPanel.add(employeeListPanel, "EmployeeList");

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	// Create the employee list panel
	private static JPanel createEmployeeListPanel(JFrame frame, JPanel mainPanel) {
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
			employeeInfoPanel.add(employeeLabel, BorderLayout.WEST);

			// "More Info" button
			JButton moreInfoButton = new JButton("More Info");
			moreInfoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel detailPanel = createEmployeeDetailPanel(employee, mainPanel);
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

		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(scrollPane, BorderLayout.CENTER);

		return listPanel;
	}

	// Create the employee detail panel
	private static JPanel createEmployeeDetailPanel(Employee employee, JPanel mainPanel) {
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
		detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Display detailed information of the employee
		JLabel idLabel = new JLabel("ID: " + employee.getId());
		JLabel nameLabel = new JLabel("Name: " + employee.getFirstName() + " " + employee.getLastName());
		JLabel emailLabel = new JLabel("Email: " + employee.getEmail());
		JLabel departmentLabel = new JLabel("Department: " + employee.getDepartment());
		JLabel positionLabel = new JLabel("Position: " + employee.getPosition());
		JLabel salaryLabel = new JLabel("Salary: " + employee.getSalary());

		detailPanel.add(idLabel);
		detailPanel.add(nameLabel);
		detailPanel.add(emailLabel);
		detailPanel.add(departmentLabel);
		detailPanel.add(positionLabel);
		detailPanel.add(salaryLabel);

		// "Back" button to return to the employee list
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
				cardLayout.show(mainPanel, "EmployeeList");
			}
		});
		detailPanel.add(backButton);

		return detailPanel;
	}
}