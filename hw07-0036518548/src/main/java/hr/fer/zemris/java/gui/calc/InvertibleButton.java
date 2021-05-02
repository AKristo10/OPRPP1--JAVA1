package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class InvertibleButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ActionListener first;
	ActionListener second;
	ActionListener trenutniListener;
	String text1;
	String text2;
	String trenutniText;

	public InvertibleButton(String text1, String text2, ActionListener first, ActionListener second) {
		super(text1);
		this.text1 = text1;
		this.text2 = text2;
		trenutniText = text1;

		this.first = first;
		this.second = second;
		trenutniListener = first;
		addActionListener(first);

		setBackground(new Color(0xC1DBEA));
		setForeground(Color.BLACK);

	}


	public ActionListener getTrenutniListener() {
		if(trenutniListener == first) {
			return first;
		}
		else 
			return second;
	}
	
	public ActionListener getOppositeListener() {
		if(trenutniListener == second) {
			trenutniListener = first;
			return first;
		}
		else {
			trenutniListener = second;
			return second;
		}
	}
	
	public String getOppositeText() {
		if(trenutniText.equals(text1)) {
				trenutniText = text2;
			return text2;
		}
		else {
			trenutniText = text1;
			return text1;
			
		}
	}

}