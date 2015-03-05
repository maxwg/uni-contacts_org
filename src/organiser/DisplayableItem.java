package organiser;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;

public interface DisplayableItem {
	public JPanel Display() throws FontFormatException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
}
