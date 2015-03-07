package organiser.business.contact;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;

import organiser.business.DataItemValue;
import organiser.gui.UpdatePanel;
import organiser.modernUIElements.ModernJTextField;

public class PhoneNumber implements DataItemValue{
	public String number;
	public PhoneNumber(){
		number = "";
	}
	@Override
	public JPanel Display() throws FontFormatException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JPanel p = new UpdatePanel(null);
		p.setSize(800, 36);
		p.setBackground(new Color(0,0,0,0));
		final JTextField numberText = new ModernJTextField(this, PhoneNumber.class.getField("number"), 140);
		p.add(numberText);
		return p;
	}
	@Override
	public void ImportXMLData(String xml) {
		number = xml;
	}
	@Override
	public String ToXML() {
		return number;
	}
}
