package organiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailPanel extends JPanel implements Resizable {
	private static final long serialVersionUID = 1802428203402139642L;
	BufferedImage bgBlank;
	BufferedImage bgCache;
	private BufferedImage mainPicture;
	private JLabel mainPictureContainer;
	private OJLabel mainLabel;
	JLabel bg;
	int curPos;

	public DetailPanel() {
		super(null);
		curPos = 300;
		try {
			bgBlank = ImageIO.read(new File("src/organiser/res/bg.png"));
			bg = new JLabel();
			this.add(bg);
		} catch (IOException e) {
			System.err.println("Could not load background resource!");
			bg = new JLabel();
			this.add(bg);
			bg.setBackground(new Color(24, 24, 24));
		}
		
		mainLabel = new OJLabel("", 32, OJLabel.LIGHT);
		mainLabel.setForeground(Color.WHITE);
		mainLabel.setLocation(224, 168);
		mainLabel.setSize(400, 50);
		setComponentZOrder(mainLabel, 1);
		this.add(mainLabel);
		
		mainPictureContainer = new JLabel();
		mainPictureContainer.setSize(200, 200);
		mainPictureContainer.setLocation(12, 12);
		setComponentZOrder(mainPictureContainer, 2);
		this.add(mainPictureContainer);
	}

	void setCaption(String text) {
		mainLabel.setText(text);
	}

	void setImage(BufferedImage img) {
		mainPicture = img;
		mainPictureContainer.setIcon(new ImageIcon(ImageFilters.resizeImage(ImageFilters.cropSquare(img),200)));
		bgCache = (ImageFilters.detailsBlur(ImageFilters.recordDarken(ImageFilters.resizeImage(img, 300))));
	}

	@Override
	public void manageResize() {
		this.setPreferredSize(new Dimension(this.getWidth(), curPos));
		this.remove(bg);
		BufferedImage bgImage =  ImageFilters.resizeImageWithoutScale(mainPicture == null ?
				bgBlank : bgCache, this.getWidth(), this.getHeight() > curPos ? this.getHeight() : curPos);
		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(this.getWidth(), this.getHeight() > curPos ? this.getHeight() : curPos);
		this.add(bg);
	}
}
