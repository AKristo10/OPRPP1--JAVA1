package hr.fer.zemris.math;

/**
 * Razred koji modelira polinom nad kompleksim brojevima.
 * @author Andrea
 *
 */
public class ComplexRootedPolynomial {
	
	Complex constant;
	Complex[] roots;
	
	
	/**
	 * Konstruktor koji prima konstantu i nul tocke.
	 * @param constant konstanta u polinomu
	 * @param roots nul tocke
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Metoda koja racuna vrijednost polinoma u tocki z.
	 * @param z tocka u kojoj se racuna vrijednost
	 * @return vrijednost polinoma u tocki z
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for(Complex r : roots) {
			Complex sub = z.sub(r);
			result = result.add(result.multiply(sub));
		}
		return constant.multiply(result);
	}
	
	
	/**
	 * Pretvara ovu reprezentaciju kompleksnog polinoma u reprezentaciju razreda ComplexPolynomial
	 * @return reprezentaciju razreda ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(roots[0], Complex.ONE);
		for(int i = 1; i < roots.length ; i++) {
			 ComplexPolynomial c = new ComplexPolynomial(roots[i], Complex.ONE);
			 result = result.multiply(c);
		}
		return result.multiply(new ComplexPolynomial(this.constant));
	}
	
	
	@Override
	public String toString() {
		String result = new String();
		result += "(" + constant.toString()+")";
		for(Complex r : roots) {
			result += "*(z-(" + r.toString() + "))";
		}
		return result;
	}
	
	
	/**
	 * Metoda koja vraca index kompleksnog broj koji ima udaljenost od zadanog kompleksnog broja manju od zadane udaljenosti
	 * @param z kompleksni broj
	 * @param treshold ograda
	 * @return index kompleksnog broj koji ima udaljenost od zadanog kompleksnog broja manju od zadane udaljenosti
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minDistance = Integer.MAX_VALUE;
		double distance; 
		int minIndex = 0;
		for(int i = 0; i<roots.length; i++) {
			distance = Math.sqrt(Math.pow(roots[i].getReal()-z.getReal(), 2)
					+ Math.pow(roots[i].getImaginary()- z.getImaginary(), 2));
			if(distance < minDistance) {
				minDistance = distance;
				minIndex = i;
			}
		}
		if(minDistance < treshold) {
			return minIndex;
		}
		return -1;
	}
}
