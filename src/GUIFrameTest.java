import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIFrameTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Employee View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLayout(new FlowLayout());

		JButton button = new JButton("Click Me!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button clicked!");
			}
		});

		frame.add(button);
		frame.setVisible(true);
	}
}
