package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
/**
 * Glavni program koji predstavlja kalkulator
 * @author Andrea
 *
 */
public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CalcImpl model = new CalcImpl();
	Stack<Double> calcStack = new Stack<>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

	/**
	 * Konstruktor koji inicijalizira kalkulator
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		setLocation(20, 20);
		setSize(500, 300);
		setLocationRelativeTo(null);
	}

	//listener za operatore 
	ActionListener getListener(DoubleBinaryOperator operator) {
		return e -> {
			double value = model.getValue();
			DoubleBinaryOperator pending = model.getPendingBinaryOperation();
			if (pending != null) {
				value = pending.applyAsDouble(model.getActiveOperand(), value);
			}
			model.setActiveOperand(value);
			model.setPendingBinaryOperation(operator);
			model.clear();
		};
	}

	/**
	 * Metoda koja incijalizira gui
	 */
	public void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));

		// dodaj labelu za upisivanje
		JLabel display = new JLabel(model.toString(), SwingConstants.RIGHT);
		display.setOpaque(true);
		display.setBackground(Color.YELLOW);
		display.setFont(display.getFont().deriveFont(30f));
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cp.add(display, "1,1");
		model.addCalcValueListener(m -> display.setText(model.toString()));

		// postavi brojeve
		int red;
		int stupac;
		for (int i = 0; i <= 9; i++) {
			NumberButton btn = new NumberButton(String.valueOf(i), i);
			btn.setFont(btn.getFont().deriveFont(30f));
			int br = i;
			btn.addActionListener(e -> model.insertDigit(br));
			// odredi stupac
			if (i % 3 == 0)
				stupac = 5;
			else
				stupac = 2 + i % 3;

			// odredi redak
			if (i == 0) {
				red = 5;
				stupac = 3;
			} else if (i >= 1 && i <= 3) {
				red = 4;
			} else if (i >= 4 && i <= 6) {
				red = 3;
			} else
				red = 2;

			cp.add(btn, new RCPosition(red, stupac));

		}

		// poseban button = jer se nakon njega brise stanje (pendingOperation)
		JButton eq = new JButton("=");
		eq.addActionListener(e -> {
			if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
				model.setValue(
						model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setPendingBinaryOperation(null);
			}

		});
		eq.setBackground(new Color(0xC1DBEA));
		eq.setForeground(Color.BLACK);
		
		cp.add(eq, new RCPosition(1, 6));
		OperatorButton plus = new OperatorButton("+", getListener((r, l) -> r + l));
		cp.add(plus, new RCPosition(5, 6));

		OperatorButton minus = new OperatorButton("-", getListener((r, l) -> r - l));
		cp.add(minus, new RCPosition(4, 6));

		OperatorButton multy = new OperatorButton("*",getListener((r, l) -> r * l));
		cp.add(multy, new RCPosition(3, 6));

		OperatorButton devide = new OperatorButton("/",getListener((r, l) -> r / l));
		cp.add(devide, new RCPosition(2, 6));
		OperatorButton push = new OperatorButton("push", e -> calcStack.push(model.getValue()));
		cp.add(push, new RCPosition(3, 7));
		OperatorButton pop = new OperatorButton("pop", e -> model.setValue(calcStack.pop()));
		cp.add(pop, new RCPosition(4, 7));
		OperatorButton clear = new OperatorButton("clr", e -> {
			model.clear();
		});
		cp.add(clear, new RCPosition(1, 7));
		OperatorButton res = new OperatorButton("res", e -> {
			model.clearAll();
			calcStack.clear();
		});
		cp.add(res, new RCPosition(2, 7));
		OperatorButton plusMinus = new OperatorButton("+/-", e -> model.swapSign());
		cp.add(plusMinus, new RCPosition(5, 4));
		OperatorButton reciprocni = new OperatorButton("1/x", e -> model.setValue((double) (1 / model.getValue())));
		cp.add(reciprocni, new RCPosition(2, 1));
		OperatorButton point = new OperatorButton(".", e -> model.insertDecimalPoint());
		cp.add(point, new RCPosition(5, 5));

		// postavljanje invertibilnih buttona
		List<InvertibleButton> invertibleButtons = new ArrayList<InvertibleButton>();
		

		
		InvertibleButton  sin = new InvertibleButton("sin", "arcsin", e -> model.setValue(Math.sin(model.getValue())), e -> model.setValue((double)Math.asin(model.getValue())));
		cp.add(sin, new RCPosition(2, 2));
		invertibleButtons.add(sin);
		
		InvertibleButton  cos = new InvertibleButton("cos", "arccos", e -> model.setValue(Math.cos(model.getValue())), e -> model.setValue((double)Math.acos(model.getValue())));
		cp.add(cos, new RCPosition(3, 2));
		invertibleButtons.add(cos);
		
		InvertibleButton tan = new InvertibleButton("tan", "arctan", e-> model.setValue(Math.tan(model.getValue())), e -> model.setValue(Math.atan(model.getValue())));
		cp.add(tan, new RCPosition(4, 2));
		invertibleButtons.add(tan);
		
		InvertibleButton log = new InvertibleButton("log", "10^x", e -> model.setValue(Math.log10(model.getValue())), e -> model.setValue(Math.pow(10, model.getValue())));
		cp.add(log, new RCPosition(3, 1));
		invertibleButtons.add(log);
		
		InvertibleButton xPowN = new InvertibleButton("x^n", "x^(1/n)", getListener((x, n) -> Math.pow(x, n)),  getListener((x, n) -> Math.pow(x, 1/n)));
		cp.add(xPowN, new RCPosition(5, 1));
		invertibleButtons.add(xPowN);
		
		InvertibleButton ctg = new InvertibleButton("ctg", "arcctg", e -> model.setValue(1/Math.tan(model.getValue())), e -> model.setValue((double)Math.atan(1/model.getValue())));
		cp.add(ctg, new RCPosition(5, 2));
		invertibleButtons.add(ctg);
		
		InvertibleButton ln = new InvertibleButton("ln", "e^x", e -> model.setValue(Math.log(model.getValue())), e -> model.setValue(Math.pow(Math.E, model.getValue())));
		cp.add(ln, new RCPosition(4, 1));
		invertibleButtons.add(ln);
		
		JCheckBox inverse = new JCheckBox("Inv");
		cp.add(inverse, new RCPosition(5, 7));
		inverse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(InvertibleButton b : invertibleButtons) {
					b.removeActionListener(b.getTrenutniListener());
					b.addActionListener(b.getOppositeListener());
					b.setText(b.getOppositeText());
				}
			}
		});
		
		
	}
}
