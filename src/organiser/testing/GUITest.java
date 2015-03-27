package organiser.testing;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import organiser.business.Record;
import organiser.business.RecordFactory;
import organiser.gui.GUI;
import organiser.gui.RecordPaneItem;

public class GUITest {
	private TestGUI gui;

	@Before
	public void setUp() throws Exception {
		gui = new TestGUI();
	}

	@Test
	/**
	 * This is indeed a test for a test class!
	 * This test ensures that anything done in the testing environment
	 * does not leak into the main database - i.e, ensures isolation.
	 * 
	 * This is done by creating a normal GUI and counting the records
	 * in the main database, creating a TestGUI and adding 10 new records,
	 * and recreating a normal GUI and counting the records, asserting
	 * that first two instances must be the same
	 */
	public void testThatTestGUIsDoNotLeakIntoMainDB() {
		// This list is a hack, as java can only access finals,
		// but cannot set the value of finals.
		final ArrayList<Integer> recordCounts = new ArrayList<Integer>();
		try {
			final GUI gui1 = new GUI();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					recordCounts.add(gui1.recordCount());
				}
			});
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < 10; i++) {
							gui.addNewRecord();
						}
					} catch (Exception e) {
						e.printStackTrace();
						fail("Exception occurred : " + e.getMessage());
					}
				}
			});

			final GUI gui2 = new GUI();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					recordCounts.add(gui2.recordCount());
				}
			});
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Assert.assertTrue(recordCounts.get(0) == recordCounts
							.get(1));
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	/**
	 * This test adds six records, removes them, undos the removal,
	 * and removes them again, all whilst asserting that the number
	 * of records in various lists of the program are as expected.
	 * 
	 * This involves the use of the addNewRecord() method (executed when the
	 * use clicks add), deleteCurrentRecord() method (executed when the user
	 * presses delete) and undoRecordDelete(). These methods run almost every
	 * essential method of the GUI and its factory.
	 */
	public void testGUIAddRemoveUndoRecords() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						int expectedSize = gui.getLoadedRecords().size();
						ArrayList<Record> added = new ArrayList<Record>();
						for (int i = 1; i < 6; i++) {
							added.add(gui.addNewRecord());
							expectedSize++;
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
							Assert.assertTrue(expectedSize == gui
									.getSidePanel().items.size());
						}
						boolean reachedZero = false;
						for (int i = 1; expectedSize > 0 && !reachedZero; i++) {
							gui.deleteCurrentRecord();
							expectedSize--;
							reachedZero = expectedSize == 0 ? true
									: reachedZero;
							expectedSize = expectedSize == 0 ? 1 : expectedSize;
							// Expect that the GUI adds new record when empty.
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
							Assert.assertTrue(expectedSize == gui
									.getSidePanel().items.size());
							Assert.assertTrue(i == gui.getDeletedRecords()
									.size());
						}
						while (gui.getDeletedRecords().size() > 0) {
							gui.selectedRecord.curRecord.setNeedsSave(false);
							gui.undoRecordDelete();
							expectedSize++;
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
							Assert.assertTrue(expectedSize == gui
									.getSidePanel().items.size());
						}
						for (int i = 1; expectedSize > 0 + 1; i++) {
							gui.deleteCurrentRecord();
							expectedSize--;
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
							Assert.assertTrue(expectedSize == gui
									.getSidePanel().items.size());
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
	/**
	 * This tests that newly added records are the records in 
	 * focus, and that by deleting the record, the record loses
	 * focus.
	 * 
	 * In the future, a more comprehensive test with component
	 * position assertions/etc could be implemented.
	 */
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
	/**
	 * This runs the save method, and asserts
	 * that saved records persist through different
	 * runnings of the program.
	 */
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

						for (Record r : records) {
							boolean in = false;
							for (RecordPaneItem rpi : gui.getLoadedRecords()) {
								if (rpi.curRecord.equals(r))
									in = true;
							}
							if (!in)
								fail("Added record was not found when re-initialising the program!");
							RecordFactory.instance().removeRecord(r);
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
}
