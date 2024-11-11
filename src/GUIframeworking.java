

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List; 

public class GUIframeworking {
    //Info for the team:
    //The idea is to put together the click buttons first, and individual windows
    //then to make it work with the information stored by other methods already in place 

    //also going to focus on function before form 

    
    //Reminders for me:
    //TODO figure out how to position things in the window
    //textboxes - editable
    //saving over information that already is displayed/exists
    //pulling employee information from other classes to display it 
    //going backwards through windows - add functional back buttons 
    //add possible home button at the bottom of screen that returns to first window
    //add profile button that pulls up personal info? potential future possibilities application 
    //employee.getEmployees is a list, can do .size, find a way to make that many clickbuttons 
    //can filter by how many employees are just working here 
    
    //primarily gonna be working with employee.java and sprint.java

    //new modifications come from having Chatgpt rewrite the code to be a bit
    //more intuitive/nice to read. 

    
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
        
        // Create main canvas
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
            }
        };
        
        // Set layout for canvas - stacks everything vertically
         canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
        //canvas.setLayout(new GridBagLayout()); // Change to GridBagLayout
        // Create GridBagConstraints

        /* 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the width of the button
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.weightx = 1; // Weight for horizontal resizing
        gbc.insets = new Insets(10, 10, 10, 10); // Add some space between buttons
        */

        // Create buttons
        JButton employeeButton = createButton("Employee");
        JButton sprintEvalButton = createButton("SPRINT Evaluations");

        // Add action listeners
        employeeButton.addActionListener(e -> openEmployeeWindow(canvas));
        sprintEvalButton.addActionListener(e -> openSprintEvalWindow(canvas));

        // Add buttons to main canvas
        canvas.add(employeeButton);
        canvas.add(sprintEvalButton);

        frame.add(canvas);
        frame.setVisible(true);
    }

    private static JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
        return button;
    }

    private static void openEmployeeWindow(JPanel canvas) {
        employeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        frame.setVisible(false);
        employeeWindow.setVisible(true);

           //clears the canvas for restructuring 
        canvas.removeAll();

        //pull in the list of employees
        List <Employee> employees = Employee.getEmployees(); 

        //should add a button for every employee
        for (Employee employee : employees) {
            String buttonTitle = employee.getFirstName() + " " + employee.getLastName();
            JButton employeeButton = createButton(buttonTitle);
            employeeButton.addActionListener(e -> openIndividualEmployeeWindow(employee));
            canvas.add(employeeButton);
        }

        //adds create employee button 
        JButton createEmployeeButton = createButton("Create Employee");
        createEmployeeButton.addActionListener(createEmployeeActionListener()); 
        canvas.add(createEmployeeButton);
        
        employeeWindow.add(canvas);
        employeeWindow.revalidate();
        employeeWindow.repaint();
    }


    private static ActionListener createEmployeeActionListener() {
        return e -> {
            JFrame createEmployeeWindow = new JFrame("Create New Employee");
            createEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
            createEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    
            // Text fields for input
            JTextField firstNameField = new JTextField(20);
            JTextField lastNameField = new JTextField(20);
            JTextField emailField = new JTextField(20);
            JTextField departmentField = new JTextField(20);
            JTextField positionField = new JTextField(20);
            JTextField salaryField = new JTextField(20);
    
            // Add labels and fields to the form panel
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
    
            // "Save" button
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(saveEvent -> {
                // Gather data from text fields
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String department = departmentField.getText();
                String position = positionField.getText();
                double salary;
    
                try {
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createEmployeeWindow, "Invalid salary input. Please enter a number.");
                    return;
                }
    
                // Create a new Employee object
                Employee newEmployee = new Employee(firstName, lastName, email, department, position, salary);
    
                // Save the new employee using the saveEmployee method
                Employee.saveEmployee(newEmployee);
    
                // Close the window after saving
                createEmployeeWindow.dispose();
    
                // Refresh the employee list in the employeeWindow
                refreshEmployeeList();
            });
    
            // Add save button to form panel
            formPanel.add(saveButton);
    
            // Add the form panel to the window
            createEmployeeWindow.add(formPanel);
            createEmployeeWindow.setVisible(true);
        };
    }

    //TODO add edit employee button functionality
    //TODO delete employee button

    
    private static void refreshEmployeeList() {
        // Clear the existing content in employeeWindow
        employeeWindow.getContentPane().removeAll();
    
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
    
        // Fetch updated list of employees
        List<Employee> employees = Employee.getEmployees();
    
        // Create buttons for each employee
        for (Employee employee : employees) {
            String buttonTitle = employee.getFirstName() + " " + employee.getLastName();
            JButton employeeButton = createButton(buttonTitle);
            employeeButton.addActionListener(e -> openIndividualEmployeeWindow(employee));
            canvas.add(employeeButton);
        }
    
        // Add the "Create Employee" button using the reusable ActionListener method
        JButton createEmployeeButton = createButton("Create Employee");
        createEmployeeButton.addActionListener(createEmployeeActionListener());
        canvas.add(createEmployeeButton);
    
        // Add updated content to employeeWindow and refresh it
        employeeWindow.add(canvas);
        employeeWindow.revalidate();
        employeeWindow.repaint();
    }

    private static ActionListener editEmployeeActionListener(Employee employee) {
        return e -> {
            JFrame editEmployeeWindow = new JFrame("Edit Employee");
            editEmployeeWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
            editEmployeeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    
            // Text fields with current employee information
            JTextField firstNameField = new JTextField(employee.getFirstName(), 20);
            JTextField lastNameField = new JTextField(employee.getLastName(), 20);
            JTextField emailField = new JTextField(employee.getEmail(), 20);
            JTextField departmentField = new JTextField(employee.getDepartment(), 20);
            JTextField positionField = new JTextField(employee.getPosition(), 20);
            JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()), 20);
    
            // Add labels and fields to the form panel
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
    
            // "Save" button to update employee information
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(saveEvent -> {
                // Gather updated data from text fields
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String department = departmentField.getText();
                String position = positionField.getText();
                double salary;
    
                try {
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editEmployeeWindow, "Invalid salary input. Please enter a number.");
                    return;
                }
    
                // Update the employee's information
                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setEmail(email);
                employee.setDepartment(department);
                employee.setPosition(position);
                employee.setSalary(salary);
    
                // Save updated employee using saveEmployee
                Employee.saveEmployee(employee);
    
                // Close the window after saving
                editEmployeeWindow.dispose();
    
                // Refresh the employee list to reflect changes
                refreshEmployeeList();
            });
    
            // Add save button to form panel
            formPanel.add(saveButton);
    
            // Add the form panel to the window
            editEmployeeWindow.add(formPanel);
            editEmployeeWindow.setVisible(true);
        };
    }
    
    

    private static void openIndividualEmployeeWindow(Employee employee) {
        JFrame employeeDetailWindow = new JFrame(employee.getFirstName() + " " + employee.getLastName());
        employeeDetailWindow.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        employeeDetailWindow.setVisible(true);
    
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.Y_AXIS));
    
        // Labels displaying detailed employee information
        JLabel nameLabel = new JLabel("Name: " + employee.getFirstName() + " " + employee.getLastName());
        JLabel emailLabel = new JLabel("Email: " + employee.getEmail());
        JLabel departmentLabel = new JLabel("Department: " + employee.getDepartment());
        JLabel positionLabel = new JLabel("Position: " + employee.getPosition());
        JLabel salaryLabel = new JLabel("Salary: $" + String.format("%.2f", employee.getSalary()));
    
        // Edit button to allow updating employee information
        JButton editEmployeeButton = createButton("Edit Employee");
        editEmployeeButton.addActionListener(editEmployeeActionListener(employee));
    
        // Add labels and button to the canvas
        canvas.add(nameLabel);
        canvas.add(emailLabel);
        canvas.add(departmentLabel);
        canvas.add(positionLabel);
        canvas.add(salaryLabel);
        canvas.add(editEmployeeButton);
    
        // Add the canvas to the window and refresh
        employeeDetailWindow.add(canvas);
        employeeDetailWindow.revalidate();
        employeeDetailWindow.repaint();
    }
    


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
}
