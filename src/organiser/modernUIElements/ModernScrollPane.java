package organiser.modernUIElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ModernScrollPane extends JScrollPane {

	private static final long serialVersionUID = 989801111061500658L;
	public ModernScrollPane(Component c, final int trackShade, final int thumbShade) {
		super(c, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(BorderFactory.createEmptyBorder());
		final JScrollBar fancyScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 60, 0, 10000);
		fancyScrollBar.setUnitIncrement(10);
		fancyScrollBar.setBorder(BorderFactory.createEmptyBorder());
		fancyScrollBar.setPreferredSize(new Dimension(6, this.getHeight()));
		fancyScrollBar.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		fancyScrollBar.setUI(new BasicScrollBarUI() {
			@Override
			protected void installComponents() {
				switch (scrollbar.getOrientation()) {
				case JScrollBar.VERTICAL:
					incrButton = createIncreaseButton(SOUTH);
					decrButton = createDecreaseButton(NORTH);
					break;

				case JScrollBar.HORIZONTAL:
					if (scrollbar.getComponentOrientation().isLeftToRight()) {
						incrButton = createIncreaseButton(EAST);
						decrButton = createDecreaseButton(WEST);
					} else {
						incrButton = createIncreaseButton(WEST);
						decrButton = createDecreaseButton(EAST);
					}
					break;
				}
				maximumThumbSize = new Dimension(6, 32);
				minimumThumbSize = new Dimension(6, 24);
				thumbColor = new Color(thumbShade, thumbShade, thumbShade);
				thumbDarkShadowColor = new Color((int)(thumbShade/1.75), (int)(thumbShade/1.75),(int)(thumbShade/1.75));
				thumbHighlightColor = new Color((int)(thumbShade/0.875), (int)(thumbShade/0.875), (int)(thumbShade/0.875));
				thumbLightShadowColor = new Color((int)(thumbShade/0.875), (int)(thumbShade/0.875), (int)(thumbShade/0.875));
				incrGap = -16;
				decrGap = -16;
				trackColor = new Color(trackShade, trackShade, trackShade);
				// Force the children's enabled state to be updated.
				scrollbar.setEnabled(scrollbar.isEnabled());
			}
		});
		this.setVerticalScrollBar(fancyScrollBar);
	}

}
