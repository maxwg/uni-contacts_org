package organiser.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHelpers {

	public static File showFileDialog(boolean showOnlyImages) {
		JFileChooser chooser = new JFileChooser();
		if (showOnlyImages) {
			// Obtain valid Java Swing image formats, and get the JFileChooser
			// to display only them
			String[] imgExts = ImageIO.getReaderFileSuffixes();
			chooser.setAcceptAllFileFilterUsed(false);
			for (int i = 0; i < imgExts.length; i++) {
				FileFilter filter = new FileNameExtensionFilter(imgExts[i]
						+ " files", imgExts[i]);
				chooser.addChoosableFileFilter(filter);
			}
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"All Images", imgExts);
			chooser.setFileFilter(filter);
		}
		// Show dialog
		if (chooser.showDialog(null, "Select "
				+ (showOnlyImages ? "Image" : "File")) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	public static File newCacheFile(String fileName) throws IOException{
		File f = new File("data/cache/"+fileName);
		f.getParentFile().mkdirs();
		f.createNewFile();
		return f;
	}
	
	/**
	 * 
	 * @param imgFile - The file referencing the location of the image to be cached
	 * @param name - name of cache file, without extension
	 * @throws IOException
	 */
	public static String resizeAndCacheImage(File imgFile, String name) throws IOException{
		if(imgFile== null)
			return "";
		BufferedImage image = ImageIO.read(imgFile);
		image = ImageFilters.resizeImage(image, 200);
		File cacheFile = newCacheFile(name+".jpg");
		ImageIO.write(image, "jpg", cacheFile);
		return cacheFile.getPath();
	}
	

}
