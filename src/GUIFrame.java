import java.awt.*;
import javax.swing.*;

public class GUIFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 960);

        JPanel mainPanel = new JPanel(new CardLayout());

        // Add panels
        LoginPanel loginPanel = new LoginPanel(mainPanel);
        EmployeeListPanel listPanel = new EmployeeListPanel(mainPanel);
        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(listPanel, "EmployeeList");

        frame.add(mainPanel, BorderLayout.CENTER);

        // Show login panel initially
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "LoginPanel");

        frame.setVisible(true);
    }
}