package organiser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordPaneItem extends JPanel {
	private static final long serialVersionUID = 1922888067141577938L;
	public JLabel icon;
	public JLabel bg;
	public OJLabel text;
	public OJLabel subtext;
	public RecordPaneItem(BufferedImage img, String label, String subLabel){
		super(null);
		this.setSize(300, 56);
		this.setBackground(Color.lightGray);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		int icoWidth = img.getWidth() < img.getHeight() ? 56 : 56 * img.getWidth()/img.getHeight();
		int icoHeight = img.getWidth() > img.getHeight() ? 56 : 56 * img.getHeight()/img.getWidth();

		icon = new JLabel(new ImageIcon(ImageFilters.resizeImage(img, 56)));
		icon.setSize(56, 56);
		text = new OJLabel(label, 20);
		text.setSize(230, 32);
		text.setLocation(70, 0);
		text.setForeground(Color.WHITE);
		subtext = new OJLabel(subLabel, 12);
		subtext.setLocation(70, 28);
		subtext.setSize(230, 24);
		subtext.setForeground(Color.LIGHT_GRAY);
		
		BufferedImage bgImage = ImageFilters.resizeImage(img, 300);
	    ImageFilters.glowImage(bgImage, -0.008f);
	    ImageFilters.blurImage(bgImage, 80f);
		
		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(300, 56);

	    
	    
		this.add(icon);
		this.add(text);
		this.add(subtext);
		this.add(bg);
	}
}
