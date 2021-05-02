package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.*;
import hr.fer.oprpp1.hw08.jnotepadpp.docModel.*;

/**
 * Aplikacija za izradu textEditora JNotepad++
 * 
 * @author Andrea
 *
 */
public class JNotepadPP extends JFrame {

	JMenuBar jmenuBar;
	JToolBar toolBar;
	BorderLayout borderLayout;
	DefaultMultipleDocumentModel models;
	private FormLocalizationProvider flp;
	int length;
	int ln;
	int col;
	int sel;
	JLabel centerLabel;
	JLabel lengthLabel;
	JLabel timeLabel;
	Timer timer;

	private static final long serialVersionUID = 1L;

	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setSize(600, 600);
		// postavi naslov
		setTitle("JNotepad++");
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		initGUI();
		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for (int i = 0; i < models.getNumberOfDocuments(); i++) {
					
					if (models.getDocument(i).isModified()) {
						int clicked = JOptionPane.showConfirmDialog(JNotepadPP.this,
								"There is unsaved work. Do you want to save?", "Warning",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
						if (clicked == JOptionPane.CANCEL_OPTION) {
							return;
						} else if (clicked == JOptionPane.YES_OPTION) {
							Path openedFilePath = models.getCurrentDocument().getFilePath();
							if (openedFilePath == null) {
								JFileChooser jfc = new JFileChooser();
								jfc.setDialogTitle("Save document");
								if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
									JOptionPane.showMessageDialog(JNotepadPP.this, "Nista nije snimljeno.", "Upozorenje",
											JOptionPane.WARNING_MESSAGE);
									continue;
								}
								openedFilePath = jfc.getSelectedFile().toPath();
							}
							models.saveDocument(models.getCurrentDocument(), openedFilePath);
							models.closeDocument(models.getCurrentDocument());
						} else {
							dispose();
							System.exit(0);
						}
					}
					else
						continue;
				}
				System.out.println("tu sam");
				dispose();
				System.exit(0);
			}
			
		};
		this.addWindowListener(wl);
	}

	public void initGUI() {
		borderLayout = new BorderLayout();
		setLayout(borderLayout);
		
		this.lengthLabel = new JLabel("Length: 0 ");
		this.centerLabel = new JLabel("Ln: 0 Col:0 Sel:0");
		this.timeLabel = new JLabel();
		
		models = new DefaultMultipleDocumentModel();

		models.addMultipleDocumentListener(getListenerForModel());
		models.createNewDocument();
	

		this.getContentPane().add(models, BorderLayout.CENTER);
		// postavi menije
		this.setJMenuBar(new NotepadMenuBar(flp, models, this));
		// posatvi toolbare
		this.getContentPane().add(new NotepadToolBar(flp, models, this), BorderLayout.PAGE_START);
		// postavi statusnu traku
		JToolBar statusBar = new JToolBar();
		statusBar.setFloatable(true);
		statusBar.setLayout(new GridLayout(0, 3));
		
		this.timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				timeLabel.setText(formatter.format(new Date()));
			}
		});
		timer.start();

		statusBar.add(lengthLabel, 0);
		statusBar.add(centerLabel, 1);
		statusBar.add(timeLabel, 2);

		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	public MultipleDocumentListener getListenerForModel() {
		MultipleDocumentListener result = new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				Document document = model.getTextComponent().getDocument();
				Caret caret = model.getTextComponent().getCaret();
				int length = document.getLength();
				int row = document.getDefaultRootElement().getElementIndex(caret.getDot()) + 1;
				int clm = caret.getDot() - document.getDefaultRootElement().getElement(row - 1).getStartOffset()
						+ 1;
				int sel = Math.abs(caret.getDot() - caret.getMark());

				lengthLabel.setText("length: " + length);
				centerLabel.setText("Ln: " + row + "  Col: " + clm + "  Sel: " + sel);
				
				model.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						Document document = model.getTextComponent().getDocument();
						Caret caret = model.getTextComponent().getCaret();
						int length = document.getLength();
						int row = document.getDefaultRootElement().getElementIndex(caret.getDot()) + 1;
						int clm = caret.getDot() - document.getDefaultRootElement().getElement(row - 1).getStartOffset()
								+ 1;
						int sel = Math.abs(caret.getDot() - caret.getMark());

						lengthLabel.setText("length: " + length);
						centerLabel.setText("Ln: " + row + "  Col: " + clm + "  Sel: " + sel);
					}
				});
				
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				Document document = currentModel.getTextComponent().getDocument();
				Caret caret = currentModel.getTextComponent().getCaret();
				int length = document.getLength();
				int row = document.getDefaultRootElement().getElementIndex(caret.getDot()) + 1;
				int clm = caret.getDot() - document.getDefaultRootElement().getElement(row - 1).getStartOffset()
						+ 1;
				int sel = Math.abs(caret.getDot() - caret.getMark());

				lengthLabel.setText("length: " + length);
				centerLabel.setText("Ln: " + row + "  Col: " + clm + "  Sel: " + sel);
				
				currentModel.getTextComponent().addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						Document document = currentModel.getTextComponent().getDocument();
						Caret caret = currentModel.getTextComponent().getCaret();
						int length = document.getLength();
						int row = document.getDefaultRootElement().getElementIndex(caret.getDot()) + 1;
						int clm = caret.getDot() - document.getDefaultRootElement().getElement(row - 1).getStartOffset()
								+ 1;
						int sel = Math.abs(caret.getDot() - caret.getMark());

						lengthLabel.setText("length: " + length);
						centerLabel.setText("Ln: " + row + "  Col: " + clm + "  Sel: " + sel);
					}
					
				});
			}
		};
		return result;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
