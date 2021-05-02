package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.docModel.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.docModel.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import ispit.Demo;
import ispit.DiagramDemo;

/**
 * Razred koji proizvodi sve potrebne funkcije za Notepad
 * 
 * @author Andrea
 *
 */
public class ActionFactory {

	FormLocalizationProvider flp;
	private MultipleDocumentModel model;
	JFrame frame;
	String clipboard;

	/**
	 * Konstruktor koji prima FormLocalizationProvider, MultipleDocumentModel i
	 * Jframe notepada
	 * 
	 * @param flp
	 * @param model
	 * @param frame
	 */
	public ActionFactory(FormLocalizationProvider flp, MultipleDocumentModel model, JFrame frame) {
		this.flp = flp;
		this.model = model;
		this.frame = frame;
		this.clipboard = new String();
	}

	/**
	 * Metoda koja vraca akcija za otvaranje novog praznog dokumenta
	 * 
	 * @return akcija za otvaranje novog praznog dokumenta
	 */
	public LocalizableAction getNewAction() {
		LocalizableAction result = new LocalizableAction("new", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		result.putValue(Action.SHORT_DESCRIPTION, "Open new blanck document");
		return result;
	}

	/**
	 * Metoda koja predstavlja akciju 'spremi dokument'
	 * 
	 * @return akciju 'spremi dokument'
	 */
	public LocalizableAction getSaveAction() {

		LocalizableAction result = new LocalizableAction("save", flp) {
			private static final long serialVersionUID = 1L;
			Path openedFilePath = model.getCurrentDocument().getFilePath();

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(model.getCurrentDocument());
				SingleDocumentModel tr = model.getCurrentDocument();
				if (tr.getFilePath() == null) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(frame, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					openedFilePath = jfc.getSelectedFile().toPath();
				}

				model.saveDocument(model.getCurrentDocument(), openedFilePath);
				model.getCurrentDocument().setModified(false);
			}
		};

		// postavi mnemonike i akceleratore
		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		result.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		return result;
	}

