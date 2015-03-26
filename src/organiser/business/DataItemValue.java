package organiser.business;

import java.io.IOException;

import organiser.gui.DisplayableItem;

public interface DataItemValue extends DisplayableItem {
	public void ImportXMLData(String xml) throws IOException;
	public String ToXML();
}
