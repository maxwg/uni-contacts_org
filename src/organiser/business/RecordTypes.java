package organiser.business;

import java.util.ArrayList;

import organiser.business.contact.Address;
import organiser.business.contact.ContactName;
import organiser.business.contact.ContactRecord;
import organiser.business.contact.Email;
import organiser.business.contact.PhoneNumber;

public class RecordTypes {
	@SuppressWarnings("rawtypes")
	public static Class[] types;
	public static ArrayList<String> importantFields;
	static {
		types = new Class[] { DisplayPicture.class, PhoneNumber.class,
				Email.class, Address.class, ContactName.class };
		importantFields = new ArrayList<String>();
		importantFields.add(ContactRecord.NAME);
		importantFields.add(ContactRecord.PICTURE);
		importantFields.add(ContactRecord.MOBILEPH);
		importantFields.add(ContactRecord.EMAIL);
	}
	
	
}
