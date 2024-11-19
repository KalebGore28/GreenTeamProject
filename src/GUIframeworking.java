    //add supervisor sprint eval info stuffs
    //contains all submissions(?) and their dates submitted 
    
    //Reminders for me:
     //TODO tune visibility of windows
     //TODO back button on windows
     //TODO home button? 

    //going backwards through windows - add functional back buttons 
    //add possible home button at the bottom of screen that returns to first window

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GUIframeworking {

    private static final int DEFAULT_FRAME_WIDTH = 540;
    private static final int DEFAULT_FRAME_HEIGHT = 960;
    private static final int DEFAULT_BUTTON_WIDTH = 500;
    private static final int DEFAULT_BUTTON_HEIGHT = 20;

    private static JFrame frame;
    private static JFrame employeeWindow;
    private static JFrame initialWindow;


    public static void main(String[] args) {
        frame = new JFrame("GUI Supervisor Homescreen");
        initialWindow = new JFrame("Login infill");
        employeeWindow = new JFrame("Employee View");
    
        initialWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
    
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
    
        JLabel titleLabel = new JLabel("I am a(n):");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(DEFAULT_FRAME_WIDTH, 10));
        canvas.add(Box.createVerticalStrut(20));
        canvas.add(titleLabel);
        canvas.add(Box.createVerticalStrut(10));
        canvas.add(separator);
        canvas.add(Box.createVerticalStrut(20));
    
        JButton employeeLoginButton = createButton("Employee");
        JButton supervisorLoginButton = createButton("Supervisor");
    
        employeeLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        supervisorLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Initialize employeeListPanel here
        JPanel employeeListPanel = new JPanel();
        employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));
    
        // Pass employeeListPanel to promptForEmployeeID
        employeeLoginButton.addActionListener(e -> promptForEmployeeID(employeeListPanel));
        supervisorLoginButton.addActionListener(e -> promptForSupervisorPassword());
    
        canvas.add(employeeLoginButton);
        canvas.add(Box.createVerticalStrut(10));
        canvas.add(supervisorLoginButton);
    
        initialWindow.add(canvas);
        initialWindow.setVisible(true);
    }

    private static JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        return button;
    }

    private static void promptForSupervisorPassword() {
        // Hardcoded password for the time being
        final int SUPERVISOR_PASSWORD = 1001; 
        
        // Prompt user to enter the password with initialWindow as the parent
        String input = JOptionPane.showInputDialog(initialWindow, "Enter Supervisor password:");
        
        // Check if input is provided
        if (input != null && !input.isEmpty()) {
            try {
                // Parse the input to an integer
                int enteredPassword = Integer.parseInt(input.trim());
                
                // Compare entered password with the hardcoded password
                if (enteredPassword == SUPERVISOR_PASSWORD) {
                    // Password is correct, proceed to open the supervisor window
                    openEmployeeWindow();
                } else {
                    // Password is incorrect, show error message
                    JOptionPane.showMessageDialog(initialWindow, "Incorrect Password");
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (non-numeric values)
                JOptionPane.showMessageDialog(initialWindow, "Invalid password format. Please enter a numeric password.");
            }
        } else {
            // Handle case where no input is provided
            JOptionPane.showMessageDialog(initialWindow, "Password cannot be empty.");
        }
    }
    

    private static void promptForEmployeeID(JPanel employeeListPanel) {
        String input = JOptionPane.showInputDialog(initialWindow, "Enter Employee ID:");
    
        if (input != null && !input.isEmpty()) {
            try {
                int employeeID = Integer.parseInt(input.trim());
                Employee employee = findEmployeeById(employeeID);
    
                if (employee != null) {
                    openIndividualEmployeeWindow(employee, employeeListPanel);  // Pass employeeListPanel
                } else {
                    JOptionPane.showMessageDialog(initialWindow, "No employee found with ID: " + employeeID);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(initialWindow, "Invalid ID format. Please enter a numeric ID.");
            }
        }
    }
    

    private static Employee findEmployeeById(int employeeID) {
        List<Employee> employees = Employee.getEmployees();
        
        for (Employee employee : employees) {
            if (employee.getId() == employeeID) {
                return employee;
            }
        }
        return null; // Returns null if no matching employee is found
    }

    // Method to create the employee view window with a search bar
    //this is the large scroll wheel page 
    private static void openEmployeeWindow() {
        employeeWindow = new JFrame("Employee Information"); // Initialize the employee window
        employeeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    
        // Panel for search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
    
        JTextField searchField = new JTextField(20);
        searchField.setMaximumSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JButton searchButton = new JButton("Search Employee");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Initialize employeeListPanel here
        JPanel employeeListPanel = new JPanel();
        employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));
    
        searchButton.addActionListener(e -> searchEmployee(searchField.getText(), employeeListPanel));  // Pass employeeListPanel here
    
        searchPanel.add(new JLabel("Enter ID, First Name, or Last Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
    
        mainPanel.add(searchPanel, BorderLayout.NORTH);
    
        // Add the employee list panel to the main panel
        mainPanel.add(new JScrollPane(employeeListPanel), BorderLayout.CENTER);
    
        // Load all employees initially
        loadEmployeeList(employeeListPanel);  // Pass employeeListPanel here
    
        // Add the buttons (Create Employee, Delete Employee)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
        JButton createEmployeeButton = new JButton("Create Employee");
        createEmployeeButton.addActionListener(createEmployeeActionListener(employeeListPanel));  // Pass employeeListPanel here
        buttonPanel.add(createEmployeeButton);
    
        // Text field for input to delete an employee
        JTextField deleteEmployeeField = new JTextField(20);
        deleteEmployeeField.setMaximumSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        deleteEmployeeField.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Button for deleting the employee
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Action listener to handle deleting an employee
        deleteEmployeeButton.addActionListener(e -> {
            String deleteTerm = deleteEmployeeField.getText().trim(); // Get the text input
            if (deleteTerm.isEmpty()) {
                JOptionPane.showMessageDialog(employeeWindow, "Please enter an ID, first name, or last name to delete.");
                return;
            }
    
            // Try deleting the employee based on input (ID, first name, or last name)
            boolean deleted = deleteEmployee(deleteTerm);
            if (deleted) {
                JOptionPane.showMessageDialog(employeeWindow, "Employee deleted successfully.");
                refreshEmployeeList(employeeListPanel);  // Refresh only the employee list
            } else {
                JOptionPane.showMessageDialog(employeeWindow, "Employee not found.");
            }
        });
    
        buttonPanel.add(deleteEmployeeField);
        buttonPanel.add(deleteEmployeeButton);
    
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        employeeWindow.add(mainPanel);
        employeeWindow.setVisible(true);
    }
    
    // Method to delete the employee based on input (ID, first name, or last name)
    private static boolean deleteEmployee(String searchTerm) {
        List<Employee> employees = Employee.getEmployees();
        boolean deleted = false;
    
        for (Employee employee : employees) {
            // Check if the search term matches the employee's ID, first name, or last name
            if (String.valueOf(employee.getId()).equals(searchTerm) || 
                employee.getFirstName().equalsIgnoreCase(searchTerm) || 
                employee.getLastName().equalsIgnoreCase(searchTerm)) {
                
                Employee.deleteEmployee(employee);  // Call your method to delete this employee
                deleted = true;
                break;
            }
        }
    
        return deleted;
    }
    
    private static void refreshEmployeeList(JPanel employeeListPanel) {
        employeeListPanel.removeAll();  // Clear the existing list
        
        List<Employee> employees = Employee.getEmployees();
        for (Employee employee : employees) {
            // Pass employeeListPanel along with panel and employee
            addEmployeeToPanel(employeeListPanel, employee, employeeListPanel);  // Add the employee to the panel
        }
    
        // Refresh the display of the employee list
        employeeListPanel.revalidate();
        employeeListPanel.repaint();
    }

    // Method to delete an employee by ID
private static void deleteEmployee(int employeeId, JPanel employeeListPanel) {
    List<Employee> employees = Employee.getEmployees();
    Employee employeeToDelete = null;

    // Find the employee by ID
    for (Employee employee : employees) {
        if (employee.getId() == employeeId) {
            employeeToDelete = employee;
            break;
        }
    }

    if (employeeToDelete != null) {
        Employee.deleteEmployee(employeeToDelete);  // Assuming you have a delete method in Employee class
        JOptionPane.showMessageDialog(frame, "Employee deleted successfully.");
        refreshEmployeeList(employeeListPanel);  // Refresh the list in the main window
    } else {
        JOptionPane.showMessageDialog(frame, "Employee not found.");
    }
}

// Updated searchEmployee method to handle int and String comparison
private static void searchEmployee(String searchTerm, JPanel employeeListPanel) {
    employeeListPanel.removeAll();  // Clear existing results
    employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));

    List<Employee> employees = Employee.getEmployees();
    boolean found = false;

    for (Employee employee : employees) {
        // Convert the int id to String and compare
        if (String.valueOf(employee.getId()).equals(searchTerm) || 
            employee.getFirstName().equalsIgnoreCase(searchTerm) || 
            employee.getLastName().equalsIgnoreCase(searchTerm)) {
            found = true;
            addEmployeeToPanel(employeeListPanel, employee, employeeListPanel);  // Pass employeeListPanel
        }
    }

    if (!found) {
        employeeListPanel.add(new JLabel("No matching employee found."));
    }

    // Refresh display with search results
    employeeWindow.getContentPane().removeAll();
    employeeWindow.add(new JScrollPane(employeeListPanel), BorderLayout.CENTER);
    employeeWindow.revalidate();
    employeeWindow.repaint();
}

