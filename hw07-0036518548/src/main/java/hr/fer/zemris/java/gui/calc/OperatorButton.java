package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Buttoni koji predstvalja opratorske buttone
 * @author Andrea
 *
 */
public class OperatorButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ActionListener l;

	public OperatorButton(String text, ActionListener l) {
		super(text);
		this.l = l;
		addActionListener(l);
		setBackground(new Color(0xC1DBEA));
		setForeground(Color.BLACK);
	}

}