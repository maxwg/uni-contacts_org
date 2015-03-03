package organiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import organiser.contact.ContactName;
import organiser.contact.ContactRecord;

public class GUI implements Runnable, ActionListener{
	JFrame frame;
	List<Record> records;
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
		
		contactsPane = new JPanel(null);
		contactsPane.setBackground(Color.green);
		detailsPane = new JPanel(null);
		detailsPane.setBackground(Color.blue);
		JScrollPane contactsPaneScroll = new JScrollPane(contactsPane);
		JScrollPane detailsPaneScroll = new JScrollPane(detailsPane);
		contactsPaneScroll.setBorder(BorderFactory.createEmptyBorder());
		detailsPaneScroll.setBorder(BorderFactory.createEmptyBorder());
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
		records = RecordFactory.instance().getRecords();
		if(records.size() == 0){
			ContactRecord r = new ContactRecord();
			r.name.setValue(new ContactName("JAMES", "DUDE"));
			RecordPaneItem p = getRecordSummaryPanel(r);
			contactsPane.add(p);
		}
	}
	
	private RecordPaneItem getRecordSummaryPanel(Record record){
		RecordPaneItem panel = new RecordPaneItem(record.getMainImage(), record.getMainLabel());
		return panel;
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
