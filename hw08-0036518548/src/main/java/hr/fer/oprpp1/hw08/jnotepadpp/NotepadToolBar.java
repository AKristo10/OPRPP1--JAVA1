package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import hr.fer.oprpp1.hw08.jnotepadpp.docModel.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;

public class NotepadToolBar extends JToolBar {
	
	private ActionFactory ac;
	private JFrame frame;
	private FormLocalizationProvider flp;
	private MultipleDocumentModel model;
	
	private static final long serialVersionUID = 1L;

	public NotepadToolBar(FormLocalizationProvider flp, MultipleDocumentModel model, JFrame frame) {
		super("Alati");
		this.flp = flp;
		this.model = model;
		this.frame = frame;
		ac = new ActionFactory(flp, model, frame);
		create();
	}

	public void create() {
		setFloatable(true);
		
		this.add(new JButton(ac.getNewAction()));
		this.add(new JButton(ac.getOpenAction()));
		this.add(new JButton(ac.getSaveAsAction()));
		this.add(new JButton(ac.getSaveAction()));
		this.add(new JButton(ac.getCopyAction()));
		this.add(new JButton(ac.getCutAction()));
		this.add(new JButton(ac.getPasteAction()));
		this.add(new JButton(ac.getCloseTabAction()));
		this.add(new JButton(ac.getExitAction()));
		this.add(new JButton(ac.getStatisticAction()));
	}
	
	
	
}
