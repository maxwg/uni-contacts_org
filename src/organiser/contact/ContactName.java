package organiser.contact;

import javax.swing.JPanel;

import organiser.DataItemValue;
import organiser.DisplayableItem;

public class ContactName implements DataItemValue{
	public String given;
	public String surname;
	
	public ContactName(String given, String surname){
		this.given = given;
		this.surname = surname;
	}
	
	public ContactName(){
		this.given = "";
		this.surname = "";
	}
	
	@Override
	public JPanel Display() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ImportXMLData(String xml) {
		String[] parts = xml.split(""+'\1', -1);
		given = parts[0];
		surname = parts[1];
	}

	@Override
	public String ToXML() {
		return given+'\1'+surname;
	}
}
