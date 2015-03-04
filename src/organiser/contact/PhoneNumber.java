package organiser.contact;

import javax.swing.JPanel;

import organiser.DataItemValue;

public class PhoneNumber implements DataItemValue{
	public String number;
	public PhoneNumber(){
		number = "";
	}
	@Override
	public JPanel Display() {
		// TODO Auto-generated method stub
		return null;
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
