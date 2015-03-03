package organiser;

import java.awt.Image;

public interface Record {
	public Iterable<DataItem<Object>> getItems();
	public String getMainLabel();
	public Image getMainImage();
	public void Save();
}
