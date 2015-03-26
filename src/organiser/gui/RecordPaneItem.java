package organiser.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import organiser.business.Record;
import organiser.helpers.ImageFilters;
import organiser.modernUIElements.OJLabel;

public class RecordPaneItem extends JPanel {
	private static final long serialVersionUID = 1922888067141577938L;
	public JLabel icon;
	public JLabel bg;
	public OJLabel text;
	public OJLabel subtext;
	BufferedImage curImg;
	public Record curRecord;
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
		subtext.setForeground(new Color(220,220,220));

		// Hack to use updateImage;
		bg = new JLabel();
		this.add(bg);
		icon = new JLabel();
		this.add(icon);
		updateImage(r.getMainImage(), false);

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
				try {
					if (!(gui.showSaveDialog() == JOptionPane.CANCEL_OPTION)) {
						gui.detailsPane.loadRecord(thisReference);
						gui.detailsPane.manageResize();
						gui.detailsPane.repaint();
						gui.selectedRecord = thisReference;
						gui.curRecordCache = gui.selectedRecord.curRecord
								.deepCopy();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void refreshPanel() {
		if (curImg != curRecord.getMainImage())
			updateImage(curRecord.getMainImage(), false);
		updateCaption(curRecord.getMainLabel(), curRecord.getSecondaryLabel());
	}

	public void updateImage(BufferedImage img, boolean selected) {
		this.remove(bg);
		this.remove(icon);

		curImg = img;
		icon = new JLabel(new ImageIcon(ImageFilters.resizeImage(
				ImageFilters.cropSquare(img), 56)));
		icon.setSize(56, 56);

		BufferedImage bgImage = ImageFilters.resizeImage(img, 300);
		ImageFilters.recordDarken(bgImage, selected);
		ImageFilters.recordBlur(bgImage);

		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(300, 56);
		
		this.add(icon);
		this.add(bg);
		repaint();
	}

	public void updateCaption(String main, String sub) {
		text.setText(main);
		subtext.setText(sub);
	}
}
