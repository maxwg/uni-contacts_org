package organiser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailPanel extends JPanel implements Resizable {
	private static final long serialVersionUID = 1802428203402139642L;
	public static final int RECORDSTARTPOS = 250;
	BufferedImage bgBlank;
	BufferedImage bgCache;
	private BufferedImage mainPicture;
	private JLabel mainPictureContainer;
	private OJLabel mainLabel1;
	private OJLabel mainLabel2;
	private ArrayList<Component> labels;
	private ArrayList<JPanel> fields;
	Record curRecord;
	JLabel bg;
	int curPos;

	public DetailPanel() {
		super(null);
		labels = new ArrayList<Component>();
		fields = new ArrayList<JPanel>();
		curPos = RECORDSTARTPOS;
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

		mainLabel1 = new OJLabel("", 32, OJLabel.LIGHT);
		mainLabel1.setForeground(Color.WHITE);
		mainLabel1.setLocation(240, 140);
		mainLabel1.setSize(400, 50);
		mainLabel2 = new OJLabel("", 32, OJLabel.LIGHT);
		mainLabel2.setForeground(Color.WHITE);
		mainLabel2.setLocation(240, 180);
		mainLabel2.setSize(400, 50);
		setComponentZOrder(mainLabel1, 1);
		setComponentZOrder(mainLabel2, 1);
		this.add(mainLabel1);
		this.add(mainLabel2);

		mainPictureContainer = new JLabel();
		mainPictureContainer.setSize(200, 200);
		mainPictureContainer.setLocation(24, 24);
		setComponentZOrder(mainPictureContainer, 2);
		this.add(mainPictureContainer);
	}

	public void loadRecord(Record r) throws FontFormatException, IOException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		curRecord = r;
		curPos = RECORDSTARTPOS;
		for (Component c : labels)
			this.remove(c);
		for (Component c : fields)
			this.remove(c);
		labels = new ArrayList<Component>();
		fields = new ArrayList<JPanel>();
		for (DataItem<? extends DataItemValue> item : r.getItems()) {
			System.out.println(item.getValue().ToXML());
			JPanel field = item.getValue().Display();
			if (field != null) {
				OJLabel itemLabel = new OJLabel(item.getLabel(), 24,
						OJLabel.LIGHT);
				itemLabel.setSize(200, 20);
				itemLabel.setForeground(Color.WHITE);
				itemLabel.setLocation(12, curPos);
				labels.add(itemLabel);
				this.add(itemLabel);
				setComponentZOrder(itemLabel, 1);

				field.setLocation(128, curPos);
				fields.add(field);
				this.add(field);
				curPos += field.getHeight() + 6;
			}
		}
		refreshPanel(false);
	}

	void refreshPanel(boolean save) {
		setCaption(curRecord.getMainLabel());
		if (curRecord.getMainImage() != mainPicture)
			setImage(curRecord.getMainImage());
		if (save)
			curRecord.setNeedsSave(true);
		repaint();
		manageResize();
	}

	void setCaption(String text) {
		mainLabel1.setText(text.substring(0, text.indexOf(' ')));
		mainLabel2.setText(text.substring(text.indexOf(' ') + 1));
	}

	void setImage(BufferedImage img) {
		mainPicture = img;
		mainPictureContainer.setIcon(new ImageIcon(ImageFilters.resizeImage(
				ImageFilters.cropSquare(img), 200)));
		bgCache = (ImageFilters.detailsBlur(ImageFilters
				.recordDarken(ImageFilters.resizeImage(img, 300))));
	}

	@Override
	public void manageResize() {
		this.setPreferredSize(new Dimension(this.getWidth(), curPos));
		this.remove(bg);
		BufferedImage bgImage = ImageFilters.resizeImageWithoutScale(
				mainPicture == null ? bgBlank : bgCache, this.getWidth(),
				this.getHeight() > curPos ? this.getHeight() : curPos);
		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(this.getWidth(),
				this.getHeight() > curPos ? this.getHeight() : curPos);
		this.add(bg);
	}
}
