package test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;

public class CalcImpl implements CalcModel {

	boolean isEditable = true;
	boolean isPositive = true;
	String inputDigits = "";
	double valueInput = 0;
	String temporaryString = null;
	DoubleBinaryOperator pendingOperation;
	double activeOperand;
	boolean setActiveOperand = false;
	List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (l != null)
			listeners.add(l);
		else
			throw new NullPointerException();
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l != null)
			listeners.remove(l);
		else
			throw new NullPointerException();
	}

	@Override
	public double getValue() {
		return valueInput;
	}

	@Override
	public void setValue(double value) {
		this.valueInput = value;
		isPositive = value < 0 ? false : true;
		if (value == 0)
			inputDigits = "";
		else
			inputDigits = String.valueOf(value);
		isEditable = false;
		listeners.stream().forEach(l -> l.valueChanged(this));
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {

		inputDigits = "";
		valueInput = 0;
		isPositive = true;
		isEditable = true;
		// obavijestiti slusatelje
		listeners.stream().forEach(l -> l.valueChanged(this));
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = 0;
		pendingOperation = null;

	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable)
			throw new CalculatorInputException();
		else {
			isPositive = isPositive == true ? false : true;
			inputDigits = inputDigits.substring(1);
			inputDigits = isPositive == true ? inputDigits : "-" + inputDigits;
			listeners.stream().forEach(l -> l.valueChanged(this));
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || inputDigits.isEmpty() || inputDigits.contains("."))
			throw new CalculatorInputException();
		else {
			inputDigits += ".";
			listeners.stream().forEach(l -> l.valueChanged(this));
		}

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException();
		} else {
			if (inputDigits.isEmpty() && !isPositive)
				temporaryString = "-";
			temporaryString = inputDigits + String.valueOf(digit);
			try {
				valueInput = Double.parseDouble(temporaryString);
				inputDigits = temporaryString;
				listeners.stream().forEach(l -> l.valueChanged(this));

			} catch (NumberFormatException e) {
				throw new CalculatorInputException();
			}

		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return setActiveOperand;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (isActiveOperandSet()) {
			return activeOperand;
		} else
			throw new IllegalStateException();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		setActiveOperand = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		setActiveOperand = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;

	}

	@Override
	public String toString() {
		if (inputDigits.isEmpty()) {
			if (isPositive)
				return "0";
			else
				return "-0";
		} else
			return inputDigits;
	}
}