	/**
	 * Metoda koja predstavlja akciju spremi kao
	 * 
	 * @return akciju spremi kao
	 */
	public LocalizableAction getSaveAsAction() {
		LocalizableAction result = new LocalizableAction("saveas", flp) {
			private static final long serialVersionUID = 1L;
			Path openedFilePath = model.getCurrentDocument().getFilePath();

			@Override
			public void actionPerformed(ActionEvent e) {

				if (openedFilePath == null) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(frame, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					openedFilePath = jfc.getSelectedFile().toPath();
					for (SingleDocumentModel m : model) {
						if (m.getFilePath() != null && m.getFilePath().equals(openedFilePath)) {
							JOptionPane.showMessageDialog(frame, "Vec je otvorena ta datoteka", "Warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}

				model.saveDocument(model.getCurrentDocument(), openedFilePath);
				model.getCurrentDocument().setModified(false);
			}
		};

		// postavi mnemonike i akceleratore
		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		result.putValue(Action.SHORT_DESCRIPTION, "Used to save new file to disk.");

		return result;
	}

	/**
	 * Metoda koja predstavlja akciju 'otvori postojeci dokument s racunala'.
	 * 
	 * @return akciju otvori postojeci dokument s racunala
	 */
	public LocalizableAction getOpenAction() {
		LocalizableAction result = new LocalizableAction("open", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");

				if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();

				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(frame, "Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
							"Pogreska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					model.loadDocument(filePath);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Pogreska pri ucitavanju datoteke", "Pogreska",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		result.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk. ");

		return result;
	}

	/**
	 * Metoda koja predstavlja akciju 'izadi'
	 * 
	 * @return akciju 'izadi'
	 */
	public LocalizableAction getExitAction() {
		LocalizableAction result = new LocalizableAction("exit", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < model.getNumberOfDocuments(); i++) {
					System.out.println(model.getNumberOfDocuments());
					System.out.println(i);
					if (model.getDocument(i).isModified()) {
						int clicked = JOptionPane.showConfirmDialog(frame,
								"There is unsaved work. Do you want to save?", "Warning",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
						if (clicked == JOptionPane.CANCEL_OPTION) {
							return;
						} else if (clicked == JOptionPane.YES_OPTION) {
							Path openedFilePath = model.getCurrentDocument().getFilePath();
							if (openedFilePath == null) {
								JFileChooser jfc = new JFileChooser();
								jfc.setDialogTitle("Save document");
								if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
									JOptionPane.showMessageDialog(frame, "Nista nije snimljeno.", "Upozorenje",
											JOptionPane.WARNING_MESSAGE);
									continue;
								}
								openedFilePath = jfc.getSelectedFile().toPath();
							}
							model.saveDocument(model.getCurrentDocument(), openedFilePath);
							model.closeDocument(model.getCurrentDocument());
							continue;
						} else {
							System.exit(0);
						}
					}
				}
				frame.dispose();
			}
		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		result.putValue(Action.SHORT_DESCRIPTION, "Exit application.");

		return result;
	}

	/**
	 * Metoda koja predstavlja akciju za zatvaranje taba.
	 * 
	 * @return akciju za zatvaranje taba.
	 */
	public LocalizableAction getCloseTabAction() {
		LocalizableAction result = new LocalizableAction("closetab", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().isModified()) {

					int clicked = JOptionPane.showConfirmDialog(frame, "There is unsaved work. Do you want to save?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

					if (clicked == JOptionPane.CANCEL_OPTION) {
						return;
					} else if (clicked == JOptionPane.YES_OPTION) {

						Path openedFilePath = model.getCurrentDocument().getFilePath();
						if (openedFilePath == null) {
							JFileChooser jfc = new JFileChooser();
							jfc.setDialogTitle("Save document");
							if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
								JOptionPane.showMessageDialog(frame, "Nista nije snimljeno.", "Upozorenje",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							openedFilePath = jfc.getSelectedFile().toPath();
						}
						model.saveDocument(model.getCurrentDocument(), openedFilePath);
						model.closeDocument(model.getCurrentDocument());

					} else if (clicked == JOptionPane.NO_OPTION) {
						model.closeDocument(model.getCurrentDocument());
					}
				}
				model.closeDocument(model.getCurrentDocument());
			}
		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		result.putValue(Action.SHORT_DESCRIPTION, "Close tab.");

		return result;
	}

	/**
	 * Metoda koja predstavlja akciju 'zalijepi' kod kopiranja dokumenta.
	 * 
	 * @return akciju 'zalijepi' kod kopiranja dokumenta.
	 */
	public LocalizableAction getPasteAction() {

		LocalizableAction result = new LocalizableAction("paste", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea editor = model.getCurrentDocument().getTextComponent();
				String oldText = editor.getText();
				String newText = new String();
				int end = model.getCurrentDocument().getTextComponent().getCaret().getDot();
				int start = model.getCurrentDocument().getTextComponent().getCaret().getMark();
				System.out.println(clipboard);
				System.out.println("start je " + start + ", a end je " + end);
				if (end >= oldText.length()) {
					editor.setText(oldText + clipboard);
					return;
				}
				for (int i = 0; i < oldText.length(); i++) {
					if (i < start || i > end) {
						newText += oldText.toCharArray()[i];
					} else if (i == start) {
						newText += clipboard;
					} else
						continue;
				}

				editor.setText(newText);
			}
		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		result.putValue(Action.SHORT_DESCRIPTION, "Paste");

		return result;
	}

	/**
	 * Akcija koja reze tekst i sprema ga u medjuspremnik
	 * 
	 * @return Akciju koja reze tekst i sprema ga u medjuspremnik
	 */
	public LocalizableAction getCutAction() {

		LocalizableAction result = new LocalizableAction("cut", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
						model.getCurrentDocument().getTextComponent().getCaret().getMark());
				try {
					clipboard = doc.getText(offset, len);
					doc.remove(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Z);
		result.putValue(Action.SHORT_DESCRIPTION, "Cut");

		return result;
	}

	/**
	 * MEtoda koja predstavlja akciju za kopiranje.
	 * 
	 * @return akciju za kopiranje.
	 */
	public LocalizableAction getCopyAction() {

		LocalizableAction result = new LocalizableAction("copy", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
						model.getCurrentDocument().getTextComponent().getCaret().getMark());
				try {
					clipboard = doc.getText(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		result.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		result.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		result.putValue(Action.SHORT_DESCRIPTION, "Copy");

		return result;
	}

	/**
	 * Metoda koja vraca akciju koja prikazuje poruku korisniku koja ga izvjestava
	 * koliko ima znakova u dokumentu, koliko nepraznih znakova i koliko linija.
	 * 
	 * @returnakciju koja prikazuje poruku korisniku koja ga izvjestava koliko ima
	 *               znakova u dokumentu, koliko nepraznih znakova i koliko linija.
	 */
	public LocalizableAction getStatisticAction() {
		LocalizableAction result = new LocalizableAction("statistic", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = model.getCurrentDocument().getTextComponent().getText().toString();
				int numberOfLines = text.split("\\n").length;
				int numberOfCharacters = text.toCharArray().length;
				int numberOfNonBlanck = 0;
				char[] data = text.toCharArray();
				for (int i = 0; i < data.length; i++) {
					if (data[i] != ' ' && data[i] != '\n' && data[i] != '\t' && data[i] != '\r') {
						numberOfNonBlanck++;
					}
				}
				JOptionPane.showMessageDialog(frame,
						"Your document has " + numberOfCharacters + " characters, " + numberOfNonBlanck
								+ " non-blank characters and " + numberOfLines + " lines.",
						"Informacija", JOptionPane.INFORMATION_MESSAGE);
			}
		};

		return result;
	}

	/**
	 * Metoda koja postavlja hrvatski jezik.
	 * 
	 * @return hrvatski jezik.
	 */
	public LocalizableAction getHrLanguage() {
		LocalizableAction result = new LocalizableAction("hr", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		return result;
	}

	/**
	 * Metoda koja postavlja engleski jezik.
	 * 
	 * @return engleski jezik
	 */
	public LocalizableAction getEnLanguage() {
		LocalizableAction result = new LocalizableAction("en", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		return result;
	}

	/**
	 * Metoda koja postavlja njemacki jezik.
	 * 
	 * @return
	 */
	public LocalizableAction getDeLanguage() {
		LocalizableAction result = new LocalizableAction("de", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

		return result;
	}

	/**
	 * Metoda koja vraca akciju koja sva oznacena slova pretvara u velika slova.
	 * 
	 * @return akciju koja sva oznacena slova pretvara u velika slova.
	 */
	public LocalizableAction getToUpperCase() {
		LocalizableAction result = new LocalizableAction("toupper", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());

				int offset = 0;
				if (len != 0) {
					offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
							model.getCurrentDocument().getTextComponent().getCaret().getMark());
				} else {
					len = doc.getLength();
				}
				try {
					String text = doc.getText(offset, len);
					text = text.toUpperCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};

		return result;
	}

	/**
	 * Metoda koja vraca akciju koja sva oznacena slova pretvara u mala slova.
	 * 
	 * @return akciju koja sva oznacena slova pretvara u velika slova.
	 */
	public LocalizableAction getToLowerCase() {
		LocalizableAction result = new LocalizableAction("tolower", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());

				int offset = 0;
				if (len != 0) {
					offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
							model.getCurrentDocument().getTextComponent().getCaret().getMark());
				} else {
					len = doc.getLength();
				}
				try {
					String text = doc.getText(offset, len);
					text = text.toLowerCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};
		return result;
	}

	/**
	 * Metoda koja vraca akciju koja sva mala slova pretvara u velika i obrnuto.
	 * 
	 * @return akciju koja sva mala slova pretvara u velika i obrnuto.
	 */
	public LocalizableAction getInvertCase() {
		LocalizableAction result = new LocalizableAction("invert", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());

				int offset = 0;
				if (len != 0) {
					offset = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
							model.getCurrentDocument().getTextComponent().getCaret().getMark());
				} else {
					len = doc.getLength();
				}
				try {
					String text = doc.getText(offset, len);
					text = changeCase(text);
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};

		return result;
	}

	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if (Character.isLowerCase(c)) {
				znakovi[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				znakovi[i] = Character.toLowerCase(c);
			}
		}
		return new String(znakovi);
	}
	
	/**
	 * Metoda koja postavlja uzlazno oznacene linije
	 * @return
	 */
	public LocalizableAction getAscendingAction() {
		LocalizableAction result = new LocalizableAction("asc", flp) {
			private static final long serialVersionUID = 1L;
			Collator collator = null;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document document = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int rowLast = document.getDefaultRootElement().getElementIndex(caret.getDot());
				int rowFirst = document.getDefaultRootElement().getElementIndex(caret.getMark());
				System.out.println(rowFirst + "  i row last je " + rowLast);
				Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
				collator = Collator.getInstance(locale);
				String[] lines = model.getCurrentDocument().getTextComponent().getText().split("\\n");
				List<String> col = new ArrayList<String>();
				String text = new String();
				for (int i = 0; i < lines.length; i++) {
					if (i >= rowFirst && i <= rowLast) {
						col.add(lines[i]);
					}
				}

				Collections.sort(col, CMP);
				for (String s : col) {
					text += s + "\n";
				}
				String allText = new String();

				for (int i = 0; i < lines.length; i++) {
					if (i == rowFirst) {
						allText += text;
					} else if (i >= rowFirst && i <= rowLast) {
						continue;
					} else {
						allText += lines[i] + "\n";
					}
				}

				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.setText(allText);

			}

			private Comparator<String> CMP = new Comparator<String>() {
				@Override
				public int compare(String a, String b) {

					return collator.compare(a, b);
				}
			};

		};

		return result;
	}

	/**
	 * Metoda koja postavlja silazno oznacene linije
	 * @return
	 */
	public LocalizableAction getDescendingAction() {
		LocalizableAction result = new LocalizableAction("desc", flp) {
			private static final long serialVersionUID = 1L;
			Collator collator;

			@Override
			public void actionPerformed(ActionEvent e) {
				Document document = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int rowLast = document.getDefaultRootElement().getElementIndex(caret.getDot());
				int rowFirst = document.getDefaultRootElement().getElementIndex(caret.getMark());
				System.out.println(rowFirst + "  i row last je " + rowLast);
				Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
				collator = Collator.getInstance(locale);
				String[] lines = model.getCurrentDocument().getTextComponent().getText().split("\\n");
				List<String> col = new ArrayList<String>();
				String text = new String();
				for (int i = 0; i < lines.length; i++) {
					if (i >= rowFirst && i <= rowLast) {
						col.add(lines[i]);
					}
				}

				Collections.sort(col, CMP);
				for (String s : col) {
					text += s + "\n";
				}
				String allText = new String();

				for (int i = 0; i < lines.length; i++) {
					if (i == rowFirst) {
						allText += text;
					} else if (i >= rowFirst && i <= rowLast) {
						continue;
					} else {
						allText += lines[i] + "\n";
					}
				}

				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.setText(allText);

			}

			private Comparator<String> CMP = new Comparator<String>() {
				@Override
				public int compare(String a, String b) {

					return -collator.compare(a, b);
				}
			};

		};

		return result;
	}

	/**
	 * Metoda koaj brise duplicirane linije
	 * @return
	 */
	public LocalizableAction getUnique() {
		LocalizableAction result = new LocalizableAction("unique", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] lines = model.getCurrentDocument().getTextComponent().getText().toString().split("\\n");
				HashSet<String> set = new HashSet<>();
				for (String s : lines) {
					set.add(s);
				}

				String text = new String();
				for (int i = set.size() - 1; i >= 0; i--) {
					System.out.println(i);
					text += set.toArray()[i] + "\n";
				}
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.setText(text);
			}
		};
		return result;
	}
	
	
	public LocalizableAction getprviZad() {
		LocalizableAction result = new LocalizableAction("task1", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Demo.main(null);
			}
		};
		return result;
	}

	public LocalizableAction getDrugi() {
		LocalizableAction result = new LocalizableAction("task1", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DiagramDemo.main(null);
			}
		};
		return result;
	}
}
