package organiser.contact;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;

import organiser.DataItemValue;
import organiser.ModernJTextField;
import organiser.UpdatePanel;

public class Email implements DataItemValue{
	public String email;
	public Email(){
		email = "";
	}
	public Email(String email){
		if(checkEmail(email)){
			this.email = email;
		}
	}
	public String getEmail(){
		return email;
	}
	public boolean setEmail(String email){
		if(checkEmail(email)){
			this.email = email;
			return true;
		}
		return false;
	}
	public boolean checkEmail(String email){
		//TODO STUFF
		return true;
	}
	@Override
	public JPanel Display() throws FontFormatException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JPanel p = new UpdatePanel(null);
		p.setSize(800, 36);
		p.setBackground(new Color(0,0,0,0));
		final JTextField emailText = new ModernJTextField(this, Email.class.getField("email"), 290);
		p.add(emailText);
		return p;
	}
	@Override
	public void ImportXMLData(String xml) {
		email = xml;
	}
	@Override
	public String ToXML() {
		return email;
	}
}
