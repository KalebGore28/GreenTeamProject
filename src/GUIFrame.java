import java.awt.*;
import javax.swing.*;

public class GUIFrame {
    public static void main(String[] args) {
        // Create the main application frame
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 960);

        // Create the main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());

        // Instantiate panels
        LoginPanel loginPanel = new LoginPanel(mainPanel);
        EmployeeListPanel listPanel = new EmployeeListPanel(mainPanel);
        CreateEmployeePanel createEmployeePanel = new CreateEmployeePanel(mainPanel);

        // Add panels to the CardLayout
        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(listPanel, "EmployeeList");
        mainPanel.add(createEmployeePanel, "CreateEmployee");

        // Add mainPanel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Show the login panel initially
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "LoginPanel");

        // Make the frame visible
        frame.setVisible(true);
    }
}