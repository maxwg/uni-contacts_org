package organiser;
/**
 * Some amount of this class relies on 
 * JHLab's optimized image filters - as the default Convolve filters for blurring
 * in swing is quite poorly optimized. http://www.jhlabs.com/ip/blurring.html
 */
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import com.jhlabs.image.BoxBlurFilter;
import com.jhlabs.image.GlowFilter;

public class ImageFilters {
	private static final BoxBlurFilter RecordBlurFilter = new BoxBlurFilter(50, 50, 1);
	private static GlowFilter RecordGlowFilter;
	static {
		RecordGlowFilter = new GlowFilter();
		RecordGlowFilter.setAmount(-0.02f);
	}
	private static final BoxBlurFilter DetailsBlurFilter = new BoxBlurFilter(75, 75, 1);
	
	public static BufferedImage resizeImage(BufferedImage img, int maxSizeTo){
		int rsWidth = img.getWidth() < img.getHeight() ? maxSizeTo : maxSizeTo * img.getWidth()/img.getHeight();
		int rsHeight = img.getWidth() > img.getHeight() ? maxSizeTo : maxSizeTo * img.getHeight()/img.getWidth();
	    BufferedImage rtn = new BufferedImage(rsWidth, rsHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics g = rtn.createGraphics();
	    g.drawImage(img, 0, 0, rsWidth, rsHeight, null);
	    g.dispose();
	    return rtn;
	}
	
	public static BufferedImage resizeImageWithoutScale(BufferedImage img, int w, int h){
	    BufferedImage rtn = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics g = rtn.createGraphics();
	    g.drawImage(img, 0, 0, w, h, null);
	    g.dispose();
	    return rtn;
	}
	
	public static BufferedImage blurImage(BufferedImage image, int radius) {
		(new BoxBlurFilter(radius, radius, 1)).filter(image, image);
		return image;
	}
	
	public static BufferedImage recordBlur(BufferedImage image) {
		//More optimized - does not create a blur filter each time.
		RecordBlurFilter.filter(image, image);
		return image;
	}
	
	public static BufferedImage detailsBlur(BufferedImage image) {
		//More optimized - does not create a blur filter each time.
		DetailsBlurFilter.filter(image, image);
		return image;
	}
	
	public static BufferedImage recordDarken(BufferedImage image) {
		RecordGlowFilter.filter(image, image);
		return image;
	}
	
	public static BufferedImage cropSquare(BufferedImage image){
		return image.getSubimage(image.getWidth()>image.getHeight() ?
						(image.getWidth()- image.getHeight())/2 :
						(image.getHeight()- image.getWidth())/2, 
				0, 
				image.getWidth()>image.getHeight() ? image.getHeight() : image.getWidth(), 
				image.getWidth()>image.getHeight() ? image.getHeight() : image.getWidth());
		      
	}
	
	public static BufferedImage setTransparency(BufferedImage image, float alpha){
		BufferedImage transparentImage = new BufferedImage(image.getWidth(),
				image.getHeight(), java.awt.Transparency.TRANSLUCENT);
	    Graphics2D g = transparentImage.createGraphics();
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	    g.drawImage(image, null, 0, 0);
	    g.dispose();
	    return transparentImage;
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
