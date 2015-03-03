package organiser.Contact;

import javax.swing.JPanel;

import organiser.DisplayableItem;

public class Address  implements DisplayableItem{
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
}
