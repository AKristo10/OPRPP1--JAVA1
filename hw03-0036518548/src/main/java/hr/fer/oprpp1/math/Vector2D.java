package hr.fer.oprpp1.math;

public class Vector2D {

	private double x;
	private double y;
	/**
	 * Konstruktor koji prima vrijednosti x i y
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Metoda koja vraca vrijednost x
	 * @return x
	 */
	public double getX() {
		return x;
	}
	/**
	 * Metoda koja vraca vrijednost y
	 * @return y
	 */
	public double getY() {
		return y;
	}
	/**
	 * Metoda koja uvecava x i y za vrijednost x i y od parametra
	 * @param offset
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	/**
	 * Metoda koja vraca vektor koji je jednak zbroju sadasnjeg vektora i vektora zadanog u parametru
	 * @param offset
	 * @return novi vektor koji je jednak zbroju sadasnjeg vektora i vektora zadanog u parametru
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	};
	/**
	 * Metoda koja 'rotira' vektor za kut angle
	 * @param angle
	 */
	public void rotate(double angle) {
		double noviX = Math.cos(angle) * x - Math.sin(angle) * y;
		double noviY = Math.sin(angle) * x + Math.cos(angle) * y;
		this.x = noviX;
		this.y = noviY;

	}
	/**
	 * Metoda koja vraca Vektor rotiran za kut angle
	 * @param angle
	 * @return vektor rotiran za kut angle
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D( Math.cos(angle) * x - Math.sin(angle) * y, Math.sin(angle) * x + Math.cos(angle) * y);
	}
	/**
	 * Metoda koja mnozi vrijednosti x i y sa skalarom zadanim u parametru
	 * @param scaler
	 */
	public void scale(double scaler) {
		this.x = this.x * scaler;
		this.y = this.y * scaler;
	}
	/**
	 * Metoda koja vraca vektor koji je pomnozen skalarom zadanim u parametru
	 * @param scaler
	 * @return vektor pomnozen skalarom
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	/**
	 * Metoda koja vraca kopiju vektora
	 * @return kopija naseg vektora
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
