package hr.fer.zemris.math;

import java.util.ArrayList;

import java.util.List;

/**
 * Razred koji omogucuje korisnicima racunanje s komplekcnim brojevima.
 * Omogucene su razne operacije s kompleksnim brojevima.
 * 
 * @author Andrea
 *
 */

public class Complex {

	private double imaginary;
	private double real;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Konstruktor koji prima realni i imaginarni dio i inicijalizira kompleksni
	 * broj
	 * 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary) {
		this.imaginary = imaginary;
		this.real = real;
	}

	/**
	 * Klasa koja vraca imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Klasa koja vraca realan dio kompleksnog broja.
	 * 
	 * @return realan dio kompleksnog broja.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Metoda koja racuna modul kompleksnog broja
	 * 
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}

	/**
	 * Metoda koja vraca umnozak ovog komplesnog broja s kompleksnim brojem zadanim
	 * u parametru
	 * 
	 * @param c
	 * @return umnozak ovog komplesnog broja s kompleksnim brojem zadanim u
	 *         parametru
	 */
	public Complex multiply(Complex c) {
		double real = (this.real * c.real) - (this.imaginary * c.imaginary);
		double imaginary = (this.real * c.imaginary) + (c.real * this.imaginary);
		Complex result = new Complex(real, imaginary);
		return result;
	}

	/**
	 * Metoda koja sluzi za dijeljenje kompleksnih brojeva
	 * 
	 * @param c
	 * @return Kompleksan broj kao rezultat dijeljenja
	 */
	public Complex divide(Complex c) {
		Complex konjugirani = new Complex(c.real, -1.00 * c.imaginary);
		Complex c1 = this.multiply(konjugirani);
		double nazivnik = ((Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)));
		Complex result = new Complex(c1.real / nazivnik, c1.imaginary / nazivnik);
		return result;
	}

	/**
	 * Metoda koja zbraja kompleksne brojeve, na nacin da zbroji realni dio s
	 * realnim i imaginarni s imaginarnim
	 * 
	 * @param c
	 * @return novi kompleksan broj kao rezultat zbrajanja
	 */
	public Complex add(Complex c) {
		Complex result = new Complex(this.real + c.real, this.imaginary + c.imaginary);
		return result;
	}

	/**
	 * Metoda koja oduzima kompleksne brojeve, na nacin da medusobono oduzme realne
	 * dijelove i imaginarne dijeloce
	 * 
	 * @param c
	 * @return novi kompleksan broj kao rezultat oduzimanja
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Metoda koja sluzi za potenciranje kompleksnih brojeva
	 * 
	 * @param n
	 * @return novi kompleksan broj
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException();

		double fi = this.getAngle();
		double z = Math.pow(this.getMagnitude(), n);
		double i = z * Math.cos(n * fi);
		double j = z * Math.sin(n * fi);

		return new Complex(i, j);
	}

	/**
	 * Metoda koja vraca kut Kompleksnog broja
	 * 
	 * @return velicina kuta
	 */
	public double getAngle() {
		double angle;
		if (real > 0) {
			angle = Math.atan(imaginary / real);
		} else if (real == 0) {
			if (imaginary > 0) {
				angle = Math.PI / 2;
			} else {
				angle = 3 * Math.PI / 2;
			}
		} else {
			angle = Math.atan(imaginary / real) + Math.PI;
		}
		if (angle < 0) {
			if (real > 0 && imaginary < 0)
				return angle + 2 * Math.PI;
			else
				return angle + 2 * Math.PI - Math.PI;
		}
		return angle;
	}

	/**
	 * Metoda koja vraca duljinu tako da korjenuje zbroj kvadrata imaginarnog i
	 * realnog dijela
	 * 
	 * @return duljinu
	 */
	public double getMagnitude() {
		double i = Math.pow(real, 2);
		double j = Math.pow(imaginary, 2);
		return Math.sqrt(i + j);
	}

	/**
	 * Metoda koja sluzi za korijenovanje kompleksnih brojeva
	 * 
	 * @param n
	 * @return polje kompleksnih brojeva ovisnih o k
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		List<Complex> resut = new ArrayList<Complex>();
		double fi = this.getAngle();
		double root = 1. / n;
		double z = Math.pow(this.getMagnitude(), root);
		for (int k = 0; k < n; k++) {
			double i = z * Math.cos(root * (fi + 2 * k * Math.PI));
			double j = z * Math.sin(root * (fi + 2 * k * Math.PI));
			resut.add(new Complex(i, j));
		}
		return resut;
	}

	/**
	 * Metoda koja vraca negativnu vrijednost kompleksnog broja.
	 * 
	 * @return negativnu vrijednost kompleksnog broja.
	 */
	public Complex negate() {
		return new Complex(this.real * -1, this.imaginary * -1);
	}

	/**
	 * Pretvara Kompleksan broj u String
	 * 
	 * @return formatiran kompleksan broj
	 */
	public String toString() {
		if (this.imaginary < 0 )
			return new String(this.real + "" + this.imaginary + "i ");
		else
			return new String(this.real + "+" + this.imaginary + "i");

	}
}
