package organiser;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class ResizeListener {
/**
 * @param O - The object whose manageResize() event is to be called
 * @param C - The object whose resizing is being tracked
 * Usage - e.g, on main frame resize, call the resize method of RecordPaneItem.
 */
	public static void AttachResizeEvent(Resizable O, JFrame f){
		final Resizable objInstance = O;
		ComponentListener resizeListener = new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				objInstance.manageResize();
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			@Override
			public void componentHidden(ComponentEvent arg0) {	}
			@Override
			public void componentShown(ComponentEvent e) {	}
		};
		f.addComponentListener(resizeListener);
	}
	/**
	 * @param O - The object whose manageResize() event is to be called
	 * @param C - The object whose resizing is being tracked
	 * Usage - e.g, on main frame resize, call the resize method of RecordPaneItem.
	 */
	public static void AttachResizeEvent(Resizable O, Component C){
		final Resizable objInstance = O;
		ComponentListener resizeListener = new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				objInstance.manageResize();
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			@Override
			public void componentHidden(ComponentEvent arg0) {	}
			@Override
			public void componentShown(ComponentEvent e) {	}
		};
		C.addComponentListener(resizeListener);
	}
}
