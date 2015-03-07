package organiser.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class DisplayPicture implements DataItemValue {
	public BufferedImage img;
	public String pathToImage;
	
	public DisplayPicture(){
		img = null;
		pathToImage="";
	}
	
	public DisplayPicture(String imgLoc) throws IOException{
		loadImage(imgLoc);
	}
	
	public void loadImage(String imgLoc) throws IOException{
		pathToImage = imgLoc;
		img = ImageIO.read(new File(imgLoc));
	}
	
	@Override
	public JPanel Display() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ImportXMLData(String xml) throws IOException {
		if(!xml.equals(""))
			loadImage(xml);
	}

	@Override
	public String ToXML() {
		return pathToImage;
	}

}
