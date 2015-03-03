package organiser.Contact;

import javax.swing.JPanel;

import organiser.DisplayableItem;

public class ContactName implements DisplayableItem{
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
}
