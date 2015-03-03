package organiser;

import java.util.ArrayList;
import java.util.List;

public class RecordFactory {
	private static RecordFactory instance;
	ArrayList<Record> contacts;
	public static RecordFactory instance(){
		if(instance == null){
			instance = new RecordFactory();
		}
		return instance;
	}
	public List<Record> getRecords(){
		if(contacts == null)
			contacts = new ArrayList<Record>();
		return contacts;
	}
}
