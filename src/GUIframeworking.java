import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class GUIframeworking {

    private static final int DEFAULT_FRAME_WIDTH = 540;
    private static final int DEFAULT_FRAME_HEIGHT = 960;
    private static final int DEFAULT_BUTTON_WIDTH = 500;
    private static final int DEFAULT_BUTTON_HEIGHT = 20;

    private static JFrame frame;
    private static JFrame employeeWindow;
    private static JFrame sprintEvalWindow;
    private static JFrame initialWindow;

    
      //Info for the team:
    //The idea is to put together the click buttons first, and individual windows
    //then to make it work with the information stored by other methods already in place 
     //also going to focus on function before form 

    //TODO: 
    //add employee button functionality
    //add employee sprint eval info
    //contains questions for employee and a save button 

    //add supervisor sprint eval info stuffs
    //contains all submissions(?) and their dates submitted 

    //THINKING THROUGH SPRINT EVAL QUESITONS:
    //What did you accomplish this sprint? 
    //What is left to accomplish? 
    //Are there any further comments? 


    
    //Reminders for me:
    //TODO figure out how to position things in the window
     //TODO create delete employee button
     //TODO create search bar in employee window 
     //TODO tune visibility of windows
     //TODO back button on windows
     //TODO home button? 
     //TODO going to transfer supervisor button to go to the employee scroll wheel and then each employee has their own sprint eval response button? 

    //textboxes - editable
    //saving over information that already is displayed/exists
    //going backwards through windows - add functional back buttons 
    //add possible home button at the bottom of screen that returns to first window
    //can filter by how many employees are just working here 
    //primarily gonna be working with employee.java and sprint.java

  // Set action listeners to open respective windows
        //TODO rewrite action listener to open to employee view
        //currently has limited functionality - is going to pull from existing supervisor views
        //but contain less information 
        // Set action listeners to open respective windows
    // Opens the Sprint Evaluation window (TODO: Add actual sprint evaluation functionality)


    public static void main(String[] args) {
        frame = new JFrame("GUI Supervisor Homescreen");
        initialWindow = new JFrame("Login infill");
        employeeWindow = new JFrame("Employee View");
        sprintEvalWindow = new JFrame("Sprint Eval Window");

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

        employeeLoginButton.addActionListener(e -> promptForEmployeeID());
        supervisorLoginButton.addActionListener(e -> openEmployeeWindow());

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

    private static void promptForEmployeeID() {
        String input = JOptionPane.showInputDialog(initialWindow, "Enter Employee ID:");
        
        if (input != null && !input.isEmpty()) {
            try {
                int employeeID = Integer.parseInt(input.trim());
                Employee employee = findEmployeeById(employeeID);
                
                if (employee != null) {
                    openIndividualEmployeeWindow(employee);
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
private static void openEmployeeWindow() {
    frame = new JFrame("Employee Information");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // Panel for search bar
    JPanel searchPanel = new JPanel();
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

    JTextField searchField = new JTextField(20);
    searchField.setMaximumSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
    searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Button to perform search
    JButton searchButton = new JButton("Search Employee");
    searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    searchButton.addActionListener(e -> searchEmployee(searchField.getText()));

    searchPanel.add(new JLabel("Enter ID, First Name, or Last Name:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    mainPanel.add(searchPanel, BorderLayout.NORTH);

    // Panel to display the list of employees or search results
    JPanel employeeListPanel = new JPanel();
    employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));
    mainPanel.add(new JScrollPane(employeeListPanel), BorderLayout.CENTER);

    // Load all employees initially
    loadEmployeeList(employeeListPanel);

    frame.add(mainPanel);
    frame.setVisible(true);
}

// Updated searchEmployee method to handle int and String comparison
private static void searchEmployee(String searchTerm) {
    JPanel employeeListPanel = new JPanel();
    employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));

    List<Employee> employees = Employee.getEmployees();
    boolean found = false;

    for (Employee employee : employees) {
        // Convert the int id to String and compare
        if (String.valueOf(employee.getId()).equals(searchTerm) || 
            employee.getFirstName().equalsIgnoreCase(searchTerm) || 
            employee.getLastName().equalsIgnoreCase(searchTerm)) {
            found = true;
            addEmployeeToPanel(employeeListPanel, employee);
        }
    }

    if (!found) {
        employeeListPanel.add(new JLabel("No matching employee found."));
    }

    // Refresh display with search results
    frame.getContentPane().removeAll();
    frame.add(employeeListPanel, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
}


// Helper method to add an employee's info to a panel
private static void addEmployeeToPanel(JPanel panel, Employee employee) {
    JPanel employeeInfoPanel = new JPanel(new BorderLayout());
    employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel employeeLabel = new JLabel(employee.getId() + " - " + employee.getFirstName() + " " + employee.getLastName());
    JButton moreInfoButton = new JButton("More Info");
    moreInfoButton.addActionListener(e -> openIndividualEmployeeWindow(employee));

    employeeInfoPanel.add(employeeLabel, BorderLayout.WEST);
    employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

    panel.add(employeeInfoPanel);
    panel.add(new JSeparator(SwingConstants.HORIZONTAL));
}

// Method to load the full list of employees in the panel
private static void loadEmployeeList(JPanel panel) {
    List<Employee> employees = Employee.getEmployees();
    for (Employee employee : employees) {
        addEmployeeToPanel(panel, employee);
    }
}

    private static JPanel createEmployeeListPanel(JPanel mainPanel) {
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));

        List<Employee> employees = Employee.getEmployees();

        for (Employee employee : employees) {
            JPanel employeeInfoPanel = new JPanel(new BorderLayout());
            employeeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel employeeLabel = new JLabel(employee.getId() + " \t " + employee.getFirstName() + " " + employee.getLastName());
            employeeInfoPanel.add(employeeLabel, BorderLayout.WEST);

            JButton moreInfoButton = new JButton("More Info");
            moreInfoButton.addActionListener(e -> openIndividualEmployeeWindow(employee));
            employeeInfoPanel.add(moreInfoButton, BorderLayout.EAST);

            employeePanel.add(employeeInfoPanel);
            employeePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        JScrollPane scrollPane = new JScrollPane(employeePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JButton createEmployeeButton = new JButton("Create Employee");
        createEmployeeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createEmployeeButton.addActionListener(createEmployeeActionListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createEmployeeButton);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        return listPanel;
    }

    
    

    private static ActionListener createEmployeeActionListener() {
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
            saveButton.addActionListener(saveEvent -> saveEmployee(createEmployeeWindow, firstNameField, lastNameField, emailField, departmentField, positionField, salaryField));
            formPanel.add(saveButton);

            createEmployeeWindow.add(formPanel);
            createEmployeeWindow.setVisible(true);
        };
    }

    private static void saveEmployee(JFrame createEmployeeWindow, JTextField firstNameField, JTextField lastNameField, JTextField emailField, JTextField departmentField, JTextField positionField, JTextField salaryField) {
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
            refreshEmployeeList();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid salary input. Please enter a number.");
        }
    }

    private static void refreshEmployeeList() {
        employeeWindow.getContentPane().removeAll();
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));

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

    private static void openIndividualEmployeeWindow(Employee employee) {
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
        editEmployeeButton.addActionListener(editEmployeeActionListener(employee));
          
        // Create a buttAon for opening the Sprint Evaluation Window
        JButton sprintEvalButton = createButton("Open Sprint Evaluation");
        
        // Add an ActionListener to the button
        sprintEvalButton.addActionListener(e -> openSprintEvalWindow());
        
        // Add the button to the main frame or a specific panel
        JPanel buttonPanel = new JPanel(); // Create a panel for the button
        buttonPanel.add(sprintEvalButton);

        
        canvas.add(nameLabel);
        canvas.add(emailLabel);
        canvas.add(departmentLabel);
        canvas.add(positionLabel);
        canvas.add(salaryLabel);
        canvas.add(editEmployeeButton);
        canvas.add(sprintEvalButton);

        employeeDetailWindow.add(canvas);
        employeeDetailWindow.revalidate();
        employeeDetailWindow.repaint();
    }

    private static void openSprintEvalWindow() {
        // Create a new JFrame for the sprint evaluation window
        JFrame sprintEvalFrame = new JFrame("Sprint Evaluation");
        sprintEvalFrame.setSize(400, 400);
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
            String accomplished = response1.getText();
            String remaining = response2.getText();
            String comments = response3.getText();
    
            // Here you can handle the responses, e.g., save them to a file or database
            System.out.println("Accomplished: " + accomplished);
            System.out.println("Remaining: " + remaining);
            System.out.println("Comments: " + comments);
    
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

    private static ActionListener editEmployeeActionListener(Employee employee) {
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
}
