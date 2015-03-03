package organiser;

import java.awt.Image;

public interface Record {
	public Iterable<DataItem<? extends DisplayableItem>> getItems();
	public String getMainLabel();
	public Image getMainImage();
	public void Save();
	public void importData(Iterable<DataItem<? extends DisplayableItem>> items);
}
