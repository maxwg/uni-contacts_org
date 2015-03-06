package organiser.contact;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;

import organiser.business.DataItemValue;
import organiser.gui.UpdatePanel;
import organiser.modernUIElements.ModernJTextField;

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
	public JPanel Display() throws FontFormatException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JPanel p = new UpdatePanel(null);
		p.setSize(800, 36);
		p.setBackground(new Color(0,0,0,0));
		final JTextField givenText = new ModernJTextField(this, ContactName.class.getField("given"), 140);
		final JTextField surText = new ModernJTextField(this, ContactName.class.getField("surname"), 140);
		surText.setLocation(150, 0);		
		p.add(givenText);
		p.add(surText);
		return p;
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
