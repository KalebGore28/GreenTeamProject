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
        EmployeeDemographicsEditPanel employeeDemographicsEditPanel = new EmployeeDemographicsEditPanel(mainPanel);
        employeeDemographicsEditPanel.setName("EmployeeDemographics");
        EmployeeAssignedSprintsPanel employeeAssignedSprintsPanel = new EmployeeAssignedSprintsPanel(mainPanel);
        employeeAssignedSprintsPanel.setName("EmployeeAssignedSprints");

        SprintListPanel sprintListPanel = new SprintListPanel(mainPanel);
        sprintListPanel.setName("SprintList");
        CreateSprintPanel createSprintPanel = new CreateSprintPanel(mainPanel);
        createSprintPanel.setName("CreateSprint");
        SprintViewPanel sprintViewPanel = new SprintViewPanel(mainPanel);
        sprintViewPanel.setName("SprintView");
        SprintAssignedEmployeesPanel sprintAssignedEmployeesPanel = new SprintAssignedEmployeesPanel(mainPanel);
        sprintAssignedEmployeesPanel.setName("SprintAssignedEmployees");
        SprintEvaluationsPanel sprintEvaluationsPanel = new SprintEvaluationsPanel(mainPanel);
        sprintEvaluationsPanel.setName("SprintEvaluations");
        SprintEvaluationEditPanel sprintEvaluationEditPanel = new SprintEvaluationEditPanel(mainPanel);
        sprintEvaluationEditPanel.setName("SprintEvaluationEdit");

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
        mainPanel.add(employeeDemographicsEditPanel, "EmployeeDemographics");
        mainPanel.add(employeeAssignedSprintsPanel, "EmployeeAssignedSprints");

        mainPanel.add(sprintListPanel, "SprintList");
        mainPanel.add(createSprintPanel, "CreateSprint");
        mainPanel.add(sprintViewPanel, "SprintView");
        mainPanel.add(sprintAssignedEmployeesPanel, "SprintAssignedEmployees");
        mainPanel.add(sprintEvaluationsPanel, "SprintEvaluations");
        mainPanel.add(sprintEvaluationEditPanel, "SprintEvaluationEdit");


        frame.add(mainPanel, BorderLayout.CENTER);

        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "LoginPanel");

        frame.setVisible(true);
    }
}