package organiser.gui;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class UpdatePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1359183415649511440L;

	public UpdatePanel(LayoutManager l){
		super(l);
	}
	
	@Override
	public void update(Graphics g){
		super.update(g);
		//dirty hack. am not happy with this, but can't figure out how to
		//propogate events to parents....
		if (getParent().getClass() == DetailPanel.class)
			((DetailPanel)getParent()).refreshPanel(true);
	}
}