// Helper method to add an employee's info to a panel
private static void addEmployeeToPanel(JPanel panel, Employee employee, JPanel employeeListPanel) {
    JPanel employeeInfoPanel = new JPanel(new BorderLayout());
    employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel employeeLabel = new JLabel(employee.getId() + " - " + employee.getFirstName() + " " + employee.getLastName());
    JButton moreInfoButton = new JButton("More Info");
    moreInfoButton.addActionListener(e -> openIndividualEmployeeWindow(employee, employeeListPanel)); // Pass employeeListPanel

    employeeInfoPanel.add(employeeLabel, BorderLayout.WEST);
    employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

    panel.add(employeeInfoPanel);
    panel.add(new JSeparator(SwingConstants.HORIZONTAL));
}

// Method to load the full list of employees in the panel
private static void loadEmployeeList(JPanel employeeListPanel) {
    List<Employee> employees = Employee.getEmployees();
    for (Employee employee : employees) {
        addEmployeeToPanel(employeeListPanel, employee, employeeListPanel);  // Pass employeeListPanel
    }
}

private static ActionListener createEmployeeActionListener(JPanel employeeListPanel) {
    return e -> {
        JFrame createEmployeeWindow = new JFrame("Create Employee");
        createEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        createEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField departmentField = new JTextField(20);
        JTextField positionField = new JTextField(20);
        JTextField salaryField = new JTextField(20);

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

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(saveEvent -> saveEmployee(createEmployeeWindow, firstNameField, lastNameField, emailField, departmentField, positionField, salaryField, employeeListPanel));
        formPanel.add(saveButton);

        createEmployeeWindow.add(formPanel);
        createEmployeeWindow.setVisible(true);
    };
}


