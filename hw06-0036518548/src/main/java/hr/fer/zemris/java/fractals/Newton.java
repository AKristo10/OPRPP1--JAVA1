package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * Razred koji predstavlja jednodretveni prikaz fraktala Newton-Raphson
 * @author Andrea
 *
 */
public class Newton {

	/**
	 * Metoda koja parsira String u kompleksan broj
	 * 
	 * @param s
	 * @return String parsiran u kompleksan broj
	 */
	public static Complex parse(String s) {

		String real = null;
		String imaginary = null;
		// ako je oblika a + ib ili a - ib
		if ((s.contains("+") || s.contains("-")) && (!s.startsWith("+")) && (!s.startsWith("-"))) {
			String[] realAndImaginary = null;
			if (s.contains("+")) {
				realAndImaginary = s.split("\\+");
				real = realAndImaginary[0].trim();
				imaginary = realAndImaginary[1].trim();
			} else if (s.contains("-")) {
				realAndImaginary = s.split("-");
				real = realAndImaginary[0].trim();
				imaginary = "-" + realAndImaginary[1].trim();
			}

			if (imaginary.contains("i") && imaginary.length() > 1) {
				imaginary = imaginary.substring(0, imaginary.length() - 1);
			} else if (imaginary.contains("i") && imaginary.length() == 1) {
				imaginary = "1";
			}
			return new Complex(Double.parseDouble(real), Double.parseDouble(imaginary));
		}
		// ako ima samo imaginarni dio
		else if (s.contains("i")) {
			if (s.contains("+") || s.contains("-")) {
				String predznak = String.valueOf(s.charAt(0));
				imaginary = predznak;
				if (s.length() == 2) {
					return new Complex((double) 0, Double.parseDouble(predznak + "1"));
				}
				s = s.substring(2, s.length());
				imaginary += s;
				return new Complex((double) 0, Double.parseDouble(imaginary));
			} else if (s.length() == 1) {
				return new Complex((double) 0, (double) 1);
			} else {
				if (s.contains("+") || s.contains("-")) {
					String predznak = String.valueOf(s.charAt(0));
					imaginary = predznak;
					if (s.length() == 2) {
						return new Complex((double) 0, (double) 1);
					}
					s = s.substring(2, s.length());
					imaginary += s;
				}
				return new Complex((double) 0, Double.parseDouble(s.substring(1, s.length())));
			}

		}
		// ako je samo realan dio
		else
			return new Complex(Double.parseDouble(s), (double) 0);
	}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer."
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		int i = 1;
		String line = "";
		List<Complex> roots = new ArrayList<Complex>();
		System.out.print("Root " + i + "> ");
		i++;
		while (!(line = sc.next()).equals("done")) {
			System.out.print("Root " + i + "> ");
			roots.add(parse(line));
			i++;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();
		Complex[] array = new Complex[roots.size()];
		int ind = 0;
		for (Complex c : roots) {
			array[ind++] = c;
		}
		ComplexRootedPolynomial rootedPolynom = new ComplexRootedPolynomial(Complex.ONE, array);
		System.out.println(rootedPolynom);
		ComplexPolynomial polynom = rootedPolynom.toComplexPolynom();
		System.out.println(polynom);
		ComplexPolynomial derived = polynom.derive();
		
		FractalViewer.show(new MojProducer(polynom, derived, rootedPolynom));

	}
  /**
   * Klasa koja predstavlja rad dretve.
   * @author Andrea
   *
   */
	public static class MojProducer implements IFractalProducer {
		
		ComplexPolynomial polynom ;
		ComplexPolynomial derived;
		ComplexRootedPolynomial rootedPolynom;
		/**
		 * Konstrukotr koji prima polinom derivirani polinom i polinom u obliku z - nul tocka.
		 * @param polynom faktoriziran polinom
		 * @param derived deriviran polinom
		 * @param rootedPolynom polinom u obliku z- nulTocka
		 */
		public MojProducer(ComplexPolynomial polynom, ComplexPolynomial derived, ComplexRootedPolynomial rootedPolynom ) {
			this.polynom = polynom;
			this.derived = derived;
			this.rootedPolynom = rootedPolynom;
		}
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int offset = 0;
			int m = 16 * 16 * 16;
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			Complex znOld = null;
			short index;
			short[] data = new short[width * height];
			double module;
			for (int y = 0; y < height; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					Complex c = new Complex( x / (width - 1.0) * (reMax - reMin) + reMin, 
							(height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin);
					Complex zn = c;
					int iters = 0;
					do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = derived.apply(zn);
						znOld = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znOld.sub(zn).module();
					} while (module > convergenceTreshold && iters < m);
					index = (short) rootedPolynom.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
	}

}
