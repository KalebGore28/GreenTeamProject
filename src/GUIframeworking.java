
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

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
    

    public static void main(String[] args){
        //storing default sizes for things -- buttons, frames, etc
        //works because I'm standardizing a lot of it to appear
        //as a phone screen/mobile application 
        final int defaultFrameWidth = 540; 
        final int defaultFrameHeight = 960; 
        final int defaultButtonWidth = 500; 
        final int defaultButtonHeight = 25; 


        //momentarily storing all windows here for easy access across methods
        //I might leave these outside the methods for easy setting of visible/invisible
        JFrame frame = new JFrame("GUI framework");
        JFrame employeeWindow = new JFrame("Employee View");
        JFrame Employee1Window = new JFrame("Employee1 Window");
        JFrame sprintEvalWindow = new JFrame("Sprint Eval Window");



        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
            }
        };


        SwingUtilities.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(defaultFrameWidth, defaultFrameHeight);
            JButton employeeButton = new JButton("Employee");
            JButton sprintEvalButton = new JButton("SPRINT Evaluations");
            employeeButton.setPreferredSize(new Dimension(defaultButtonWidth, defaultButtonHeight));
            sprintEvalButton.setPreferredSize(new Dimension(defaultButtonWidth, defaultButtonHeight));

            //testing adding button capabilities
            JButton Employee1 = new JButton("Example Employee1"); 
            Employee1.setPreferredSize(new Dimension(500, 25));

            JButton Employee2 = new JButton("Example Employee2"); 
            Employee2.setPreferredSize(new Dimension(defaultButtonWidth, defaultButtonHeight));

            JButton createEmployee = new JButton("Create Employee"); 
            createEmployee.setPreferredSize(new Dimension(defaultButtonWidth, defaultButtonHeight));
            //TODO add createEmployee Button functionality 

            //open employee window whenever employee button is clicked
            //only triggers on employee button  
            employeeButton.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e){
                    employeeWindow.setSize(defaultFrameWidth, defaultFrameHeight);
                    frame.setVisible(false); 
                    employeeWindow.setVisible(true);   
                    
                    canvas.removeAll(); //forces a wipe of the canvas
                    canvas.add(Employee1); 
                    canvas.add(Employee2);
                    canvas.add(createEmployee);
                    employeeWindow.add(canvas);
                 
                }
            });

            //when employee1 button is pushed from employee class 
            Employee1.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e){
                    Employee1Window.setSize(defaultFrameWidth, defaultFrameHeight);
                    employeeWindow.setVisible(false);  //general employee window
                    Employee1Window.setVisible(true);   //specific employee window
                    JLabel EmployeeLabel = new JLabel("Employee 1"); //Employee name/title/ placeholder for top of window
                    JButton editEmployeeButton = new JButton("Edit Employee");
                    editEmployeeButton.setPreferredSize(new Dimension(125, defaultButtonHeight));
                    //TODO add editEmployeeButton Functionality 
                    canvas.removeAll(); //forces a wipe of the canvas
                    canvas.add(EmployeeLabel);
                    canvas.add(editEmployeeButton);
                    
                    Employee1Window.add(canvas);
                 
                }
            });

                 
            //sprint eval Button - add additional info to Sprint Eval Window later
            sprintEvalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JLabel EvalLabel = new JLabel("Eval Title");
                    JButton editEvalButton = new JButton("Edit SPRINT Evaluation");


                    sprintEvalWindow.setSize(defaultFrameWidth, defaultFrameHeight);
                    sprintEvalWindow.setVisible(true);
                    frame.setVisible(false);

                    canvas.removeAll(); //forces a wipe of the canvas
                    canvas.add(EvalLabel);
                    canvas.add(editEvalButton); 
                    
                    sprintEvalWindow.add(canvas);
                }
            });

            //everything below belongs to the default window 
            canvas.add(employeeButton);
            canvas.add(sprintEvalButton);

            frame.add(canvas);
            frame.setVisible(true);
            });
    }
    
}
