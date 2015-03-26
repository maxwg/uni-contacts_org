package organiser.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import organiser.business.DataItem;
import organiser.business.DataItemValue;
import organiser.business.Record;
import organiser.business.RecordTypes;
import organiser.helpers.FileHelpers;
import organiser.helpers.ImageFilters;
import organiser.modernUIElements.ModernButton;
import organiser.modernUIElements.ModernJTextField;
import organiser.modernUIElements.OJLabel;

public class DetailPanel extends JPanel implements Resizable {
	private static final long serialVersionUID = 1802428203402139642L;
	public static final int RECORDSTARTPOS = 250;
	BufferedImage bgBlank;
	BufferedImage bgCache;
	private BufferedImage mainPicture;
	private JLabel mainPictureContainer;
	private OJLabel mainLabel1;
	private OJLabel mainLabel2;
	private ArrayList<Component> labels;
	private ArrayList<JPanel> fields;
	public Record curRecord;
	JLabel bg;
	int curPos;
	GUI gui;
	ModernButton newRecordButton;

	public DetailPanel(GUI mainWindow) {
		super(null);
		gui = mainWindow;
		labels = new ArrayList<Component>();
		fields = new ArrayList<JPanel>();
		curPos = RECORDSTARTPOS;
		try {
			bgBlank = ImageIO.read(DetailPanel.class.getResourceAsStream("/organiser/res/bg.png"));
			bg = new JLabel();
			this.add(bg);
		} catch (IOException e) {
			System.err.println("Could not load background resource!");
			bg = new JLabel();
			this.add(bg);
			bg.setBackground(new Color(24, 24, 24));
		}

		mainLabel1 = new OJLabel("", 32, OJLabel.LIGHT);
		mainLabel1.setForeground(Color.WHITE);
		mainLabel1.setLocation(240, 140);
		mainLabel1.setSize(400, 50);
		mainLabel2 = new OJLabel("", 32, OJLabel.LIGHT);
		mainLabel2.setForeground(Color.WHITE);
		mainLabel2.setLocation(240, 180);
		mainLabel2.setSize(400, 50);
		setComponentZOrder(mainLabel1, 1);
		setComponentZOrder(mainLabel2, 1);
		this.add(mainLabel1);
		this.add(mainLabel2);

		mainPictureContainer = new JLabel();
		mainPictureContainer.setSize(200, 200);
		mainPictureContainer.setLocation(24, 24);
		setComponentZOrder(mainPictureContainer, 2);
		setMainPictureClickAction();

		this.add(mainPictureContainer);
	}

	public void loadRecord(RecordPaneItem r) throws Exception {
		if (gui.selectedRecord != null)
			gui.selectedRecord.updateImage(gui.selectedRecord.curImg, false);
		gui.selectedRecord = r;
		gui.curRecordCache = r.curRecord.deepCopy();
		curRecord = r.curRecord;
		curPos = RECORDSTARTPOS;
		for (Component c : labels)
			this.remove(c);
		for (Component c : fields)
			this.remove(c);
		labels = new ArrayList<Component>();
		fields = new ArrayList<JPanel>();
		int ino=0;
		for (final DataItem<? extends DataItemValue> item : r.curRecord
				.getItems()) {
			JPanel field = item.getValue().Display();
			if (field != null) {
				OJLabel itemLabel = new OJLabel(item.getLabel(), 24,
						OJLabel.LIGHT);
				itemLabel.setSize(200, 20);
				itemLabel.setForeground(Color.WHITE);
				itemLabel.setLocation(12, curPos);
				labels.add(itemLabel);
				this.add(itemLabel);
				setComponentZOrder(itemLabel, 1);

				field.setLocation(128, curPos);
				fields.add(field);
				this.add(field);
				
				final int itemNo = ino;
				
				if (!RecordTypes.importantFields.contains(item.getLabel())) {
					ModernButton rmBtn = new ModernButton("-", 24, 24,
							new Callable<Object>() {
								@Override
								public Object call() throws Exception {
									curRecord.getItems().remove(item);
									loadRecord(gui.selectedRecord);
									refreshPanel(true);
									return null;
								}
							});
					rmBtn.setLocation(428, curPos);
					labels.add(rmBtn);
					this.add(rmBtn);
				}
				
				ModernButton upBtn = new ModernButton("^", 24, 12,
						new Callable<Object>() {
							@Override
							public Object call() throws Exception {
								curRecord.getItems().remove(item);
								curRecord.getItems().add(Math.max(0, itemNo-1), item);
								loadRecord(gui.selectedRecord);
								refreshPanel(true);
								return null;
							}
						});
				upBtn.setLocation(460, curPos);
				labels.add(upBtn);
				this.add(upBtn);
				ModernButton downBtn = new ModernButton("v", 24, 12,
						new Callable<Object>() {
							@Override
							public Object call() throws Exception {
								System.out.println(curRecord.getItems().size() + " : " + itemNo);
								curRecord.getItems().remove(item);
								curRecord.getItems().add(Math.min(curRecord.getItems().size()-1, itemNo+1), item);
								loadRecord(gui.selectedRecord);
								refreshPanel(true);
								return null;
							}
						}, true);
				downBtn.setLocation(460, curPos+12);
				labels.add(downBtn);
				this.add(downBtn);
				
				ino++;
				curPos += field.getHeight() + 6;
			}
		}
		r.updateImage(r.curImg, true);
		addNewRecordButton();
		refreshPanel(false);
	}

