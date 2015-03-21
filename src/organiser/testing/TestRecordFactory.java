package organiser.testing;

import java.io.File;
import java.io.IOException;

import organiser.business.RecordFactory;

public class TestRecordFactory extends RecordFactory {

	public TestRecordFactory() throws Exception {
		dbLoc = "data/testing/contacts";
		bakLoc = DBLOC+"bak";
		data = new File(dbLoc);
		data.delete();
		initializeXMLDBIfNeeded();
		importXMLDB();
	}
	
	public void importXMLDBExposed() throws Exception{
		importXMLDB();
	}
	
	public void initializeXMLDBIfNeededExposed() throws IOException{
		initializeXMLDBIfNeeded();
	}
}
