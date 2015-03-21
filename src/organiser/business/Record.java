package organiser.business;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface Record extends Comparable<Record>{	
	public UUID getID();	
	public void setID(UUID id);
	public List<DataItem<? extends DataItemValue>> getItems();
	public String getMainLabel();
	public String getSecondaryLabel();
	public BufferedImage getMainImage();
	public void setMainImage(String path) throws IOException;
	public void Save() throws IOException, Exception;
	public void importItem(String itemXML) throws Exception;
	public String exportData();
	public boolean needsSave();
	public void setNeedsSave(boolean needSave);
	public Record deepCopy() throws Exception;
	@Override
	public boolean equals(Object other);
}