private static void saveEmployee(JFrame createEmployeeWindow, JTextField firstNameField, JTextField lastNameField, JTextField emailField, JTextField departmentField, JTextField positionField, JTextField salaryField, JPanel employeeListPanel) {
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String email = emailField.getText();
    String department = departmentField.getText();
    String position = positionField.getText();
    double salary;

    if (!email.contains("@")) {
        JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid email format. Email must contain '@'.");
        return;
    }

    try {
        salary = Double.parseDouble(salaryField.getText());
        Employee newEmployee = new Employee(firstName, lastName, email, department, position, salary);
        Employee.saveEmployee(newEmployee);
        createEmployeeWindow.dispose();
        refreshEmployeeList(employeeListPanel);  // Refresh the list in the main window
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid salary input. Please enter a number.");
    }
}

    private static void openIndividualEmployeeWindow(Employee employee, JPanel employeeListPanel) {
        JFrame employeeDetailWindow = new JFrame(employee.getFirstName() + " " + employee.getLastName());
        employeeDetailWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        employeeDetailWindow.setVisible(true);
    
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
    
        JLabel nameLabel = new JLabel("Name: " + employee.getFirstName() + " " + employee.getLastName());
        JLabel emailLabel = new JLabel("Email: " + employee.getEmail());
        JLabel departmentLabel = new JLabel("Department: " + employee.getDepartment());
        JLabel positionLabel = new JLabel("Position: " + employee.getPosition());
        JLabel salaryLabel = new JLabel("Salary: $" + String.format("%.2f", employee.getSalary()));
    
        JButton editEmployeeButton = createButton("Edit Employee");
        editEmployeeButton.addActionListener(editEmployeeActionListener(employee, employeeListPanel));
    
        // Create a button for opening the Sprint Evaluation Window
        JButton sprintEvalButton = createButton("Open Sprint Evaluation");
    
        // Add an ActionListener to the button
        sprintEvalButton.addActionListener(e -> openSprintEvalWindow());
    
        // Add the button to the main frame or a specific panel
        JPanel buttonPanel = new JPanel(); // Create a panel for the button
        buttonPanel.add(sprintEvalButton);
    
        // Add button for viewing Sprint Eval
        JButton viewSprintEvalButton = createButton("View Sprint Eval");
        viewSprintEvalButton.addActionListener(e -> viewSprintEvaluations(employee.getId()));
    
        canvas.add(nameLabel);
        canvas.add(emailLabel);
        canvas.add(departmentLabel);
        canvas.add(positionLabel);
        canvas.add(salaryLabel);
        canvas.add(editEmployeeButton);
        canvas.add(sprintEvalButton);
        canvas.add(viewSprintEvalButton);
    
        employeeDetailWindow.add(canvas);
        employeeDetailWindow.revalidate();
        employeeDetailWindow.repaint();
    }
    

    private static void openSprintEvalWindow() {
        // Create a new JFrame for the sprint evaluation window
        JFrame sprintEvalFrame = new JFrame("Sprint Evaluation");
        sprintEvalFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        sprintEvalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        // Create a JPanel with a vertical layout to hold components
        JPanel evalPanel = new JPanel();
        evalPanel.setLayout(new BoxLayout(evalPanel, BoxLayout.Y_AXIS));
    
        // Add question 1: What did you accomplish this sprint?
        JLabel question1 = new JLabel("What did you accomplish this sprint?");
        JTextArea response1 = new JTextArea(3, 30);
        response1.setLineWrap(true);
        response1.setWrapStyleWord(true);
        JScrollPane scroll1 = new JScrollPane(response1);
    
        // Add question 2: What is left to accomplish?
        JLabel question2 = new JLabel("What is left to accomplish?");
        JTextArea response2 = new JTextArea(3, 30);
        response2.setLineWrap(true);
        response2.setWrapStyleWord(true);
        JScrollPane scroll2 = new JScrollPane(response2);
    
        // Add question 3: Are there any further comments?
        JLabel question3 = new JLabel("Are there any further comments?");
        JTextArea response3 = new JTextArea(3, 30);
        response3.setLineWrap(true);
        response3.setWrapStyleWord(true);
        JScrollPane scroll3 = new JScrollPane(response3);
    
        // Add a submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Capture responses
            String accomplished = response1.getText().trim();
            String remaining = response2.getText().trim();
            String comments = response3.getText().trim();
    
            // Save the responses
            saveSprintEvaluation(accomplished, remaining, comments);
    
            // Close the window after submission
            sprintEvalFrame.dispose();
        });
    
        // Add components to the panel
        evalPanel.add(question1);
        evalPanel.add(scroll1);
        evalPanel.add(question2);
        evalPanel.add(scroll2);
        evalPanel.add(question3);
        evalPanel.add(scroll3);
        evalPanel.add(submitButton);
    
        // Add the panel to the frame and make it visible
        sprintEvalFrame.add(evalPanel);
        sprintEvalFrame.setVisible(true);
    }

    private static void viewSprintEvaluations(int employeeID) {
        JFrame evalViewWindow = new JFrame("Sprint Evaluations");
        evalViewWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        evalViewWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
    
        List<String> evaluations = getSprintEvaluationsForEmployee(employeeID);
    
        if (evaluations.isEmpty()) {
            JLabel noEvalLabel = new JLabel("No sprint evaluations found for this employee.");
            canvas.add(noEvalLabel);
        } else {
            for (String eval : evaluations) {
                JLabel evalLabel = new JLabel(eval);
                canvas.add(evalLabel);
                canvas.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
        }
    
        JButton closeButton = createButton("Close");
        closeButton.addActionListener(e -> evalViewWindow.dispose());
        canvas.add(closeButton);
    
        evalViewWindow.add(new JScrollPane(canvas));
        evalViewWindow.setVisible(true);
    }
    

    private static void saveSprintEvaluation(String accomplished, String remaining, String comments) {
        String filePath = "sprint_evaluations.csv"; // Path to your CSV file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        // Prepare CSV entry
        String csvEntry = String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n", timestamp, accomplished, remaining, comments);

        // Append to the CSV file
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(csvEntry);
            JOptionPane.showMessageDialog(null, "Sprint evaluation submitted successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save sprint evaluation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //TODO rewrite to be a view sprint eval button? 
    private static List<String> getSprintEvaluationsForEmployee(int employeeID) {
        List<String> evaluations = new ArrayList<>();
        String filePath = "sprint_evaluations.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                // Assuming the format is: TIMESTAMP,COMMENT,REMAINING,COMMENTS,EMPLOYEE_ID
                //TODO implement format?
                int evalEmployeeID = Integer.parseInt(columns[4].trim());
                if (evalEmployeeID == employeeID) {
                    String timestamp = columns[0].replace("\"", "").trim();
                    String comment = columns[1].replace("\"", "").trim(); 
                    evaluations.add("Date: " + timestamp + " - Comment: " + comment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return evaluations;
    }
    
    private static ActionListener editEmployeeActionListener(Employee employee, JPanel employeeListPanel) {
        return e -> {
            JFrame editEmployeeWindow = new JFrame("Edit Employee");
            editEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
            editEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    
            JTextField firstNameField = new JTextField(employee.getFirstName(), 20);
            JTextField lastNameField = new JTextField(employee.getLastName(), 20);
            JTextField emailField = new JTextField(employee.getEmail(), 20);
            JTextField departmentField = new JTextField(employee.getDepartment(), 20);
            JTextField positionField = new JTextField(employee.getPosition(), 20);
            JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()), 20);
    
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
    
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(saveEvent -> {
                employee.setFirstName(firstNameField.getText());
                employee.setLastName(lastNameField.getText());
                employee.setEmail(emailField.getText());
                employee.setDepartment(departmentField.getText());
                employee.setPosition(positionField.getText());
                try {
                    employee.setSalary(Double.parseDouble(salaryField.getText()));
                    Employee.saveEmployee(employee);
                    editEmployeeWindow.dispose();
                    refreshEmployeeList(employeeListPanel);  // Refresh the list in the main window
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editEmployeeWindow, "Invalid salary input. Please enter a number.");
                }
            });
    
            formPanel.add(saveButton);
            editEmployeeWindow.add(formPanel);
            editEmployeeWindow.setVisible(true);
        };
    }
    
}