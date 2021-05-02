package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Klasa koja oznacava stanje kornjace.
 * 
 * @author Andrea
 *
 */
public class TurtleState {

	Vector2D position;
	Vector2D direction;
	double shift;
	Color color;

	TurtleState() {
	}

	/**
	 * Konstruktor koji prima poziciju, smjer, pomak i boju.
	 * 
	 * @param position
	 * @param direction
	 * @param shift
	 * @param boja
	 */
	public TurtleState(Vector2D position, Vector2D direction, double shift, Color color) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.shift = shift;
	}

	/**
	 * Metoda koja vraca trenutnu boju.
	 * @return trenutna boja.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Metoda koja vraca smjer kornjacice.
	 * @return smjer kornjace.
	 */
	public Vector2D getDirection() {
		return direction;
	}
	/**
	 * Metoda koja vraca trenutnu poziciju kornjacice.
	 * @return poziciju kornjace.
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Metoda koja vraca pomak
	 * @return pomak
	 */
	public double getShift() {
		return shift;
	}

	/**
	 * Metoda koja postavlja boju.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * Metoda koja postavlja smjer kornjace.
	 * @param direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}
	/**
	 * Metoda koja postalvlja poziciju kornjacice.
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	/**
	 * Metoda koja postavlja pomak.
	 * @param shift
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}

	/**
	 * Metoda koja vraca kopiju trenutnog stanja
	 * 
	 * @return kopija trenutnog stanja
	 */
	public TurtleState copy() {
		TurtleState result = new TurtleState(this.position.copy(), this.direction.copy(), this.shift, this.color);
		return result;
	}
}
