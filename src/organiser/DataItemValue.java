package organiser;

import java.io.IOException;

public interface DataItemValue extends DisplayableItem {
	public void ImportXMLData(String xml) throws IOException;
	public String ToXML();
}
