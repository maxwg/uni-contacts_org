package organiser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI implements Runnable, ActionListener{
	JFrame frame;
	ArrayList<? extends Record> records;
	JPanel contactsPane;
	JPanel detailsPane;
	
	public GUI(){
		SwingUtilities.invokeLater(this);
	}
	
	public void run(){
		frame = new JFrame("Contacts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		screenSize.height = screenSize.height > 768 ? 768 : screenSize.height;
		screenSize.width = screenSize.width > 800 ? 800 : screenSize.width;
		frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		frame.pack();
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				resizeScreen();
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		contactsPane = new JPanel(new BorderLayout(0,0));
		contactsPane.setBackground(Color.green);
		detailsPane = new JPanel(new BorderLayout(0,0));
		detailsPane.setBackground(Color.blue);
		resizeScreen();
		
		renderRecords();
		
		
		frame.getContentPane().add(contactsPane);
		frame.getContentPane().add(detailsPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void resizeScreen(){
		int frameWidth = frame.getContentPane().getWidth();
		int frameHeight = frame.getContentPane().getHeight();
		contactsPane.setSize(new Dimension(Math.min(264,frameWidth/3),frameHeight));
		detailsPane.setSize(new Dimension(frameWidth - contactsPane.getWidth(), frameHeight));
		detailsPane.setLocation(new Point(contactsPane.getWidth(), 0));
	}
	
	private void renderRecords(){
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
	}

}
