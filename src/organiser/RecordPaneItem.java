package organiser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RecordPaneItem extends JPanel {
	private static final long serialVersionUID = 1922888067141577938L;
	public JLabel icon;
	public JLabel bg;
	public OJLabel text;
	public OJLabel subtext;
	BufferedImage curImg;
	Record curRecord;
	GUI gui;

	public RecordPaneItem(Record r, GUI parent) {
		super(null);
		gui = parent;
		curRecord = r;
		this.setSize(300, 56);
		this.setBackground(new Color(24, 24, 24));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		text = new OJLabel(r.getMainLabel(), 20);
		text.setSize(230, 32);
		text.setLocation(70, 0);
		text.setForeground(Color.WHITE);
		subtext = new OJLabel(r.getSecondaryLabel(), 12);
		subtext.setLocation(70, 28);
		subtext.setSize(230, 24);
		subtext.setForeground(new Color(220, 220, 220));

		// Hack to use updateImage;
		bg = new JLabel();
		this.add(bg);
		icon = new JLabel();
		this.add(icon);
		updateImage(r.getMainImage());

		this.add(text);
		this.add(subtext);
		setComponentZOrder(subtext, 1);
		setComponentZOrder(text, 2);
		final RecordPaneItem thisReference = this;
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int option = JOptionPane.YES_OPTION;
				if (gui.currentRecordNeedsSave()) {
					option = JOptionPane.showConfirmDialog(thisReference,
							"MATE YOU HAVENT SAVED. SAVE NOW?");
					if (option == JOptionPane.CANCEL_OPTION)
						return;
				}
				if (option == JOptionPane.YES_OPTION) {
					try {
						gui.saveCurrentRecord();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					gui.refreshRecordDisplay();
				}
				else if (option == JOptionPane.NO_OPTION){
					try {
						gui.selectedRecord.curRecord = gui.curRecordCache.deepCopy();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					gui.detailsPane.loadRecord(curRecord);
					gui.detailsPane.manageResize();
					gui.detailsPane.repaint();
				} catch (NoSuchFieldException | SecurityException
						| IllegalArgumentException | IllegalAccessException
						| FontFormatException | IOException e) {
					e.printStackTrace();
				}
				gui.selectedRecord = thisReference;
				try {
					gui.curRecordCache = gui.selectedRecord.curRecord.deepCopy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void refreshPanel() {
		if (curImg != curRecord.getMainImage())
			updateImage(curRecord.getMainImage());
		updateCaption(curRecord.getMainLabel(), curRecord.getSecondaryLabel());
	}

	public void updateImage(BufferedImage img) {
		this.remove(bg);
		this.remove(icon);

		curImg = img;
		icon = new JLabel(new ImageIcon(ImageFilters.resizeImage(
				ImageFilters.cropSquare(img), 56)));
		icon.setSize(56, 56);

		BufferedImage bgImage = ImageFilters.resizeImage(img, 300);
		ImageFilters.recordDarken(bgImage);
		ImageFilters.recordBlur(bgImage);

		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(300, 56);

		this.add(icon);
		this.add(bg);
	}

	public void updateCaption(String main, String sub) {
		text.setText(main);
		subtext.setText(sub);
	}
}
