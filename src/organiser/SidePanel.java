package organiser;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SidePanel extends JPanel implements Resizable {
	int curPos;
	ArrayList<Component> items;
	
	public SidePanel(){
		super(null);
		items = new ArrayList<Component>();
		curPos=1;
	}
	
	@Override
	public Component add(Component comp){
		items.add(comp);
		comp.setLocation(1, curPos);
		curPos+=comp.getHeight()+1;
		super.add(comp);
		manageResize();
		return comp;
	}
	
	public void manageResize(){
		this.setPreferredSize(new Dimension(300, curPos));
		for(Component comp : items){
			comp.setSize(this.getWidth()-32,comp.getHeight());
		}
	}
}
