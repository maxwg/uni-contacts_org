package organiser.contact;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	public static BufferedImage defaultImg;
	static {
		try{
			defaultImg = ImageIO.read(new File("src/organiser/res/blankDP.jpg"));
		}
		catch(IOException e){
			System.err.println("FAILURE TO LOAD VITAL RESOURCES!");
			System.exit(-1);
		}
	}
	
	public final DataItem<ContactName> name;
	public final DataItem<DisplayPicture> picture;
	public final DataItem<PhoneNumber> homePh;
	public final DataItem<PhoneNumber> workPh;
	public final DataItem<PhoneNumber> mobilePh;
	public final DataItem<Email> email;
	public final DataItem<Address> homeAddress;
	public final DataItem<Address> workAddress;
	//allItems also acts as a container for custom attributes!
	private ArrayList<DataItem<? extends DisplayableItem>> allItems;
	
	public ContactRecord(){
		name = new DataItem<ContactName>(NAME,new ContactName());
		picture = new DataItem<DisplayPicture>(PICTURE, new DisplayPicture());
		mobilePh = new DataItem<PhoneNumber>(MOBILEPH, new PhoneNumber());
		homePh = new DataItem<PhoneNumber>(HOMEPH, new PhoneNumber());
		workPh = new DataItem<PhoneNumber>(WORKPH, new PhoneNumber());
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
	
	public String getSecondaryLabel() { 
		//If there is no mobile, display email instead. Else nothing ("").
		return mobilePh.getValue().number == "" ? email.getValue().getEmail() : mobilePh.getValue().number;
	}
	
	@Override
	public BufferedImage getMainImage() {
		return picture.getValue().img != null ? picture.getValue().img : defaultImg;
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
