import java.awt.*;
import javax.swing.*;

public class GUIFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 960);

        JPanel mainPanel = new JPanel(new CardLayout());

        LoginPanel loginPanel = new LoginPanel(mainPanel);
        EmployeeListPanel listPanel = new EmployeeListPanel(mainPanel);
        CreateEmployeePanel createEmployeePanel = new CreateEmployeePanel(mainPanel);
        HomePanel homePanel = new HomePanel(mainPanel);
        EmployeeDetailPanel detailPanel = new EmployeeDetailPanel(mainPanel);

        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(listPanel, "EmployeeList");
        mainPanel.add(createEmployeePanel, "CreateEmployee");
        mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(detailPanel, "EmployeeDetail");

        frame.add(mainPanel, BorderLayout.CENTER);

        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "LoginPanel");

        frame.setVisible(true);
    }
}