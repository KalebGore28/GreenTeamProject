import java.awt.*;
import javax.swing.*;

public class GUIFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 960);
        frame.setResizable(false);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new CardLayout());

        LoginPanel loginPanel = new LoginPanel(mainPanel);
        loginPanel.setName("LoginPanel");
        EmployeeListPanel employeeListPanel = new EmployeeListPanel(mainPanel);
        employeeListPanel.setName("EmployeeList");
        CreateEmployeePanel createEmployeePanel = new CreateEmployeePanel(mainPanel);
        createEmployeePanel.setName("CreateEmployee");
        HomePanel homePanel = new HomePanel(mainPanel);
        homePanel.setName("HomePanel");
        EmployeeViewPanel employeeViewPanel = new EmployeeViewPanel(mainPanel);
        employeeViewPanel.setName("EmployeeView");
        EmployeeEditPanel employeeEditPanel = new EmployeeEditPanel(mainPanel);
        employeeEditPanel.setName("EmployeeEdit");
        EmployeeHistoryPanel employeeHistoryPanel = new EmployeeHistoryPanel(mainPanel);
        employeeHistoryPanel.setName("EmployeeHistory");
        EmployeeHistoryEditPanel employeeHistoryEditPanel = new EmployeeHistoryEditPanel(mainPanel);
        employeeHistoryEditPanel.setName("EmployeeHistoryEdit");
        EmployeeSkillsPanel employeeSkillsPanel = new EmployeeSkillsPanel(mainPanel);
        employeeSkillsPanel.setName("EmployeeSkills");
        EmployeeSkillsEditPanel employeeSkillsEditPanel = new EmployeeSkillsEditPanel(mainPanel);
        employeeSkillsEditPanel.setName("EmployeeSkillsEdit");
        EmployeeTasksPanel employeeTasksPanel = new EmployeeTasksPanel(mainPanel);
        employeeTasksPanel.setName("EmployeeTasks");
        EmployeeTaskEditPanel employeeTaskEditPanel = new EmployeeTaskEditPanel(mainPanel);
        employeeTaskEditPanel.setName("EmployeeTaskEdit");

        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(employeeListPanel, "EmployeeList");
        mainPanel.add(createEmployeePanel, "CreateEmployee");
        mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(employeeViewPanel, "EmployeeView");
        mainPanel.add(employeeEditPanel, "EmployeeEdit");
        mainPanel.add(employeeHistoryPanel, "EmployeeHistory");
        mainPanel.add(employeeHistoryEditPanel, "EmployeeHistoryEdit");
        mainPanel.add(employeeSkillsPanel, "EmployeeSkills");
        mainPanel.add(employeeSkillsEditPanel, "EmployeeSkillsEdit");
        mainPanel.add(employeeTasksPanel, "EmployeeTasks");
        mainPanel.add(employeeTaskEditPanel, "EmployeeTaskEdit");


        frame.add(mainPanel, BorderLayout.CENTER);

        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "LoginPanel");

        frame.setVisible(true);
    }
}