package organiser.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author max This class manages the creation and deletion of records which
 *         have been saves to the 'database', and acts as a 'database'
 *         interface.
 */
public class RecordFactory {
	private static final int NONE = 0;
	private static final int INRECORD = 1;
	public static final String RECORD = "Record";
	public static final String DBLOC = "data/contacts";
	public static final String BAKLOC = DBLOC+"bak";

	protected static RecordFactory instance;
	private List<Record> records;
	protected File data;
	protected String dbLoc;
	protected String bakLoc;

	public RecordFactory() throws Exception {
		dbLoc = DBLOC;
		bakLoc = BAKLOC;
		data = new File(dbLoc);
		initializeXMLDBIfNeeded();
		importXMLDB();
	}

	public static RecordFactory instance() throws Exception {
		if (instance == null) {
			instance = new RecordFactory();
		}
		return instance;
	}

	public List<Record> getRecords() {
		if (records == null)
			records = new ArrayList<Record>();
		return records;
	}

	public void removeRecord(Record record) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(data));
		File tmp = File.createTempFile("tmp", "");
		BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
		String line;
		boolean write = true;
		while ((line = reader.readLine()) != null) {
			if (line != ""
					&& getFirstTag(line).startsWith(RECORD)
					&& record.getID()
							.equals(UUID.fromString(line.substring(line
									.indexOf('>') + 1)))) {
				write = false;
			} else {
				if (write)
					writer.write(line + '\n');
				else if (getFirstTag(line).equals("/" + RECORD))
					write = true;
			}
		}
		reader.close();
		writer.flush();
		writer.close();
		File databak = new File(
				bakLoc
						+ (new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss")
								.format(new Date())));
		databak.getParentFile().mkdirs();
		databak.createNewFile();
		data.renameTo(databak);
		tmp.renameTo(new File(dbLoc));
		records.remove(record);
	}

	public void addRecord(Record r) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(data, true));
		writer.write(r.exportData());
		writer.flush();
		writer.close();
	}

	protected void initializeXMLDBIfNeeded() throws IOException {
		if (!data.exists()) {
			data.getParentFile().mkdirs();
			data.createNewFile();
		}
	}

	/**
	 * Uses reflection to dynamically instantiate the Record class.
	 * This allows RecordFactory to support any record type in the
	 * future.
	 * 
	 * @throws Exception
	 *             - Something went wrong with import - Corrupt DB likely
	 */
	protected void importXMLDB() throws Exception {
		getRecords().clear();
		BufferedReader br = new BufferedReader(new FileReader(data));
		String line;
		String data = "";
		String lineData;
		int status = NONE;
		Record curRecord = null;
		while ((line = br.readLine()) != null) {
			switch (status) {
			case NONE: {
				if (line != ""
						&& (lineData = getFirstTag(line)).startsWith(RECORD)) {
					data = "";
					String recordType = lineData.substring(RECORD.length()
							+ "class".length() + 2);
					curRecord = (Record) Class.forName(recordType)
							.newInstance();
					curRecord.setID(UUID.fromString(line.substring(lineData
							.length() + 2)));
					status = INRECORD;
				} else {
					throw new NotImplementedException(); // Does not yet support
															// other records
				}
				break;
			}
			case INRECORD: {
				if (!(getFirstTag(line).equals("/" + RECORD)))
					data += line + '\n';
				else {
					curRecord.importItem(data);
					getRecords().add(curRecord);
					status = NONE;
				}

				break;
			}
			}
		}
		br.close();
		Collections.sort(getRecords());
	}

	public static String getFirstTag(String line) {
		int start = line.indexOf('<') + 1;
		int end = line.indexOf('>');
		return line.substring(start, end);
	}

	public static String getTagValue(String line) {
		int start = line.indexOf('>') + 1;
		int end = line.indexOf('<', line.indexOf('<') + 1);
		return line.substring(start, end);
	}
}
