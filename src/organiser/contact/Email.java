package organiser.contact;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import organiser.DataItemValue;
import organiser.ModernButton;
import organiser.ModernJTextField;
import organiser.UpdatePanel;

public class Email implements DataItemValue {
	public String email;

	public Email() {
		email = "";
	}

	public Email(String email) {
		if (checkEmail(email)) {
			this.email = email;
		}
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(String email) {
		if (checkEmail(email)) {
			this.email = email;
			return true;
		}
		return false;
	}

	public boolean checkEmail(String email) {
		// TODO STUFF
		return true;
	}

	@Override
	public JPanel Display() throws FontFormatException, IOException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		final JPanel p = new UpdatePanel(null);
		p.setSize(800, 36);
		p.setBackground(new Color(0, 0, 0, 0));
		final JTextField emailText = new ModernJTextField(this,
				Email.class.getField("email"), 190);
		p.add(emailText);
		ModernButton sendMail = new ModernButton("Send Email", 90, 24,
				new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						try {
							Desktop.getDesktop().mail(
									new URI("mailto:" + email));
						} catch (Exception e1) {
							// Something went wrong on mail server side -
							// nothing we can do!
							// User may not have properly configured a mail
							// client.
							if (email.equals(""))
								JOptionPane
										.showMessageDialog(p,
												"Unable to send email! Enter a valid email");
							else
								JOptionPane
										.showMessageDialog(
												p,
												"Unable to send email! Make sure you have properly configured your mail client.");
						}
						return null;
					}
				});
		sendMail.setLocation(200, 0);
		p.add(sendMail);
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
