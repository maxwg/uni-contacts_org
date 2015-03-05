package organiser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import organiser.contact.ContactRecord;

public class TopMenu extends JPanel implements Resizable {
	public static final int HEIGHT = 42;
	private static final long serialVersionUID = 2015353956108592989L;

	JFrame frame;
	GUI gui;
	int sx, sy;

	public TopMenu(JFrame window, GUI main) {
		super(null);
		this.frame = window;
		this.gui = main;
		this.setBackground(new Color(42, 42, 42));
		manageResize();
		addDragListeners();

		JButton exit = new ModernButton("Exit", 60, HEIGHT,
				new Callable<Object>() {
					public Object call() {
						System.exit(0);
						return null;
					}
				});
		JButton save = new ModernButton("Save", 60, HEIGHT,
				new Callable<Object>() {
					public Object call() throws IOException {
						gui.saveCurrentRecord();
						gui.refreshRecordDisplay();
						return null;
					}
				});
		save.setLocation(60, 0);
		JButton add = new ModernButton("Add", 60, HEIGHT,
				new Callable<Object>() {
					public Object call() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException {
						gui.showSaveDialog();
						gui.addNewRecord();
						return null;
					}
				});
		add.setLocation(120, 0);
		JButton remove = new ModernButton("Remove", 60, HEIGHT,
				new Callable<Object>() {
					public Object call() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException {
						gui.deleteCurrentRecord();
						return null;
					}
				});
		remove.setLocation(180, 0);
		JButton undo = new ModernButton("Undo", 60, HEIGHT,
				new Callable<Object>() {
					public Object call() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FontFormatException {
						gui.undoRecordDelete();
						return null;
					}
				});
		undo.setLocation(240, 0);
		this.add(add);
		this.add(remove);
		this.add(undo);
		this.add(exit);
		this.add(save);
	}

	@Override
	public void manageResize() {
		this.setSize(1200, HEIGHT);
	}

	private void addDragListeners() {
		this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				sx = e.getX();
				sy = e.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				frame.setLocation(arg0.getX() - sx + frame.getX(), arg0.getY()
						- sy + frame.getY());
			}
		});
	}
}