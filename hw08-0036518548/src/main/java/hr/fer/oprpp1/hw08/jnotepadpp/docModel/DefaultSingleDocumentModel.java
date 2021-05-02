package hr.fer.oprpp1.hw08.jnotepadpp.docModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path path;
	private JTextArea textComponent;
	boolean changeFlag = false;
	List<SingleDocumentListener> listeners = new ArrayList<SingleDocumentListener>();

	/**
	 * Konstruktor koji prima datotecnu putanju i tekst.
	 * 
	 * @param path datotecna putanja
	 * @param text
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path;
		this.textComponent = new JTextArea(text);
		textComponent.getDocument().addDocumentListener(docListener);
	}

	/**
	 * Anonimni razred koji predstavlja DoucumentListener.
	 */
	DocumentListener docListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setModified(true);
		}
	};

	@Override
	public JTextArea getTextComponent() {
		return this.textComponent;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null)
			throw new IllegalArgumentException("Path can not be null!");
		// ako nije null promjeni path i obavijesti listenere
		this.path = path;
		listeners.stream().forEach(l -> l.documentFilePathUpdated(DefaultSingleDocumentModel.this));
	}

	@Override
	public boolean isModified() {
		return changeFlag;
	}

	@Override
	public void setModified(boolean modified) {
		this.changeFlag = modified;
		listeners.stream().forEach(l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (l != null)
			listeners.add(l);
		else
			throw new IllegalArgumentException("Listener can not be null!");
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
