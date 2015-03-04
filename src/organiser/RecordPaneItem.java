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
		this.setBackground(new Color(24,24,24));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		text = new OJLabel(label, 20);
		text.setSize(230, 32);
		text.setLocation(70, 0);
		text.setForeground(Color.WHITE);
		subtext = new OJLabel(subLabel, 12);
		subtext.setLocation(70, 28);
		subtext.setSize(230, 24);
		subtext.setForeground(new Color(220,220,220));
		
		//Hack to use updateImage;
		bg = new JLabel(); this.add(bg);
		icon = new JLabel(); this.add(icon);
		updateImage(img);
		
		this.add(text);
		this.add(subtext);
		setComponentZOrder(subtext, 1);
		setComponentZOrder(text, 2);
	}
	
	public void updateImage(BufferedImage img){
		this.remove(bg);
		this.remove(icon);

		icon = new JLabel(new ImageIcon(ImageFilters.resizeImage(ImageFilters.cropSquare(img),56)));
		icon.setSize(56, 56);

		BufferedImage bgImage = ImageFilters.resizeImage(img, 300);
	    ImageFilters.recordDarken(bgImage);
	    ImageFilters.recordBlur(bgImage);
		
		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(300, 56);

		this.add(icon);
		this.add(bg);
	}
	
	public void updateCaption(String main, String sub){
		text.setText(main);
		subtext.setText(sub);
	}
}
