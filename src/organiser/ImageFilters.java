package organiser;
/**
 * Some amount of this class relies on 
 * JHLab's optimized image filters - as the default Convolve filters for blurring
 * in swing is quite poorly optimized. http://www.jhlabs.com/ip/blurring.html
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GlowFilter;

public class ImageFilters {
	
	public static BufferedImage resizeImage(BufferedImage img, int maxSizeTo){
		int rsWidth = img.getWidth() < img.getHeight() ? maxSizeTo : maxSizeTo * img.getWidth()/img.getHeight();
		int rsHeight = img.getWidth() > img.getHeight() ? maxSizeTo : maxSizeTo * img.getHeight()/img.getWidth();
	    BufferedImage rtn = new BufferedImage(rsWidth, rsHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics g = rtn.createGraphics();
	    g.drawImage(img, 0, 0, rsWidth, rsHeight, null);
	    g.dispose();
	    return rtn;
	}
	
	public static void blurImage(BufferedImage image, float radius) {
		(new GaussianFilter(radius)).filter(image, image);
	}
	
	public static void glowImage(BufferedImage image, float radius) {
		GlowFilter f = new GlowFilter();
		f.setAmount(radius);
		f.filter(image, image);
	}
	
	public static void darkenImage(BufferedImage image, float lightness){
	    RescaleOp op = new RescaleOp(lightness, 0, null);
	    image = op.filter(image, null);
	}
}
