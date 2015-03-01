package organiser;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI implements Runnable, ActionListener{
	JFrame frame;
	
	public GUI(){
		SwingUtilities.invokeLater(this);
	}
	
	public void run(){
		frame = new JFrame("Contacts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		screenSize.height = screenSize.height > 768 ? 768 : screenSize.height;
		screenSize.width = screenSize.width > 1024 ? 1024 : screenSize.width;
		frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		
		JButton exit = new JButton();
		exit.setActionCommand("EXIT");
		exit.addActionListener(this);
		exit.setText("EXIT!");
		frame.getContentPane().add(exit);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new GUI();
	}

}
