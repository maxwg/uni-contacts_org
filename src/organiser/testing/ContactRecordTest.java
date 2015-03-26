package organiser.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import organiser.business.contact.Address;
import organiser.business.contact.ContactName;
import organiser.business.contact.ContactRecord;
import organiser.business.contact.Email;
import organiser.business.contact.PhoneNumber;
import organiser.modernUIElements.ModernJTextField;

public class ContactRecordTest {
	Random rng;

	@Before
	public void setUp() throws Exception {
		rng = new Random();
	}

	@Test
	public void testImportExport() {
		final int noTests = 50;
		for (int i = 0; i < noTests; i++) {
			ContactRecord r1 = new ContactRecord(null);
			ContactRecord r2 = new ContactRecord(null);
			r1.email.setValue(new Email(ranString(20)));
			r1.name.setValue(new ContactName(ranString(15), ranString(15)));
			r1.mobilePh.getValue().number = ranString(10);

			try {
				String data = r1.exportData();
				data = data.substring(data.indexOf('\n') + 1,
						data.lastIndexOf('\n' + "", data.length() - 2));
				r2.importItem(data);
				assertTrue(r2.email.getValue().email
						.equals(r1.email.getValue().email));
				assertTrue(r2.name.getValue().given
						.equals(r1.name.getValue().given));
				assertTrue(r2.name.getValue().surname
						.equals(r1.name.getValue().surname));
				assertTrue(r2.mobilePh.getValue().number.equals(r1.mobilePh
						.getValue().number));
			} catch (Exception e) {
				e.printStackTrace();
				fail("Importing record failed!");
			}
		}
	}

	/**
	 * Generate random string
	 * 
	 * @param length
	 *            - length of string (up to, may be less than)
	 * @return string, without illegal characters as specified by static
	 */
	private String ranString(int length) {
		String rtn = "";
		for (; length > 0; length--) {
			rtn += (char) (rng.nextInt(254) + 1);
		}
		for (char c : ModernJTextField.illegalCharacters) {
			rtn = rtn.replace(c + "", "");
		}
		return rtn;
	}
}
