package organiser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import organiser.contact.ContactRecord;

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

	private static RecordFactory instance;
	ArrayList<Record> records;
	File data;

	public RecordFactory() {
		data = new File("data/contacts");
		try {
			initializeXMLDBIfNeeded();
			importXMLDB();
		} catch (IOException e) {
			System.err.println("CANNOT ESTABLISH DATABASE!");
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println("DATABASE MAY BE CORRUPT!");
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.err
					.println("SOMETHING ELSE WENT WRONG - LIKELY FROM DB CORRUPTION!");
			e.printStackTrace();
		}
	}

	public static RecordFactory instance() {
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

	public void removeRecord(UUID id) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(data));
		File tmp = File.createTempFile("tmp", "");
		BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
		String line;
		boolean write = true;
		while ((line = reader.readLine()) != null) {
			if (line != ""
					&& getFirstTag(line).startsWith(RECORD)
					&& id.equals(UUID.fromString(line.substring(line
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
		data.delete();
		tmp.renameTo(data);
	}

	public void addRecord(Record r) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(data, true));
		writer.write(r.exportData());
		writer.flush();
		writer.close();
	}

	private void initializeXMLDBIfNeeded() throws IOException {
		if (!data.exists()) {
			data.getParentFile().mkdirs();
			data.createNewFile();
		}
	}

	/**
	 * Uses reflection to dynamically instantiate the Record class - such that
	 * recordFactory will work despite what types of records are used in the
	 * future.
	 * 
	 * @throws Exception
	 *             - Something went wrong with import - Corrupt DB likely
	 */
	private void importXMLDB() throws Exception {
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
