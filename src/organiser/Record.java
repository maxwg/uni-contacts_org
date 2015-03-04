package organiser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

public interface Record {	
	public UUID getID();	
	public void setID(UUID id);
	public Iterable<DataItem<? extends DataItemValue>> getItems();
	public String getMainLabel();
	public String getSecondaryLabel();
	public BufferedImage getMainImage();
	public void Save() throws IOException;
	public void importItem(String itemXML) throws Exception;
	public String exportData();
}
