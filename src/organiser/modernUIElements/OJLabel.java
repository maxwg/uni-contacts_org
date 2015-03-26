package organiser.modernUIElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class OJLabel extends JLabel {
	private static final long serialVersionUID = -4406198632582381978L;
	static Font regular;
	static Font light;
	static Font bold;
	public static final int REG = 0;
	public static final int LIGHT = 1;
	public static final int BOLD = 2;

	static {
		try {
			regular = Font
					.createFont(
							Font.TRUETYPE_FONT,
							OJLabel.class
									.getResourceAsStream("/organiser/res/fonts/OpenSans-Regular.ttf"));
			light = Font
					.createFont(
							Font.TRUETYPE_FONT,
							OJLabel.class
									.getResourceAsStream("/organiser/res/fonts/OpenSans-Light.ttf"));
			bold = Font
					.createFont(
							Font.TRUETYPE_FONT,
							OJLabel.class
									.getResourceAsStream("/organiser/res/fonts/OpenSans-Bold.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			ge.registerFont(regular);
			ge.registerFont(light);
			ge.registerFont(bold);
		} catch (IOException e) {
			System.err.println("Unable to load vital resources! "
					+ e.getMessage());
			System.exit(-1);
		} catch (FontFormatException e) {
			System.err.println("Unable to load fonts - may be corrupt! "
					+ e.getMessage());
		}
	}

	public OJLabel(String s, float size) {
		super(s);
		this.setFont(regular.deriveFont(size));
	}

	public OJLabel(String s, float size, int mode) {
		super(s);
		if (mode == LIGHT) {
			this.setFont(light.deriveFont(size));
		} else if (mode == BOLD) {
			this.setFont(bold.deriveFont(size));
		} else {
			this.setFont(regular.deriveFont(size));
		}
	}
}
