package organiser.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import organiser.business.Record;
import organiser.business.RecordFactory;
import organiser.business.contact.ContactRecord;
import organiser.modernUIElements.ModernScrollPane;

public class GUI implements Runnable, Resizable {
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
		detailsPane = new DetailPanel(this);
		contactsPaneScroll = new ModernScrollPane(contactsPane, 24, 42);
		detailsPaneScroll = new ModernScrollPane(detailsPane, 42, 24);
		ResizeListener.AttachResizeEvent(contactsPane, frame);
		ResizeListener.AttachResizeEvent(detailsPane, frame);
		try {
			topMenu = new TopMenu(frame, this);
			renderRecords();
		} catch (Exception e) {
			int opt = JOptionPane
					.showConfirmDialog(
							null,
							"Unable to import records! Database may be corrupt!\n\nWould you like to reset the database? (Warning: This will clear all data!)\n\n\nMore info: "
									+ e.getClass() + ": " + e.getMessage(),
									"Unable to load Database", JOptionPane.YES_NO_OPTION);
			if(opt == JOptionPane.YES_OPTION){
				try {
					File f = new File(RecordFactory.DBLOC);
					f.delete();
					f.createNewFile();
				} catch (Exception e1) {
					System.err.println("It seems that a broken DB wasn't the problem!");
					e1.printStackTrace();
				}
			}
			else{
				opt = JOptionPane
						.showConfirmDialog(
								null,
								"Would you like to try manually fixing the error?\n\nMore info:"
										+ e.getClass() + ": " + e.getMessage(),
										"Unable to load Database", JOptionPane.YES_NO_OPTION);
				if(opt == JOptionPane.YES_OPTION){
					try {
						Runtime.getRuntime().exec("gedit "+RecordFactory.DBLOC);
						Runtime.getRuntime().exec("notepad.exe "+RecordFactory.DBLOC);
					} catch (IOException e1) {
						// Currently supports only Windows and linux versions with gedit.
						// Ignore error - Really, we could check what OS user is using,
						// but this is endgame anyways. The user can figure out a 
						// better XML editor themselves.
					}
				}
				System.exit(-1);
			}
		}

		frame.getContentPane().add(contactsPaneScroll);
		frame.getContentPane().add(detailsPaneScroll);

		frame.getContentPane().add(topMenu);

		frame.pack();
		frame.setVisible(true);
		manageResize();
	}

	private void renderRecords() throws Exception {
		loadedRecords = new ArrayList<RecordPaneItem>();
		deletedRecords = new ArrayList<RecordPaneItem>();

		for (Record r : RecordFactory.instance().getRecords()) {
			addToContactsPane(r, false);
		}
	}

	public void addNewRecord() throws Exception {
		ContactRecord r = new ContactRecord();
		r.name.getValue().given = "New";
		r.name.getValue().surname = "Person";
		r.Save();
		detailsPane.loadRecord(addToContactsPane(r, true));
	}

	public void undoRecordDelete() throws Exception {
		showSaveDialog();
		if (deletedRecords.size() > 0) {
			RecordPaneItem recovered = deletedRecords
					.get(deletedRecords.size() - 1);
			contactsPane.add(recovered);
			loadedRecords.add(recovered);
			detailsPane.loadRecord(recovered);
			deletedRecords.remove(deletedRecords.size() - 1);
			this.selectedRecord.curRecord.setNeedsSave(true);
			manageResize();
		}
	}

	public void undoRecordChanges() throws Exception {
		System.out.println("YEA");
		this.selectedRecord.curRecord = curRecordCache.deepCopy();
		this.detailsPane.loadRecord(this.selectedRecord);
		this.detailsPane.refreshPanel(false);
	}

	public RecordPaneItem addToContactsPane(Record r, boolean select)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			FontFormatException, IOException {
		final RecordPaneItem tag = new RecordPaneItem(r, this);
		if (select)
			detailsPane.loadRecord(tag);
		loadedRecords.add(tag);
		contactsPane.add(tag);
		manageResize();
		return tag;
	}

	public void deleteCurrentRecord() throws Exception {
		RecordFactory.instance().removeRecord(selectedRecord.curRecord);
		int rpos = contactsPane.items.indexOf(selectedRecord);
		contactsPane.items.remove(rpos);
		deletedRecords.add(selectedRecord);
		loadedRecords.remove(selectedRecord);
		contactsPane.reRender();
		if (loadedRecords.size() > 0)
			detailsPane.loadRecord(loadedRecords.get(Math.max(0, rpos - 1)));
		else {
			addNewRecord();
		}
		manageResize();
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

	public void saveCurrentRecord() throws Exception {
		if (selectedRecord != null)
			selectedRecord.curRecord.Save();
	}

	public int showSaveDialog() throws Exception {
		if (currentRecordNeedsSave()) {
			int option = JOptionPane.showConfirmDialog(selectedRecord,
					"MATE YOU HAVENT SAVED. SAVE NOW?");
			if (option == JOptionPane.CANCEL_OPTION)
				return JOptionPane.CANCEL_OPTION;
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
			return option;
		}
		return JOptionPane.YES_NO_OPTION;
	}

	public void manageResize() {
		int frameWidth = frame.getContentPane().getWidth();
		int frameHeight = frame.getContentPane().getHeight();
		contactsPane
				.setSize(new Dimension(Math.max(56,
						Math.min(264, frameWidth - 424)), frameHeight
						- TopMenu.HEIGHT));
		contactsPaneScroll
				.setSize(new Dimension(Math.max(56,
						Math.min(264, frameWidth - 424)), frameHeight
						- TopMenu.HEIGHT));
		contactsPaneScroll.setLocation(new Point(0, TopMenu.HEIGHT));
		detailsPane.setSize(new Dimension(frameWidth - contactsPane.getWidth(),
				frameHeight - TopMenu.HEIGHT));
		detailsPaneScroll.setSize(new Dimension(frameWidth
				- contactsPane.getWidth(), frameHeight - TopMenu.HEIGHT));
		detailsPaneScroll.setLocation(new Point(contactsPane.getWidth(),
				TopMenu.HEIGHT));
		topMenu.setSize(frameWidth, topMenu.HEIGHT);
		topMenu.manageResize();
	}
}
