package organiser.business;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import organiser.gui.UpdatePanel;
import organiser.helpers.FileHelpers;
import organiser.helpers.ImageFilters;

public class DisplayPicture implements DataItemValue {
	public BufferedImage img;
	public String pathToImage;
	private static BufferedImage blankImg;
	static {
		try {
			blankImg = ImageIO.read(DisplayPicture.class
					.getResourceAsStream("/organiser/res/plus.png"));
		} catch (IOException e) {
			System.err.println("FAILURE TO LOAD VITAL RESOURCES!");
			System.exit(-1);
		}
	}
	JLabel imgContainer;

	public DisplayPicture() {
		img = null;
		pathToImage = "";
	}

	public DisplayPicture(String imgLoc) throws IOException {
		loadImage(imgLoc);
	}

	public void loadImage(String imgLoc) throws IOException {
		pathToImage = imgLoc;
		if (imgLoc != "")
			img = ImageIO.read(new File(imgLoc));
	}

	@Override
	public JPanel Display() {
		UpdatePanel p = new UpdatePanel(null);
		p.setBackground(new Color(0, 0, 0, 0));
		BufferedImage panelImage = ImageFilters.resizeWidthOnly(
				img != null ? img : blankImg, 290);
		imgContainer = new JLabel(new ImageIcon(panelImage));
		imgContainer.setCursor(new Cursor(Cursor.HAND_CURSOR));
		p.add(imgContainer);
		p.setSize(290, panelImage.getHeight() + 8);
		imgContainer.setSize(290, panelImage.getHeight());
		bindImgContainerClick();
		return p;
	}

	private void bindImgContainerClick() {
		imgContainer.addMouseListener(new MouseListener() {
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
					String imgString = FileHelpers.resizeAndCacheImage(
							FileHelpers.showFileDialog(true), UUID.randomUUID()
									.toString());
					if (imgString != "") {
						loadImage(imgString);
						imgContainer.setIcon(new ImageIcon(img));
						imgContainer.repaint();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void ImportXMLData(String xml) throws IOException {
		if (!xml.equals(""))
			loadImage(xml);
	}

	@Override
	public String ToXML() {
		return pathToImage;
	}

}
