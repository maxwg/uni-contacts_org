package organiser;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ModernScrollPane extends JScrollPane {
	private static final long serialVersionUID = 989801111061500658L;
	public ModernScrollPane(Component c){
		super(c, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(BorderFactory.createEmptyBorder());
	}

}
