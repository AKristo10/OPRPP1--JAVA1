package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import hr.fer.oprpp1.hw08.jnotepadpp.docModel.DefaultMultipleDocumentModel;

public class StatusBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label;
	DefaultMultipleDocumentModel model;
	int length;
	int ln;
	int col;
	int sel;
	JLabel lengthLabel;
	JLabel centerLabel;
	
	
	public StatusBar(DefaultMultipleDocumentModel model) {
		this.model = model;
		createStatusBar(0, 0, 0, 0);
	}
	
	
	public void createStatusBar(int length, int ln, int col, int sel) {
		//postavi velicine
		this.length = length;
		this.ln  = ln;
		this.col = col;
		this.sel = sel;
		System.out.println(length + " " + ln + " " + col + " " + sel +" ");
		//postavi floatable i layout
		setFloatable(true);
		setLayout(new GridLayout(0,3));
		//postavi labele
		lengthLabel = new JLabel("length: " + length);
		centerLabel = new JLabel("ln: " + ln + "  col: "+ col + "  sel: "+ sel);
		add(lengthLabel, 0);
		add(centerLabel, 1);
	}	
}
