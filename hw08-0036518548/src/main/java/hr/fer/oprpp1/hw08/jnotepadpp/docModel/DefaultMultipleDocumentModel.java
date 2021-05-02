package hr.fer.oprpp1.hw08.jnotepadpp.docModel;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String imageIcon;

	ImageIcon icon;

	ArrayList<SingleDocumentModel> models = new ArrayList<SingleDocumentModel>();

	SingleDocumentModel currentModel;
	

	ArrayList<MultipleDocumentListener> mdlisteners = new ArrayList<MultipleDocumentListener>();

	public  DefaultMultipleDocumentModel() {
		super();
		
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel m = currentModel;
				int selected = getSelectedIndex();
				currentModel = models.get(selected);
				mdlisteners.stream().forEach(l -> l.currentDocumentChanged(m, currentModel));
				
			}
		});
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return models.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		System.out.println("u create new doc sam");
		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		addListenertoCurrentModel(model);
		models.add(model);
		mdlisteners.stream().forEach(l -> l.documentAdded(model));
		SingleDocumentModel old = currentModel;
		currentModel = model;
		mdlisteners.stream().forEach(l -> l.currentDocumentChanged(old, currentModel));
		createNewTab("unnamed", models.indexOf(model), null, currentModel.getTextComponent());
		
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		SingleDocumentModel result = null;
		if (path == null) {
			throw new NullPointerException("Path ne smije biti null!");
		}
		for (SingleDocumentModel m : models) {
			if (m.getFilePath() != null  && m.getFilePath().equals(path)) {
				System.out.println("Taj dokument je vec otvoren!!");
				return changedCurrentModel(m);
			}
		}
		if (Files.isReadable(path)) {
			byte[] octets = null;
			try {
				octets = Files.readAllBytes(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String text = new String(octets, StandardCharsets.UTF_8);

			SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
			result = model;
			
			models.add(model);
			// kreiraj novi tab i prebaci se na njega
			createNewTab(path.getFileName().toString(), models.indexOf(model), path.toAbsolutePath().toString(), model.getTextComponent());
			mdlisteners.stream().forEach(l -> l.documentAdded(model));
			changedCurrentModel(model);
			currentModel.setModified(false);
			addListenertoCurrentModel(currentModel);
		} else {
			System.err.println("Datoteka se ne moze citati!!!");
		}
		return result;
	}

	private SingleDocumentModel changedCurrentModel(SingleDocumentModel m) {
		mdlisteners.stream().forEach(l -> l.currentDocumentChanged(currentModel, m));
		currentModel = m;
		setSelectedIndex(models.indexOf(currentModel));
		return m;
	}

	private void addListenertoCurrentModel(SingleDocumentModel m) {
		m.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(models.indexOf(currentModel), changeImageIcon(currentModel));
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.setTitleAt(models.indexOf(currentModel),
						model.getFilePath().toString());
			}
		});

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Path path = newPath == null ? model.getFilePath() : newPath;
		byte[] octets = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(path, octets);
		} catch (Exception e) {
			System.err.println("Greska prilikom pisanja u datoteku!");
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(models.size() == 1) {
			createNewDocument();
			removeTabAt(models.indexOf(model));
			models.remove(model);
			mdlisteners.stream().forEach(l -> l.documentRemoved(currentModel));
			return;
		}
		int index = models.indexOf(model);
		removeTabAt(models.indexOf(model));
		currentModel = models.get(index - 1);
		models.remove(model);
		mdlisteners.stream().forEach(l -> l.documentRemoved(currentModel));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		mdlisteners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		mdlisteners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return models.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return models.get(index);
	}

	private ImageIcon changeImageIcon(SingleDocumentModel m) {
		return m.isModified() ? getRedIcon() : getGreenIcon();
	}

	private void createNewTab(String name, int indexInList, String fullPath, JTextArea textArea) {
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.add(textArea);
		panel.add(scrollPane);
		panel.setVisible(true);
		
		// dodaj tab
		addTab(name, getGreenIcon(), new JScrollPane(textArea), fullPath);
		this.setSelectedIndex(models.indexOf(currentModel));

	}



	private ImageIcon getRedIcon() {
		InputStream is = this.getClass().getResourceAsStream("icons/c.jpg");
		if (is == null)
			throw new NullPointerException("Error prilikom dohvacanja slike!");
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ImageIcon getGreenIcon() {
		InputStream is = this.getClass().getResourceAsStream("icons/z.jpg");
		if (is == null)
			throw new NullPointerException("Error prilikom dohvacanja slike!");
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
