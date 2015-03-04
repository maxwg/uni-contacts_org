package organiser.contact;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

import organiser.DataItem;
import organiser.DataItemValue;
import organiser.DisplayPicture;
import organiser.Record;
import organiser.RecordFactory;

public class ContactRecord implements Record {
	public static final String XMLTITLE = "ContactRecord";
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
		try {
			defaultImg = ImageIO
					.read(new File("src/organiser/res/blankDP.jpg"));
		} catch (IOException e) {
			System.err.println("FAILURE TO LOAD VITAL RESOURCES!");
			System.exit(-1);
		}
	}

	public DataItem<ContactName> name;
	public DataItem<DisplayPicture> picture;
	public DataItem<PhoneNumber> homePh;
	public DataItem<PhoneNumber> workPh;
	public DataItem<PhoneNumber> mobilePh;
	public DataItem<Email> email;
	public DataItem<Address> homeAddress;
	public DataItem<Address> workAddress;
	// allItems also acts as a container for custom attributes!
	private ArrayList<DataItem<? extends DataItemValue>> allItems;
	private UUID id;

	public ContactRecord() {
		id = UUID.randomUUID();
		name = new DataItem<ContactName>(NAME, new ContactName());
		picture = new DataItem<DisplayPicture>(PICTURE, new DisplayPicture());
		mobilePh = new DataItem<PhoneNumber>(MOBILEPH, new PhoneNumber());
		homePh = new DataItem<PhoneNumber>(HOMEPH, new PhoneNumber());
		workPh = new DataItem<PhoneNumber>(WORKPH, new PhoneNumber());
		email = new DataItem<Email>(EMAIL, new Email());
		homeAddress = new DataItem<Address>(HOMEADDRESS, new Address());
		workAddress = new DataItem<Address>(WORKADDRESS, new Address());
	}

	@Override
	public Iterable<DataItem<? extends DataItemValue>> getItems() {
		if (allItems == null) {
			allItems = new ArrayList<DataItem<? extends DataItemValue>>();
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
		// If there is no mobile, display email instead. Else nothing ("").
		return mobilePh.getValue().number.equals("") ? email.getValue().getEmail()
				: mobilePh.getValue().number;
	}

	@Override
	public BufferedImage getMainImage() {
		return picture.getValue().img != null ? picture.getValue().img
				: defaultImg;
	}

	@Override
	public UUID getID() {
		return id;
	}

	@Override
	public void setID(UUID id) {
		this.id = id;
	}

	@Override
	public void Save() throws IOException {
		RecordFactory.instance().removeRecord(this.getID());
		RecordFactory.instance().addRecord(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void importItem(String itemXML) throws Exception {
		String[] dataItems = itemXML.split("" + '\n');
		for (String item : dataItems) {
			Class valueClass = Class.forName(
					RecordFactory.getFirstTag(item).substring(6));
			DataItemValue value = (DataItemValue) valueClass.newInstance();
			String innerData = RecordFactory.getTagValue(item);
			value.ImportXMLData(innerData.substring(innerData.indexOf('\1')+1));
			String label = innerData.substring(0,innerData.indexOf('\1'));
			System.out.println(label);
			DataItem<?> dataItem = new DataItem<DataItemValue>(label,value);
			if(valueClass == ContactName.class && label.equals(NAME))
				name = (DataItem<ContactName>)dataItem;
			if(valueClass == DisplayPicture.class && label.equals(PICTURE))
				picture = (DataItem<DisplayPicture>)dataItem;
			if(valueClass == PhoneNumber.class && label.equals(MOBILEPH))
				mobilePh = (DataItem<PhoneNumber>)dataItem;
			if(valueClass == Email.class && label.equals(EMAIL))
				email = (DataItem<Email>)dataItem;
		}
	}

	@Override
	public String exportData() {
		StringBuilder xml = new StringBuilder();
		xml.append(startXML(XMLTITLE) + getID() + '\n');
		for (DataItem<? extends DataItemValue> attr : getItems()) {
			xml.append(startXML(attr.getValue().getClass().toString()));
			xml.append(attr.getLabel());
			xml.append('\1');
			xml.append(attr.getValue().ToXML());
			xml.append(endXML(attr.getValue().getClass().toString()));
		}
		xml.append(endXML(XMLTITLE));
		return xml.toString();
	}

	public static String startXML(String s) {
		return "<" + s + ">";
	}

	public static String endXML(String s) {
		return "</" + s + ">\n";
	}
}
