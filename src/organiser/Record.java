package organiser;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Record {
	public Iterable<DataItem<? extends DisplayableItem>> getItems();
	public String getMainLabel();
	public String getSecondaryLabel();
	public BufferedImage getMainImage();
	public void Save();
	public void importData(Iterable<DataItem<? extends DisplayableItem>> items);
}
