package organiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import organiser.contact.ContactName;
import organiser.contact.ContactRecord;
import organiser.contact.Email;

public class GUI implements Runnable, ActionListener, Resizable {
	JFrame frame;
	SidePanel contactsPane;
	DetailPanel detailsPane;
	ModernScrollPane contactsPaneScroll;
	ModernScrollPane detailsPaneScroll;

	public GUI() {
		SwingUtilities.invokeLater(this);
	}

	public void run() {
		frame = new JFrame("Contacts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Rectangle screenSize = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		screenSize.height = screenSize.height > 600 ? 600 : screenSize.height;
		screenSize.width = screenSize.width > 900 ? 900 : screenSize.width;
		frame.setPreferredSize(new Dimension(screenSize.width,
				screenSize.height));
		frame.pack();
		ResizeListener.AttachResizeEvent(this, frame);

		contactsPane = new SidePanel();
		detailsPane = new DetailPanel();
		contactsPaneScroll = new ModernScrollPane(contactsPane, 24, 42);
		detailsPaneScroll = new ModernScrollPane(detailsPane, 42, 24);
		ResizeListener.AttachResizeEvent(contactsPane, frame);
		ResizeListener.AttachResizeEvent(detailsPane, frame);
		renderRecords();

		frame.getContentPane().add(contactsPaneScroll);
		frame.getContentPane().add(detailsPaneScroll);
		frame.pack();
		frame.setVisible(true);
		manageResize();
	}

	private void renderRecords() {
		if (RecordFactory.instance().getRecords().size() == 0) {
			for (int i = 0; i < 25; i++) {
				ContactRecord r = new ContactRecord();
				r.name.setValue(new ContactName("JAMES", "DUDE"));
				r.email.setValue(new Email("JAMES@DUDE.COM"));
				try {
					r.Save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RecordPaneItem p = getRecordSummaryPanel(r);
				System.out.println(r.exportData());
				contactsPane.add(p);
			}
		}
		for(Record r : RecordFactory.instance().getRecords()){
			RecordPaneItem tag = getRecordSummaryPanel(r);
			contactsPane.add(tag);
		}
	}

	private RecordPaneItem getRecordSummaryPanel(Record record) {
		RecordPaneItem panel = new RecordPaneItem(record.getMainImage(),
				record.getMainLabel(), record.getSecondaryLabel());
		return panel;
	}

	public void manageResize() {
		int frameWidth = frame.getContentPane().getWidth();
		int frameHeight = frame.getContentPane().getHeight();
		contactsPane.setSize(new Dimension(Math.min(264, frameWidth / 3),
				frameHeight));
		contactsPaneScroll.setSize(new Dimension(Math.min(264, frameWidth / 3),
				frameHeight));
		detailsPane.setSize(new Dimension(frameWidth - contactsPane.getWidth(),
				frameHeight));
		detailsPaneScroll.setSize(new Dimension(frameWidth
				- contactsPane.getWidth(), frameHeight));
		detailsPaneScroll.setLocation(new Point(contactsPane.getWidth(), 0));
	}

	public static void main(String[] args) {
		new GUI();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
	}

}
