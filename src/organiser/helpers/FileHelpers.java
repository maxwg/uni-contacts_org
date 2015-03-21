package organiser.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import organiser.business.RecordFactory;

public class FileHelpers {

	/**
	 * Opens a file dialog, and prompts the user to choose a file.
	 * @param showOnlyImagesObtain valid Java Swing image formats, and get the JFileChooser
			 to display only them
	 * @return File pointing to chosen file. null if no file is selected.
	 */
	public static File showFileDialog(boolean showOnlyImages) {
		return showFileDialog(showOnlyImages, null);
	}
	/**
	 * 
	 * @param loc - location of file to open
	 * @return
	 */
	public static File showFileDialog(boolean showOnlyImages, String loc) {
		JFileChooser chooser = new JFileChooser(loc);
		if (showOnlyImages) {
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
	
	/**
	 * Create a new cache file
	 * @param fileName - file to be cached
	 * @return the file created
	 * @throws IOException - Could not create (no permission/etc)
	 */
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
		image = ImageFilters.resizeImage(image, 290);
		File cacheFile = newCacheFile(name+".jpg");
		ImageIO.write(image, "jpg", cacheFile);
		return cacheFile.getPath();
	}
	

}
