package organiser.Contact;

import java.awt.Image;
import java.util.ArrayList;

import organiser.DataItem;
import organiser.DisplayPicture;
import organiser.DisplayableItem;
import organiser.Record;

public class ContactRecord implements Record {
	public static final String NAME = "Name";
	public static final String PICTURE = "Picture";
	public static final String HOMEPH = "Home Phone";
	public static final String WORKPH = "Work Phone";
	public static final String MOBILEPH = "Mobile Phone";
	public static final String EMAIL = "Email";
	public static final String HOMEADDRESS = "Home Address";
	public static final String WORKADDRESS = "Work Address";
	
	private DataItem<ContactName> name;
	private DataItem<DisplayPicture> picture;
	private DataItem<PhoneNumber> homePh;
	private DataItem<PhoneNumber> workPh;
	private DataItem<PhoneNumber> mobilePh;
	private DataItem<Email> email;
	private DataItem<Address> homeAddress;
	private DataItem<Address> workAddress;
	//allItems also acts as a container for custom attributes!
	private ArrayList<DataItem<? extends DisplayableItem>> allItems;
	
	public ContactRecord(){
		name = new DataItem<ContactName>(NAME,new ContactName());
		picture = new DataItem<DisplayPicture>(PICTURE, new DisplayPicture());
		homePh = new DataItem<PhoneNumber>(HOMEPH, new PhoneNumber());
		workPh = new DataItem<PhoneNumber>(WORKPH, new PhoneNumber());
		mobilePh = new DataItem<PhoneNumber>(MOBILEPH, new PhoneNumber());
		email = new DataItem<Email>(EMAIL, new Email());
		homeAddress = new DataItem<Address>(HOMEADDRESS, new Address());
		workAddress = new DataItem<Address>(WORKADDRESS, new Address());
	}
	
	@Override
	public Iterable<DataItem<? extends DisplayableItem>> getItems() {
		if(allItems == null){
			allItems = new ArrayList<DataItem<? extends DisplayableItem>>();
			allItems.add(name);
			allItems.add(picture);
			allItems.add(homePh);
			allItems.add(workPh);
			allItems.add(mobilePh);
			allItems.add(email);
			allItems.add(homeAddress);
			allItems.add(workAddress);
		}
		return allItems;
	}

	@Override
	public String getMainLabel() {
		return name.getValue().given + " " + name.getValue().surname;
	}

	@Override
	public Image getMainImage() {
		return picture.getValue().img;
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importData(Iterable<DataItem<? extends DisplayableItem>> items) {
		// TODO Auto-generated method stub
		
	}

}
