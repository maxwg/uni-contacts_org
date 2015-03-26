package organiser.business.contact;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.hamcrest.Factory;

import organiser.business.DataItem;
import organiser.business.DataItemValue;
import organiser.business.DisplayPictureEmpty;
import organiser.business.Record;
import organiser.business.RecordFactory;

public class ContactRecord implements Record {
	public static final String NAME = "Name";
	public static final String PICTURE = "MainDisplayPictureImage";
	public static final String MOBILEPH = "Mobile";
	public static final String HOMEPH = "Home Ph";
	public static final String WORKPH = "Work Ph";
	public static final String EMAIL = "Email";
	public static final String ADDRESS = "Address";
	public static BufferedImage defaultImg;
	static {
		try {
			defaultImg = ImageIO
					.read(ContactRecord.class.getResourceAsStream("/organiser/res/blankDP.png"));
		} catch (IOException e) {
			System.err.println("FAILURE TO LOAD VITAL RESOURCES!");
			System.exit(-1);
		}
	}

	public DataItem<ContactName> name;
	public DataItem<DisplayPictureEmpty> picture;
	public DataItem<PhoneNumber> homePh;
	public DataItem<PhoneNumber> workPh;
	public DataItem<PhoneNumber> mobilePh;
	public DataItem<Email> email;
	public DataItem<Address> homeAddress;
	// allItems also acts as a container for custom attributes!
	private ArrayList<DataItem<? extends DataItemValue>> allItems;
	private UUID id;
	private boolean needsSave;
	RecordFactory Factory;
	
	public ContactRecord(RecordFactory Factory) {
		this.Factory = Factory;
		id = UUID.randomUUID();
		name = new DataItem<ContactName>(NAME, new ContactName());
		mobilePh = new DataItem<PhoneNumber>(MOBILEPH, new PhoneNumber());
		homePh = new DataItem<PhoneNumber>(HOMEPH, new PhoneNumber());
		workPh = new DataItem<PhoneNumber>(WORKPH, new PhoneNumber());
		email = new DataItem<Email>(EMAIL, new Email());
		homeAddress = new DataItem<Address>(ADDRESS, new Address());
		picture = new DataItem<DisplayPictureEmpty>(PICTURE, new DisplayPictureEmpty());
		needsSave = false;
	}

	@Override
	public List<DataItem<? extends DataItemValue>> getItems() {
		if (allItems == null) {
			allItems = new ArrayList<DataItem<? extends DataItemValue>>();
			allItems.add(name);
			allItems.add(mobilePh);
			allItems.add(homePh);
			allItems.add(workPh);
			allItems.add(email);
			allItems.add(homeAddress);
			allItems.add(picture);
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
		return mobilePh.getValue().number.equals("") ? email.getValue()
				.getEmail() : mobilePh.getValue().number;
	}

	@Override
	public BufferedImage getMainImage() {
		return picture.getValue().img != null ? picture.getValue().img
				: defaultImg;
	}
	
	@Override
	public void setMainImage(String path) throws IOException {
		picture.getValue().loadImage(path);
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
	public void Save() throws Exception {
		Factory.removeRecord(this);
		Factory.addRecord(this);
		this.setNeedsSave(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void importItem(String itemXML) throws Exception {
		allItems = new ArrayList<DataItem<? extends DataItemValue>>();
		String[] dataItems = itemXML.split("" + '\n');
		for (String item : dataItems) {
			Class valueClass = Class.forName(RecordFactory.getFirstTag(item)
					.substring(6));
			DataItemValue value = (DataItemValue) valueClass.newInstance();
			String innerData = RecordFactory.getTagValue(item);
			value.ImportXMLData(innerData.substring(innerData.indexOf('\1') + 1));
			String label = innerData.substring(0, innerData.indexOf('\1'));
			DataItem<?> dataItem = new DataItem<DataItemValue>(label, value);
			allItems.add(dataItem);
			if (valueClass == ContactName.class && label.equals(NAME))
				name = (DataItem<ContactName>) dataItem;
			if (valueClass == DisplayPictureEmpty.class && label.equals(PICTURE))
				picture = (DataItem<DisplayPictureEmpty>) dataItem;
			if (valueClass == PhoneNumber.class && label.equals(MOBILEPH))
				mobilePh = (DataItem<PhoneNumber>) dataItem;
			if (valueClass == Email.class && label.equals(EMAIL))
				email = (DataItem<Email>) dataItem;
		}
	}

	@Override
	public String exportData() {
		StringBuilder xml = new StringBuilder();
		xml.append(startXML(RecordFactory.RECORD + " "
				+ this.getClass().toString())
				+ getID() + '\n');
		xml.append(exportInnerData());
		xml.append(endXML(RecordFactory.RECORD));
		return xml.toString();
	}

	private String exportInnerData() {
		StringBuilder xml = new StringBuilder();
		for (DataItem<? extends DataItemValue> attr : getItems()) {
			xml.append(startXML(attr.getValue().getClass().toString()));
			xml.append(attr.getLabel());
			xml.append('\1');
			xml.append(attr.getValue().ToXML());
			xml.append(endXML(attr.getValue().getClass().toString()));
		}
		return xml.toString();
	}

	public static String startXML(String s) {
		return "<" + s + ">";
	}

	public static String endXML(String s) {
		return "</" + s + ">\n";
	}

	@Override
	public boolean needsSave() {
		return needsSave;
	}

	public void setNeedsSave(boolean needsSave) {
		this.needsSave = needsSave;
	}

	@Override
	public ContactRecord deepCopy() throws Exception {
		ContactRecord cpy = new ContactRecord(Factory);
		cpy.setID(id);
		cpy.importItem(this.exportInnerData());
		return cpy;
	}

	@Override
	public int compareTo(Record arg0) {
		return arg0.getMainLabel().compareTo(getMainLabel());
	}

	@Override
	public boolean equals(Object other){
		return other.getClass().equals(this.getClass()) && ((ContactRecord)other).getID().equals(this.getID());
	}
	
}
