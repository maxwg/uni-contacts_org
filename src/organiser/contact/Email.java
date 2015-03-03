package organiser.contact;

import javax.swing.JPanel;

import organiser.DisplayableItem;

public class Email implements DisplayableItem{
	private String email;
	public Email(){
		email = "";
	}
	public Email(String email){
		checkEmail(email);
		this.email = email;
	}
	public String getEmail(){
		return email;
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
}
