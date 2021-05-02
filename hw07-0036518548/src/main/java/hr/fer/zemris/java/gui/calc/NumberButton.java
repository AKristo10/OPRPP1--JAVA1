package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;
/**
 * Klasa koja predstvalja buttone brojeve.
 * @author Andrea
 *
 */
public  class NumberButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int number;

	public NumberButton(String text, int number) {
		super(text);
		this.number = number;
		setBackground(new Color(0xC1DBEA));
		setForeground(Color.BLACK);
	}

}
