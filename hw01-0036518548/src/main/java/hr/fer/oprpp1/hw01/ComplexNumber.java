package hr.fer.oprpp1.hw01;
import java.lang.Math;
/**
 * Razred koji omogucuje korisnicima racunanje s komplekcnim brojevima. Omoguceno je zbrajanje, oduzimanje, mnozenje, dijeljenje ...
 * @author Andrea
 *
 */

public class ComplexNumber {

	private double imaginary;
	private double real;
	
	public ComplexNumber(double real, double imaginary) {
		this.imaginary = imaginary;
		this.real = real;
	}
	/**
	 * Metoda koja od realnog dijela pravi kompleksni broj koji ce, naravno, imati samo realan dio
	 * @param real
	 * @return novi komleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	/**
	 *  Metoda koja od imaginarnog dijela pravi kompleksni broj koji ce, naravno, imati samo imaginarni dio
	 * @param imaginary
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary );
	}
	/**
	 * Metoda koje uz pomoc duljine i kuta odredjuje realan i imaginarni dio kompleksnog broja i sprema ga u novu varijablu
	 * @param magnitude je duljina
	 * @param angle je kut
	 * @return novi kompleksan broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle){
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude*Math.sin(angle));
	}
	/**
	 * Metoda koja vraca realan dio Kompleksnog broja
	 * @return
	 */
	public double getReal() {
		return this.real;
	}
	/**
	 * Metoda koja vraca Imaginarni dio kompleksnog broja
	 * @return
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	/**
	 * Metoda koja vraca duljinu tako da korjenuje zbroj kvadrata imaginarnog i realnog dijela
	 * @return duljinu
	 */
	public double getMagnitude() {
		double i = Math.pow(real, 2);
		double j = Math.pow(imaginary, 2);
		return Math.sqrt(i+j);
	}
	/**
	 * Metoda koja vraca kut Kompleksnog broja
	 * @return velicina kuta
	 */
	public double getAngle() {
		double angle;
		if(real > 0) {
			angle = Math.atan(imaginary/real) ;			
		}
		else if(real == 0) {
			if(imaginary > 0) {
				angle =  Math.PI /2;
			} else {
				angle = 3 * Math.PI / 2;
			}
		} else {
			angle = Math.atan(imaginary/real) + Math.PI;
		}
		if(angle< 0) {
			if(real >0 && imaginary<0)
			return angle + 2 * Math.PI;
			else 
				return angle + 2 * Math.PI - Math.PI;
		}
		return angle;
	}
	/**
	 * Metoda koja zbraja kompleksne brojeve, na nacin da zbroji realni dio s realnim i imaginarni s imaginarnim
	 * @param c
	 * @return novi kompleksan broj kao rezultat zbrajanja
	 */
	public ComplexNumber add(ComplexNumber c) {
		ComplexNumber result = new ComplexNumber(this.real+c.real,this.imaginary+c.imaginary);
		return result;
	}
	/**
	 * Metoda koja oduzima kompleksne brojeve, na nacin da medusobono oduzme realne dijelove i imaginarne dijeloce
	 * @param c
	 * @return novi kompleksan broj kao rezultat oduzimanja
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber( this.real - c.real, this.imaginary-c.imaginary);
	}
	/**
	 * Metoda koja sluzi za mnozenje kompleksnih brojeva
	 * @param c
	 * @return Kompleksan broj kao rezultat mnozenja
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = (this.real * c.real) - (this.imaginary * c.imaginary);
		double imaginary=(this.real * c.imaginary)  + (c.real * this.imaginary);
		ComplexNumber result = new ComplexNumber(real,imaginary);
		return result;
	}
	/**
	 * Metoda koja sluzi za dijeljenje kompleksnih brojeva 
	 * @param c
	 * @return Kompleksan broj kao rezultat dijeljenja
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber konjugirani = new ComplexNumber(c.real, -1.00 * c.imaginary);
		ComplexNumber c1 = this.mul(konjugirani);
		double nazivnik = ((Math.pow(c.real, 2) + Math.pow(c.imaginary, 2)));
		ComplexNumber result = new ComplexNumber(c1.real/nazivnik, c1.imaginary/nazivnik);
		return result;
	}
	
	/**
	 * Metoda koja parsira String u kompleksan broj
	 * @throws IllegalArgumentException ako je String pogresno zadan npr. 3-+1 
	 * @param s
	 * @return
	 */
	public static ComplexNumber parse(String s) {
		String firstPart = null;
		String secondPart = null;
		int i;
		char[] polje = s.toCharArray();
		boolean found=false;
		String provjeraJeLiIuSredini = s.substring(0,s.length()-1);
		if(provjeraJeLiIuSredini.contains("i")) {
			throw new IllegalArgumentException();
		}
		for(i = polje.length-1; i>=0; i--) {
			if(polje[i] == '+' || polje[i] =='-' || i ==0) {
				if(i==0) {
					secondPart=s.substring(0,polje.length);
					secondPart = secondPart.trim();
					found=true;
				}
				else if(polje[i-1] == '+' || polje[i-1] == '-' ) {
					throw new IllegalArgumentException();
				}
				else {
					secondPart = s.substring(i, polje.length);
					secondPart = secondPart.trim();
					found=true;
				}
			}
			if(found) break;
		}
		if(i!=0) {
			for(int j = i-1 ; j>=0; j--) {
				if(polje[j] == '+' || polje[j]== '-' || j==0) {
					if(j!=0) {
						throw new IllegalArgumentException();
					}
					else {
						firstPart = s.substring(j, i);
						firstPart = firstPart.trim();
					}
				}
			}
			double im = Double.parseDouble(secondPart.substring(0, secondPart.length()-1));
			double re = Double.parseDouble(firstPart);
			return new ComplexNumber(re, im);
		}
		else {

			System.out.println(secondPart);
			if(secondPart.endsWith("i"))
				return fromImaginary(Double.parseDouble(secondPart.substring(0, secondPart.length()-1)));
			else
				return fromReal(Double.parseDouble(secondPart));
		}
	}
/**
 * Metoda koja sluzi za potenciranje kompleksnih brojeva
 * @param n
 * @return novi kompleksan broj
 */
	public ComplexNumber power(int n) {
		if(n<0)
			throw new IllegalArgumentException();

		double fi = this.getAngle();
		double z = Math.pow(this.getMagnitude(), n);
		double i = z * Math.cos(n*fi);
		double j = z* Math.sin(n*fi);

		return new ComplexNumber(i, j);
	}
/**
 * Metoda koja sluzi za korijenovanje kompleksnih brojeva
 * @param n
 * @return polje kompleksnih brojeva ovisnih o k
 */
	public ComplexNumber[] root(int n) {
		if(n<=0)
			throw new IllegalArgumentException();
		ComplexNumber[] resut = new ComplexNumber[n];
		double fi = this.getAngle();
		double root = 1./n;
		double z = Math.pow(this.getMagnitude(), root);
		for(int k = 0; k < n; k++) {
			double i = z * Math.cos(root*(fi + 2*k *Math.PI));
			double j = z * Math.sin(root*(fi + 2*k *Math.PI));
			resut[k]=new ComplexNumber(i, j);
		}

		return resut;	
	}
	/** 
	 * Pretvara Kompleksan broj u String
	 * @return formatiran kompleksan broj
	 */
	public String toString() {
		if(this.imaginary < 0 && this.real != 0)
		return new String(this.real +""+ this.imaginary + "i ");
		else if(this.imaginary >0 && this.real !=0)
			return new String(this.real + "+"+this.imaginary+"i");
		else if(this.imaginary == 0 && this.real != 0)
			return new String(this.real + "");
		else
			return new String(this.imaginary+"i");


	}

}
