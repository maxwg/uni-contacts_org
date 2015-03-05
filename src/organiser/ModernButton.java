package organiser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Callable;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class ModernButton extends JButton {
	private static final long serialVersionUID = 4889596406034462097L;
	public static final Color NORMAL = new Color(24, 24, 24);
	public static final Color OVER = new Color(42, 42, 42);
	public static final Color PRESS = new Color(80, 80, 80);
	
	private Callable<?> func;

	public ModernButton(String text, int w, int h, Callable<?> func) {
		super(text);
		this.func = func;
		this.setSize(w, h);
		initialize();
	}

	public ModernButton(Icon icon, int w, int h, Callable<?> func) {
		super(icon);
		this.func = func;
		this.setSize(w, h);
		initialize();
	}

	void initialize() {
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(NORMAL);
		this.setForeground(new Color(220, 220, 220));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setUI(new BasicButtonUI() {
			public void installUI(JComponent c) {
				super.installUI(c);

				c.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						JComponent c = (JComponent) e.getComponent();
						c.setBackground(OVER);
					}

					@Override
					public void mousePressed(MouseEvent e) {
						JComponent c = (JComponent) e.getComponent();
						c.setBackground(PRESS);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						JComponent c = (JComponent) e.getComponent();
						c.setBackground(OVER);
						c.repaint();
					}

					@Override
					public void mouseExited(MouseEvent e) {
						JComponent c = (JComponent) e.getComponent();
						c.setBackground(NORMAL);
						c.repaint();
					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							func.call();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		});
	}
}
