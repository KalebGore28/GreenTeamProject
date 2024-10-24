import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

public class GUIframeworking {
    //The idea is to put together the click buttons first, and individual windows
    //then to make it work with the information stored by other methods already in place 

    //also going to focus on function before form 

    //basis code? 
    public static void main(String[] args){
        //storing default sizes for things -- buttons, frames, etc
        //works because I'm standardizing a lot of it to appear
        //as a phone screen/mobile application 
        final int defaultFrameWidth = 540; 
        final int defaultFrameHeight = 960; 
        final int defaultButtonWidth = 500; 
        final int defaultButtonHeight = 25; 


        //momentarily storing all windows here for easy access across methods
        JFrame frame = new JFrame("GUI framework");
        JFrame employeeWindow = new JFrame("Employee View");
        JFrame Employee1Window = new JFrame("Employee1 Window");


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
                    employeeWindow.add(canvas);
                 
                }
            });

            //when employee1 button is pushed from employee class 
            Employee1.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e){
                    Employee1Window.setSize(defaultFrameWidth, defaultFrameHeight);
                    employeeWindow.setVisible(false); 
                    Employee1Window.setVisible(true);   
                    
                    canvas.removeAll(); //forces a wipe of the canvas
                    
                    Employee1Window.add(canvas);
                 
                }
            });

                 

            sprintEvalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFrame sprintEvalWindow = new JFrame("Employee View");
                    sprintEvalWindow.setSize(defaultFrameWidth, defaultFrameHeight);
                    sprintEvalWindow.setVisible(true);
                    frame.setVisible(false);
                }
            });

            
            
            
            canvas.add(employeeButton);
            canvas.add(sprintEvalButton);

            frame.add(canvas);
            frame.setVisible(true);
            });
        

    }
    
}
