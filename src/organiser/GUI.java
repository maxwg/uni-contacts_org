package organiser;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import organiser.contact.ContactName;
import organiser.contact.ContactRecord;
import organiser.contact.Email;

public class GUI implements Runnable, ActionListener, Resizable {
	JFrame frame;
	SidePanel contactsPane;
	DetailPanel detailsPane;
	TopMenu topMenu;
	ModernScrollPane contactsPaneScroll;
	ModernScrollPane detailsPaneScroll;
	List<RecordPaneItem> loadedRecords;
	RecordPaneItem selectedRecord;
	Record curRecordCache;

	public GUI() {
		SwingUtilities.invokeLater(this);
	}

	public void run() {
		frame = new JFrame("Contacts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setUndecorated(true);
		Rectangle screenSize = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		screenSize.height = screenSize.height > 660 ? 660 : screenSize.height;
		screenSize.width = screenSize.width > 750 ? 750 : screenSize.width;
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
		try {
			renderRecords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.getContentPane().add(contactsPaneScroll);
		frame.getContentPane().add(detailsPaneScroll);

		topMenu = new TopMenu(frame, this);
		frame.getContentPane().add(topMenu);

		frame.pack();
		frame.setVisible(true);
		manageResize();
	}

	private void renderRecords() throws FontFormatException, IOException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		loadedRecords = new ArrayList<RecordPaneItem>();
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
				RecordPaneItem p = new RecordPaneItem(r, this);
				contactsPane.add(p);
			}
		}
		for (Record r : RecordFactory.instance().getRecords()) {
			final RecordPaneItem tag = new RecordPaneItem(r, this);
			loadedRecords.add(tag);
			contactsPane.add(tag);
		}
	}

	public void refreshRecordDisplay() {
		if (selectedRecord != null)
			selectedRecord.refreshPanel();
	}
	
	public boolean currentRecordNeedsSave(){
		if (selectedRecord != null)
			return selectedRecord.curRecord.needsSave();
		return false;
	}

	public void saveCurrentRecord() throws IOException {
		if(selectedRecord != null)
			selectedRecord.curRecord.Save();
	}

	public void manageResize() {
		int frameWidth = frame.getContentPane().getWidth();
		int frameHeight = frame.getContentPane().getHeight();
		contactsPane.setSize(new Dimension(Math.min(264, frameWidth / 3),
				frameHeight - TopMenu.HEIGHT));
		contactsPaneScroll.setSize(new Dimension(Math.min(264, frameWidth / 3),
				frameHeight - TopMenu.HEIGHT));
		contactsPaneScroll.setLocation(new Point(0, TopMenu.HEIGHT));
		detailsPane.setSize(new Dimension(frameWidth - contactsPane.getWidth(),
				frameHeight - TopMenu.HEIGHT));
		detailsPaneScroll.setSize(new Dimension(frameWidth
				- contactsPane.getWidth(), frameHeight - TopMenu.HEIGHT));
		detailsPaneScroll.setLocation(new Point(contactsPane.getWidth(),
				TopMenu.HEIGHT));
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
