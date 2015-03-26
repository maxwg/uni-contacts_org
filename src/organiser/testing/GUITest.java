package organiser.testing;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
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
	public void testThatTestGUIsDoNotLeakIntoMainDB() {
		// These two lists are hacks, as java can only access finals,
		// but cannot set the value of finals.
		final ArrayList<Integer> recordCounts = new ArrayList<Integer>();
		// We need to call an invokeLater to ensure the GUI has finished loading
		final ArrayList<GUI> guis = new ArrayList<GUI>();
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					guis.add(new GUI());
				}
			});
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					recordCounts.add(guis.get(0).recordCount());
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
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					guis.add(new GUI());
				}
			});
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					recordCounts.add(guis.get(1).recordCount());
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
						}
						boolean reachedZero = false;
						for (int i = 1; expectedSize > 0 && !reachedZero; i++) {
							gui.deleteCurrentRecord();
							expectedSize--;
							reachedZero = expectedSize == 0 ? true : reachedZero;
							expectedSize = expectedSize == 0 ? 1 : expectedSize;
							// Expect that the GUI adds new record when empty.
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
							Assert.assertTrue(i == gui.getDeletedRecords()
									.size());
						}
						while( gui.getDeletedRecords().size()>0) {
							gui.selectedRecord.curRecord.setNeedsSave(false);
							gui.undoRecordDelete();
							expectedSize++;
							Assert.assertTrue(expectedSize == gui
									.getLoadedRecords().size());
						}
						for (int i = 1; expectedSize>0 + 1; i++) {
							gui.deleteCurrentRecord();
							expectedSize--;
							Assert.assertTrue(expectedSize == gui
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
