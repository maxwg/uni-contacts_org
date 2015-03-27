package organiser.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import organiser.business.contact.ContactRecord;

public class RecordFactoryTest {
	private TestRecordFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new TestRecordFactory();
		assertTrue(factory.getRecords().size() == 0);
	}

	/**
	 * Adds n records to the factory.
	 * @param n - number of records
	 */
	private void populateRecords(int n) throws Exception{
		for (int i = 0; i < n; i++) {
			factory.addRecord(new ContactRecord(factory));
			factory.importXMLDBExposed();
		}
	}
	
	@Test
	/**
	 * Tests that initializing the DB does not override the
	 * current DB if a DB already exists.
	 */
	public void testInitializingDoesNotOverride(){
		try {
			populateRecords(3);
			assertTrue(factory.getRecords().size()==3);
			factory.initializeXMLDBIfNeededExposed();
			factory.importXMLDBExposed();
			assertTrue(factory.getRecords().size()==3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception Occurred: " + e.getMessage());
		}
	}
	
	@Test
	/**
	 * Tests records populate correctly.
	 */
	public void testAddingRecords() {
		try {
			for (int i = 0; i < 10; i++) {
				populateRecords(1);
				assertTrue(factory.getRecords().size()==i+1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception Occurred: " + e.getMessage());
		}
	}
	
	@Test
	/**
	 * Tests records remove correctly
	 */
	public void testRemovingRecords(){
		try {
			populateRecords(10);
			assertTrue(factory.getRecords().size()==10);
			for(int i = 0; i<10; i++){
				factory.removeRecord(factory.getRecords().get(0));
				factory.importXMLDBExposed();
				assertTrue(factory.getRecords().size() == 9-i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception was thrown: " + e.getMessage());
		}
	}

}
