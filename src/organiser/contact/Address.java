package organiser.contact;

import javax.swing.JPanel;

import organiser.DataItemValue;
import organiser.DisplayableItem;

public class Address implements DataItemValue{
	public String streetNo;
	public String streetName;
	public String streetType;
	public String suburb;
	public String state;
	public String postcode;
	public String country;
	
	public Address(){
		streetNo = "";
		streetName = "";
		streetType = "";
		suburb = "";
		state = "";
		postcode = "";
		country = "";
	}
	
	public Address(String streetNo, String streetName, String streetType, String suburb, String state, String postcode, String country){
		this.streetNo = streetNo;
		this.streetName = streetName;
		this.streetType = streetType;
		this.suburb = suburb;
		this.state = state;
		this.postcode = postcode;
		this.country = country;
	}
	
	@Override
	public JPanel Display() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ImportXMLData(String xml) {
		// TODO Auto-generated method stub
		String[] parts = xml.split(""+'\1', -1);
		streetNo = parts[0];
		streetName = parts[1];
		streetType = parts[2];
		suburb = parts[3];
		state = parts[4];
		postcode = parts[5];
		country = parts[6];
	}

	@Override
	public String ToXML() {
		return streetNo +'\1'+ streetName +'\1'+ streetType +'\1'+ suburb +'\1'+ state +'\1'+ postcode +'\1'+ country;
	}
}
