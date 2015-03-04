package organiser.contact;

import javax.swing.JPanel;

import organiser.DataItemValue;

public class Email implements DataItemValue{
	private String email;
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
	public JPanel Display() {
		// TODO Auto-generated method stub
		return null;
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
