package hr.fer.zemris.math;


/**
 * Klasa koja predstavlja kompleksni polinom f(z) oblika
 * zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0, gdje su z0 do zn koeficijenti koji pišu uz odgovarajuće potencije od z
 * 
 * @author Andrea
 *
 */
public class ComplexPolynomial {

	Complex[] factors;

	/**
	 * Konstrukotr koji prima polje koeficijenata uz z
	 * @param factors faktori uz z
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	
	/**
	 * Racuna red polinoma.
	 * @return red polinoma.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	
	// computes a new polynomial this*p
	/**
	 * Metoda koja mnozi dva polinoma.
	 * @param p polinom s kojim se mnozi 
	 * @return ovaj polinom pomnozen s polinomom iz @param
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newPolFactors = new Complex[this.order() + p.order() + 1];
		
		//napuni ih nulama
		for(int i = 0; i< newPolFactors.length; i++) {
			newPolFactors[i] = Complex.ZERO;
		}
		
		for(int i = 0; i < this.factors.length ; i++) {
			for(int j = 0; j < p.factors.length; j++) {
				newPolFactors[i+j] = newPolFactors[i+j].add(factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(newPolFactors);
	}

	
	
	/**
	 * Metoda koja racuna prvu derivaciju polinoma.
	 * @return prvu derivaciju polinoma.
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[factors.length-1];
		Complex n = Complex.ONE;
		int index=0;
		for(int i = 1; i < this.factors.length;  i++) {
			result[index++] = factors[i].multiply(n);
			n = n.add(Complex.ONE);
		}
		//jel vraca novi ili sebe mijenja?
		return new ComplexPolynomial(result);
	}

	
	
	/**
	 * Racuna vrijednost polinoma u tocki z.
	 * @param z tocka u kojoj se racuna vrijednost polinoma
	 * @return vrijednost polinoma u tocki z.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		int i = 0;
		for(Complex c : factors) {
			result = result.add(z.power(i).multiply(c));
			i++;
		}
		return result;
	}

	@Override
	public String toString() {
		String result = new String();
		int n = factors.length-1;
		for(int i = factors.length - 1; i >= 0; i--) {
			if(n!= 0)
				result += "(" + factors[i].toString() + ")*z^"+n + "+";
			else 
				result += "(" + factors[i].toString() + ")";
			n--;
		}
		return result;
	}
	
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
				ComplexPolynomial cp = crp.toComplexPolynom();
				System.out.println(crp);
				System.out.println(cp);
				System.out.println(cp.apply(Complex.ONE).toString());
	}
}
