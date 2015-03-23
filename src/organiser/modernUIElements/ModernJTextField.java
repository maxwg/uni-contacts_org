package organiser.modernUIElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import organiser.business.DataItemValue;

public class ModernJTextField extends JTextField {
	private static final long serialVersionUID = 4159783237856056109L;
	private static final char[] illegalCharacters = { '\1', '<', '>' };

	public ModernJTextField(final DataItemValue item, final Field field,
			int width) throws FontFormatException, IOException,
			IllegalArgumentException, IllegalAccessException {
		this(width, 1024);
		this.setText((String) field.get(item));
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					boolean charIsIllegal = false;
					for (char c : illegalCharacters) {
						if (c == arg0.getKeyChar())
							setText(getText().replace(c + "", ""));
					}
					if (!charIsIllegal)
						field.set(item, getText());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				getParent().update(getParent().getGraphics()); // dirty hack.
																// Swing is
																// terrible, no
																// event
																// bubbling.
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
	}

	public ModernJTextField(int width, final int charLim)
			throws FontFormatException, IOException {
		super();
		this.setBackground(new Color(192, 192, 192));
		this.setForeground(new Color(48, 48, 64));
		this.setSize(width, 24);
		this.setMargin(new Insets(0, 3, 0, 3));
		this.setFont(Font.createFont(Font.TRUETYPE_FONT,
				new File("src/organiser/res/fonts/OpenSans-Regular.ttf"))
				.deriveFont(15f));
		this.setBorder(BorderFactory.createEmptyBorder(1, 6, 0, 6));
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (getText().length() > charLim)
					setText(getText().substring(0, charLim));
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
