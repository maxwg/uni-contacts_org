package organiser;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	List<RecordPaneItem> deletedRecords;
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
		deletedRecords = new ArrayList<RecordPaneItem>();
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
			addToContactsPane(r, false);
		}
	}
	
	public void addNewRecord() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException{
		ContactRecord r = new ContactRecord();
		r.name.getValue().given = "New";
		r.name.getValue().surname = "Person";
		r.Save();
		addToContactsPane(r, true);
		detailsPane.loadRecord(r);
	}
	
	public void undoRecordDelete() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException, IOException{
		if (deletedRecords.size()>0){
			RecordPaneItem recovered = deletedRecords.get(deletedRecords.size()-1);
			selectedRecord = recovered;
			contactsPane.add(recovered);
			loadedRecords.add(recovered);
			detailsPane.loadRecord(recovered.curRecord);
			deletedRecords.remove(deletedRecords.size()-1);
		}
	}
	
	public RecordPaneItem addToContactsPane(Record r, boolean select){
		final RecordPaneItem tag = new RecordPaneItem(r, this);
		if (select) selectedRecord = tag;
		loadedRecords.add(tag);
		contactsPane.add(tag);
		return tag;
	}
	
	public void deleteCurrentRecord() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException{
		RecordFactory.instance().removeRecord(selectedRecord.curRecord);
		contactsPane.items.remove(selectedRecord);
		deletedRecords.add(selectedRecord);
		loadedRecords.remove(selectedRecord);
		contactsPane.reRender();
		detailsPane.loadRecord(loadedRecords.get(0).curRecord);
		selectedRecord = loadedRecords.get(0);
	}

	public void refreshRecordDisplay() {
		if (selectedRecord != null)
			selectedRecord.refreshPanel();
	}

	public boolean currentRecordNeedsSave() {
		if (selectedRecord != null)
			return selectedRecord.curRecord.needsSave();
		return false;
	}

	public void saveCurrentRecord() throws IOException {
		if (selectedRecord != null)
			selectedRecord.curRecord.Save();
	}

	public void showSaveDialog() throws IOException {
		if (currentRecordNeedsSave()) {
			int option = JOptionPane.showConfirmDialog(selectedRecord,
					"MATE YOU HAVENT SAVED. SAVE NOW?");
			if (option == JOptionPane.CANCEL_OPTION)
				return;
			else if (option == JOptionPane.YES_OPTION) {
				saveCurrentRecord();
				refreshRecordDisplay();
			} else if (option == JOptionPane.NO_OPTION) {
				try {
					selectedRecord.curRecord = curRecordCache.deepCopy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
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
