import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeTasksPanel extends BasePanel {
    private Employee employee; // The employee whose tasks are being displayed
    private JPanel tasksPanel; // Panel to hold task cards

    public EmployeeTasksPanel(JPanel mainPanel) {
        super(mainPanel);
        initializeContent();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        refreshContent();
    }

    @Override
    protected void initializeContent() {
        setLayout(new BorderLayout());
        setBackground(WHITE);

        // Header
        JLabel titleLabel = new JLabel("Employee Tasks");
        titleLabel.setBackground(LIGHT_GRAY);
        titleLabel.setForeground(ACCENT);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Tasks panel
        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBackground(WHITE);

        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Panel to hold both the action bar and bottom bar
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout()); // Use BorderLayout for stacking
        southPanel.setBackground(WHITE); // Ensure consistent background color

        // Action bar with an "Add New Task" button
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionBar.setBackground(WHITE);

        JButton addTaskButton = new JButton("Add New Task");
        styleButton(addTaskButton);
        addTaskButton.addActionListener(_ -> navigateToEditPanel(null));
        actionBar.add(addTaskButton);

        southPanel.add(actionBar, BorderLayout.NORTH);

        // Bottom Bar
        BottomBar bottomBar = new BottomBar(mainPanel);
        southPanel.add(bottomBar, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void refreshContent() {
        if (employee != null) {
            displayTasks(employee.getTasks());
        }
    }

    private void displayTasks(List<EmployeeTask> tasks) {
        tasksPanel.removeAll();

        if (tasks == null || tasks.isEmpty()) {
            JLabel noTasksLabel = new JLabel("No tasks found.", SwingConstants.CENTER);
            styleLabel(noTasksLabel);
            tasksPanel.add(noTasksLabel);
        } else {
            for (EmployeeTask task : tasks) {
                tasksPanel.add(createTaskCard(task));
            }
        }

        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    private JPanel createTaskCard(EmployeeTask task) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DARK_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        card.setBackground(LIGHT_GRAY);

        JLabel titleLabel = new JLabel(task.getName());
        styleLabel(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea descriptionArea = new JTextArea(task.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton editButton = new JButton("Edit");
        styleButton(editButton);
        editButton.addActionListener(_ -> navigateToEditPanel(task));

        JButton markCompleteButton = new JButton("Mark Complete");
        styleButton(markCompleteButton);
        markCompleteButton.addActionListener(_ -> {
            task.setStatus(true); // Mark the task as complete
            employee.updateTask(task); // Save the updated task
            refreshContent(); // Refresh the task list
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ACCENT_LIGHT);
        buttonPanel.add(editButton);
        if (!task.getStatus()) {
            buttonPanel.add(markCompleteButton);
        }

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descriptionArea, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void navigateToEditPanel(EmployeeTask task) {
        EmployeeTaskEditPanel editPanel = findPanelByType(EmployeeTaskEditPanel.class);
        if (editPanel != null) {
            editPanel.setTask(employee, task);
            navigateToPanel("EmployeeTaskEdit");
        }
    }
}