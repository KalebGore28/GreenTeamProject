import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class GUIframeworking {

      //Info for the team:
    //The idea is to put together the click buttons first, and individual windows
    //then to make it work with the information stored by other methods already in place 

    //also going to focus on function before form 

    //TODO add first window to say I am a supervisor(button)/employee(other button)

    
    //Reminders for me:
    //TODO figure out how to position things in the window
     //TODO create delete employee button
    //textboxes - editable
    //saving over information that already is displayed/exists
    //pulling employee information from other classes to display it 
    //going backwards through windows - add functional back buttons 
    //add possible home button at the bottom of screen that returns to first window
    //add profile button that pulls up personal info? potential future possibilities application 
    //employee.getEmployees is a list, can do .size, find a way to make that many clickbuttons 
    //can filter by how many employees are just working here 
    
    //primarily gonna be working with employee.java and sprint.java

    // Default sizes for components
    private static final int DEFAULT_FRAME_WIDTH = 540;
    private static final int DEFAULT_FRAME_HEIGHT = 960;
    private static final int DEFAULT_BUTTON_WIDTH = 500;
    private static final int DEFAULT_BUTTON_HEIGHT = 20;

    // Main frames
    private static JFrame frame;
    private static JFrame employeeWindow;
    private static JFrame sprintEvalWindow;

    public static void main(String[] args) {
        // Initialize frames
        frame = new JFrame("GUI framework");
        employeeWindow = new JFrame("Employee View");
        sprintEvalWindow = new JFrame("Sprint Eval Window");

        // Set default close operation and size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        
        // Main canvas with vertical layout
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        // Create and add main buttons
        JButton employeeButton = createButton("Employee");
        JButton sprintEvalButton = createButton("SPRINT Evaluations");

        // Set action listeners to open respective windows
        employeeButton.addActionListener(e -> openEmployeeWindow());
        sprintEvalButton.addActionListener(e -> openSprintEvalWindow(canvas));

        // Add buttons to canvas
        canvas.add(employeeButton);
        canvas.add(sprintEvalButton);

        // Add canvas to frame and display
        frame.add(canvas);
        frame.setVisible(true);
    }

    // Utility method to create buttons with consistent size
    private static JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        return button;
    }

    // Opens the Employee Window, showing a list of employees
    private static void openEmployeeWindow() {
        JFrame frame = new JFrame("Employee View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        // Main panel that will contain employee list and other components
        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel employeeListPanel = createEmployeeListPanel(mainPanel);

        mainPanel.add(employeeListPanel, "EmployeeList");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Creates the Employee List Panel with a list of employees and "More Info" buttons
    private static JPanel createEmployeeListPanel(JPanel mainPanel) {
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        // Fetch list of employees from Employee class
        List<Employee> employees = Employee.getEmployees();

        // Add each employee's info to the list
        for (Employee employee : employees) {
            JPanel employeeInfoPanel = new JPanel(new BorderLayout());
            employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Display basic employee information
            JLabel employeeLabel = new JLabel(employee.getId() + " \t " + employee.getFirstName() + " " + employee.getLastName());
            employeeInfoPanel.add(employeeLabel, BorderLayout.WEST);

            // Add "More Info" button to view detailed employee info
            JButton moreInfoButton = new JButton("More Info");
            moreInfoButton.addActionListener(e -> openIndividualEmployeeWindow(employee));
            employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

            // Add employee info panel and separator
            employeePanel.add(employeeInfoPanel);
            employeePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        // Wrap in a scroll pane for easier navigation
        JScrollPane scrollPane = new JScrollPane(employeePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);
        return listPanel;
    }

    // Opens a new window with detailed information about the selected employee
    private static void openIndividualEmployeeWindow(Employee employee) {
        JFrame employeeDetailWindow = new JFrame(employee.getFirstName() + " " + employee.getLastName());
        employeeDetailWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        employeeDetailWindow.setVisible(true);

        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        // Display detailed information about the employee
        JLabel nameLabel = new JLabel("Name: " + employee.getFirstName() + " " + employee.getLastName());
        JLabel emailLabel = new JLabel("Email: " + employee.getEmail());
        JLabel departmentLabel = new JLabel("Department: " + employee.getDepartment());
        JLabel positionLabel = new JLabel("Position: " + employee.getPosition());
        JLabel salaryLabel = new JLabel("Salary: $" + String.format("%.2f", employee.getSalary()));

        // Button to edit employee details
        JButton editEmployeeButton = createButton("Edit Employee");
        editEmployeeButton.addActionListener(editEmployeeActionListener(employee));

        // Add components to the panel
        canvas.add(nameLabel);
        canvas.add(emailLabel);
        canvas.add(departmentLabel);
        canvas.add(positionLabel);
        canvas.add(salaryLabel);
        canvas.add(editEmployeeButton);

        employeeDetailWindow.add(canvas);
        employeeDetailWindow.revalidate();
        employeeDetailWindow.repaint();
    }

    // Action listener for editing an employee's details
    private static ActionListener editEmployeeActionListener(Employee employee) {
        return e -> {
            JFrame editEmployeeWindow = new JFrame("Edit Employee");
            editEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
            editEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Form panel to hold employee information fields
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

            // TODO: Double-check if all fields are consistent with Employee attributes
            // Create text fields for each editable field
            JTextField firstNameField = new JTextField(employee.getFirstName(), 20);
            JTextField lastNameField = new JTextField(employee.getLastName(), 20);
            JTextField emailField = new JTextField(employee.getEmail(), 20);
            JTextField departmentField = new JTextField(employee.getDepartment(), 20);
            JTextField positionField = new JTextField(employee.getPosition(), 20);
            JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()), 20);

            // Add labels and text fields to form panel
            formPanel.add(new JLabel("First Name:"));
            formPanel.add(firstNameField);
            formPanel.add(new JLabel("Last Name:"));
            formPanel.add(lastNameField);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(emailField);
            formPanel.add(new JLabel("Department:"));
            formPanel.add(departmentField);
            formPanel.add(new JLabel("Position:"));
            formPanel.add(positionField);
            formPanel.add(new JLabel("Salary:"));
            formPanel.add(salaryField);

            // Save changes button with validation
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(saveEvent -> {
                // Validate email format
                String email = emailField.getText();
                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(editEmployeeWindow, "Invalid email format. Email must contain '@'.");
                    return;
                }

                try {
                    // Parse and update employee details
                    double salary = Double.parseDouble(salaryField.getText());
                    employee.setFirstName(firstNameField.getText());
                    employee.setLastName(lastNameField.getText());
                    employee.setEmail(email);
                    employee.setDepartment(departmentField.getText());
                    employee.setPosition(positionField.getText());
                    employee.setSalary(salary);

                    // Save employee data and refresh list
                    Employee.saveEmployee(employee);
                    editEmployeeWindow.dispose();
                    refreshEmployeeList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editEmployeeWindow, "Invalid salary input. Please enter a number.");
                }
            });

            formPanel.add(saveButton);
            editEmployeeWindow.add(formPanel);
            editEmployeeWindow.setVisible(true);
        };
    }

    // Opens the Sprint Evaluation window (TODO: Add actual sprint evaluation functionality)
    private static void openSprintEvalWindow(JPanel canvas) {
        sprintEvalWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        frame.setVisible(false);
        sprintEvalWindow.setVisible(true);

        canvas.removeAll();
        JLabel evalLabel = new JLabel("Eval Title");
        JButton editEvalButton = createButton("Edit SPRINT Evaluation");

        canvas.add(evalLabel);
        canvas.add(editEvalButton);

        sprintEvalWindow.add(canvas);
        sprintEvalWindow.revalidate();
        sprintEvalWindow.repaint();
    }

    // Refreshes the employee list after an update or new employee addition
    private static void refreshEmployeeList() {
        employeeWindow.getContentPane().removeAll();
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

        // TODO: Update to display more attributes if needed
        List<Employee> employees = Employee.getEmployees();
        for (Employee employee : employees) {
            JButton employeeButton = createButton(employee.getFirstName() + " " + employee.getLastName());
            employeeButton.addActionListener(e -> openIndividualEmployeeWindow(employee));
            canvas.add(employeeButton);
        }

        JButton createEmployeeButton = createButton("Create Employee");
        createEmployeeButton.addActionListener(createEmployeeActionListener());
        canvas.add(createEmployeeButton);

        employeeWindow.add(canvas);
        employeeWindow.revalidate();
        employeeWindow.repaint();
    }

    // Opens the "Create Employee" form window to add a new employee
    private static ActionListener createEmployeeActionListener() {
        return e -> {
            JFrame createEmployeeWindow = new JFrame("Create Employee");
            createEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
            createEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

            // Form fields for new employee creation
            JTextField firstNameField = new JTextField(20);
            JTextField lastNameField = new JTextField(20);
            JTextField emailField = new JTextField(20);
            JTextField departmentField = new JTextField(20);
            JTextField positionField = new JTextField(20);
            JTextField salaryField = new JTextField(20);

            // TODO: Validate input fields as required
            formPanel.add(new JLabel("First Name:"));
            formPanel.add(firstNameField);
            formPanel.add(new JLabel("Last Name:"));
            formPanel.add(lastNameField);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(emailField);
            formPanel.add(new JLabel("Department:"));
            formPanel.add(departmentField);
            formPanel.add(new JLabel("Position:"));
            formPanel.add(positionField);
            formPanel.add(new JLabel("Salary:"));
            formPanel.add(salaryField);

            // Save button to save the new employee
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(saveEvent -> saveEmployee(createEmployeeWindow, firstNameField, lastNameField, emailField, departmentField, positionField, salaryField));
            formPanel.add(saveButton);

            createEmployeeWindow.add(formPanel);
            createEmployeeWindow.setVisible(true);
        };
    }

    // Save method for adding a new employee, with input validation
    private static void saveEmployee(JFrame createEmployeeWindow, JTextField firstNameField, JTextField lastNameField, JTextField emailField, JTextField departmentField, JTextField positionField, JTextField salaryField) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String department = departmentField.getText();
        String position = positionField.getText();
        double salary;

        // Validate email format
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid email format. Email must contain '@'.");
            return;
        }

        try {
            // Parse salary and create new employee
            salary = Double.parseDouble(salaryField.getText());
            Employee newEmployee = new Employee(firstName, lastName, email, department, position, salary);
            Employee.saveEmployee(newEmployee);
            createEmployeeWindow.dispose();
            refreshEmployeeList();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid salary input. Please enter a number.");
        }
    }
}
