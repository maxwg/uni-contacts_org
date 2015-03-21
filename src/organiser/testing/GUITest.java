package organiser.testing;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import organiser.business.Record;

public class GUITest {
	private TestGUI gui;

	@Before
	public void setUp() throws Exception {
		gui = new TestGUI();
	}

	@Test
	public void testGUIAddRemoveUndoRecords() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						int startSize = gui.getLoadedRecords().size();
						ArrayList<Record> added = new ArrayList<Record>();
						for (int i = 1; i < 6; i++) {
							added.add(gui.addNewRecord());
							Assert.assertTrue(startSize + i == gui
									.getLoadedRecords().size());
						}
						for (int i = 1; i < added.size() + 1; i++) {
							gui.deleteCurrentRecord();
							Assert.assertTrue(startSize + added.size() - i == gui
									.getLoadedRecords().size());
							Assert.assertTrue(i == gui.getDeletedRecords()
									.size());
						}
						for (int i = 1; i < added.size() + 1; i++) {
							gui.selectedRecord.curRecord.setNeedsSave(false);
							gui.undoRecordDelete();
							Assert.assertTrue(startSize + i == gui
									.getLoadedRecords().size());
						}
						for (int i = 1; i < added.size() + 1; i++) {
							gui.deleteCurrentRecord();
							Assert.assertTrue(startSize + added.size() - i == gui
									.getLoadedRecords().size());
							Assert.assertTrue(i == gui.getDeletedRecords()
									.size());
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						fail("Exception occurred : " + e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	public void testGUIUpdatesAsExpected() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						Record r = gui.addNewRecord();
						Assert.assertTrue(gui.selectedRecord.curRecord == r);
						Assert.assertTrue(gui.getDetailPanel().curRecord == r);
						gui.deleteCurrentRecord();
						Assert.assertTrue(gui.selectedRecord.curRecord != r);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						fail("Exception occurred : " + e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	public void testSaveIsPersistent() {
		final ArrayList<Record> records = new ArrayList<Record>();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						for (int i = 0; i < 5; i++) {
							records.add(gui.addNewRecord());
							gui.saveCurrentRecord();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						fail("Exception occurred : " + e.getMessage());
					}
				}
			});
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						System.out.println(gui.getLoadedRecords().contains(records.get(0)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						fail("Exception occurred : " + e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown: " + e.getMessage());
		}
	}
}
