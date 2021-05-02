package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.docModel.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.*;

public class NotepadMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private FormLocalizationProvider flp;
	private MultipleDocumentModel model;
	private JMenu file;
	private JMenu edit;
	private JMenu view;
	private JMenu ispit;
	private JMenu language;
	private ActionFactory ac;
	private JFrame frame;
	private JMenu tools;
	private JMenu changeCase;
	private JMenu sort;
	
	

	public NotepadMenuBar(FormLocalizationProvider flp, MultipleDocumentModel model, JFrame frame) {
		this.flp = flp;
		this.model = model;
		this.frame = frame;
		ac = new ActionFactory(flp, model, frame);
		createNotepadMenuBar();
	}

	public void createNotepadMenuBar() {

		file = new JMenu(new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;
		});
		add(file);
		file.add(new JMenuItem(ac.getNewAction()));
		file.add(new JMenuItem(ac.getSaveAction()));
		file.add(new JMenuItem(ac.getSaveAsAction()));
		file.add(new JMenuItem(ac.getCloseTabAction()));
		file.add(new JMenuItem(ac.getExitAction()));

		edit = new JMenu(new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;
		});
		add(edit);
		edit.add(new JMenuItem(ac.getCopyAction()));
		edit.add(new JMenuItem(ac.getCopyAction()));
		edit.add(new JMenuItem(ac.getCutAction()));

		view = new JMenu(new LocalizableAction("view", flp) {
			private static final long serialVersionUID = 1L;

		});
		add(view);
		view.add(new JMenuItem(ac.getStatisticAction()));

		language = new JMenu(new LocalizableAction("language", flp) {
			private static final long serialVersionUID = 1L;
		});
		add(language);
		language.add(new JMenuItem(ac.getHrLanguage()));
		language.add(new JMenuItem(ac.getEnLanguage()));
		language.add(new JMenuItem(ac.getDeLanguage()));
		
		tools = new JMenu(new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;
		});
		add(tools);
		changeCase = new JMenu(new LocalizableAction("changeCase", flp) {
			private static final long serialVersionUID = 1L;
		});
		tools.add(changeCase);
		JMenuItem tolower = new JMenuItem(ac.getToLowerCase());
		JMenuItem toupper = new JMenuItem(ac.getToUpperCase());
		JMenuItem invert = new JMenuItem(ac.getInvertCase());
		changeCase.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
						- model.getCurrentDocument().getTextComponent().getCaret().getMark());
				if (len == 0) {
					tolower.setEnabled(false);
					toupper.setEnabled(false);
					invert.setEnabled(false);
					return;
				}
				else {
					tolower.setEnabled(true);
					toupper.setEnabled(true);
					invert.setEnabled(true);
					return;
				}
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		changeCase.add(tolower);
		changeCase.add(toupper);
		changeCase.add(invert);
		
		sort = new JMenu(new LocalizableAction("sort", flp) {
			private static final long serialVersionUID = 1L;
		});
		tools.add(sort);
		sort.add(new JMenuItem(ac.getAscendingAction()));
		
		sort.add(new JMenuItem(ac.getDescendingAction()));
		sort.add(new JMenuItem(ac.getUnique()));
		

		ispit = new JMenu(new LocalizableAction("exam", flp) {
			
		});
		add(ispit);
		ispit.add(new JMenuItem(ac.getprviZad()));
		ispit.add(new JMenuItem(ac.getDrugi()));
		
		
	}
}
