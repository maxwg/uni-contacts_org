package organiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordPaneItem extends JPanel {
	private static final long serialVersionUID = 1922888067141577938L;
	public JLabel icon;
	public JLabel text;
	public RecordPaneItem(BufferedImage img, String label){
		super(null);
		this.setSize(300, 56);
		this.setBackground(Color.GRAY);
		
		int icoWidth = img.getWidth() < img.getHeight() ? 56 : 56 * img.getWidth()/img.getHeight();
		int icoHeight = img.getWidth() > img.getHeight() ? 56 : 56 * img.getHeight()/img.getWidth();

		icon = new JLabel(new ImageIcon(img.getScaledInstance(icoWidth, icoHeight, Image.SCALE_SMOOTH)));
		icon.setSize(56, 56);;
		text = new JLabel(label);
		text.setSize(200, 50);
		text.setLocation(64, 0);
		
		this.add(icon);
		this.add(text);
	}
}
