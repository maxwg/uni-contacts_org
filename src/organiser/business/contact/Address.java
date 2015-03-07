package organiser.business.contact;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;

import organiser.business.DataItemValue;
import organiser.gui.UpdatePanel;
import organiser.modernUIElements.ModernJTextField;

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
	public JPanel Display() throws FontFormatException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JPanel p = new UpdatePanel(null);
		p.setSize(800, 96);
		p.setBackground(new Color(0,0,0,0));
		final JTextField noText = new ModernJTextField(this, Address.class.getField("streetNo"), 40);
		final JTextField nameText = new ModernJTextField(this, Address.class.getField("streetName"), 200);
		final JTextField typeText = new ModernJTextField(this, Address.class.getField("streetType"), 30);
		final JTextField suburbText = new ModernJTextField(this, Address.class.getField("suburb"), 140);
		final JTextField stateText = new ModernJTextField(this, Address.class.getField("state"), 60);
		final JTextField postCodeText = new ModernJTextField(this, Address.class.getField("postcode"), 70);
		final JTextField countryText = new ModernJTextField(this, Address.class.getField("country"), 140);
		nameText.setLocation(50, 0);	
		typeText.setLocation(260, 0);
		suburbText.setLocation(0,32);
		stateText.setLocation(150, 32);
		postCodeText.setLocation(220, 32);
		countryText.setLocation(150, 64);
		p.add(noText);
		p.add(nameText);
		p.add(typeText);
		p.add(suburbText);
		p.add(stateText);
		p.add(postCodeText);
		p.add(countryText);
		return p;
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