	void refreshPanel(boolean needsSave) {
		setCaption(curRecord.getMainLabel());
		if (curRecord.getMainImage() != mainPicture)
			setImage(curRecord.getMainImage());
		if (needsSave)
			curRecord.setNeedsSave(true);
		
		/*
		 * hack as swing is messed up - calling manageResize will set the sizes,
		 * but scrollbars will not be properly set. Resizing the window itself
		 * seems to call some event which regenerates scrollbars.
		 */
		gui.frame.setSize(gui.frame.getWidth(), gui.frame.getHeight() + 1);
		gui.frame.setSize(gui.frame.getWidth(), gui.frame.getHeight() - 1);
		repaint();
	}

	void setCaption(String text) {
		mainLabel1.setText(text.substring(0, text.indexOf(' ')));
		mainLabel2.setText(text.substring(text.indexOf(' ') + 1));
	}

	void setImage(BufferedImage img) {
		mainPicture = img;
		mainPictureContainer.setIcon(new ImageIcon(ImageFilters.resizeImage(
				ImageFilters.cropSquare(img), 200)));
		bgCache = (ImageFilters.detailsBlur(ImageFilters.recordDarken(
				ImageFilters.resizeImage(img, 300), false)));
	}

	void setMainPictureClickAction() {
		mainPictureContainer.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mainPictureContainer.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					curRecord.setMainImage(FileHelpers.resizeAndCacheImage(
							FileHelpers.showFileDialog(true), curRecord.getID()
									.toString()));
					refreshPanel(true);
				} catch (IOException e) {
					// Something broke -- debug!
					e.printStackTrace();
				}
			}
		});
	}

	private void addNewRecordButton() throws FontFormatException, IOException {
		if (newRecordButton == null) {
			newRecordButton = new ModernButton("Add New Record", 120, 28,
					new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							Class classChoice = (Class) JOptionPane
									.showInputDialog(
											null,
											"What type of field would you like to add?\n",
											"Select Field Type",
											JOptionPane.QUESTION_MESSAGE, null,
											RecordTypes.types,
											RecordTypes.types[0]);
							ModernJTextField tf = new ModernJTextField(100, 9);
							int opt = JOptionPane.CANCEL_OPTION;
							if (classChoice != null)
								opt = JOptionPane
										.showConfirmDialog(
												null,
												new Object[] {
														"What would you like to label this as? ",
														tf }, "Enter Label",
												JOptionPane.OK_CANCEL_OPTION,
												JOptionPane.PLAIN_MESSAGE);
							if (classChoice != null
									&& opt == JOptionPane.OK_OPTION) {
								DataItemValue classInst = (DataItemValue) classChoice
										.newInstance();
								curRecord.getItems().add(
										new DataItem<DataItemValue>(tf
												.getText(), classInst));
								loadRecord(gui.selectedRecord);
								refreshPanel(true);
							}
							return null;
						}
					});
			this.add(newRecordButton);
		}
		newRecordButton.setLocation(300, curPos);
		curPos += newRecordButton.getHeight() + 6;
	}

	@Override
	public void manageResize() {
		this.setPreferredSize(new Dimension(this.getWidth(), curPos));
		this.remove(bg);
		BufferedImage bgImage = ImageFilters.resizeImageWithoutScale(
				mainPicture == null ? bgBlank : bgCache, this.getWidth(),
				this.getHeight() > curPos ? this.getHeight() : curPos);
		bg = new JLabel(new ImageIcon(bgImage));
		bg.setSize(this.getWidth(),
				this.getHeight() > curPos ? this.getHeight() : curPos);
		this.add(bg);
	}
}
