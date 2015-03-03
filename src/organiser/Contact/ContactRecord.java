package organiser.Contact;

import java.awt.Image;

import organiser.DataItem;
import organiser.Record;

public class ContactRecord implements Record {
	private DataItem<ContactName> name;
	private DataItem<PhoneNumber> homePh;
	private DataItem<PhoneNumber> workPh;
	private DataItem<PhoneNumber> mobilePh;
	private DataItem<Email> email;
	private DataItem<Address> homeAddress;
	private DataItem<Address> workAddress;
	
	@Override
	public Iterable<DataItem<Object>> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMainLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getMainImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}

}
