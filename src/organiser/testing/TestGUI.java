package organiser.testing;

import java.util.List;

import organiser.gui.DetailPanel;
import organiser.gui.GUI;
import organiser.gui.RecordPaneItem;
import organiser.gui.SidePanel;
import organiser.gui.TopMenu;

public class TestGUI extends GUI {
	public TestGUI(){
		super();
	}
	public List<RecordPaneItem> getLoadedRecords(){
		return loadedRecords;
	}
	public List<RecordPaneItem> getDeletedRecords(){
		return deletedRecords;
	}
	public TopMenu getTopMenu(){
		return topMenu;
	}
	public DetailPanel getDetailPanel(){
		return detailsPane;
	}
	public SidePanel getSidePanel(){
		return contactsPane;
	}
}
