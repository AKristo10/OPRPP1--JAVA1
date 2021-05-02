package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Klasa koja visedretveno izvodi prikaz fraktala Newton-Raphson 
 * @author Andrea
 *
 */
public class NewtonParallel {

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
		String workers = null;
		String tracks = null;
		if(args.length == 2) {
			if(args[0].contains("w") && args[1].contains("t")) {
				workers = args[0].replaceAll("[^0-9]", "");
				tracks = args[1].replaceAll("[^0-9]", "");
			}
			else if(args[1].contains("w") && args[0].contains("t")) {
				workers = args[1].replaceAll("[^0-9]", "");
				tracks = args[0].replaceAll("[^0-9]", "");
			}
			else {
				throw new IllegalArgumentException("Ne mozete dva puta isti argument zadati");
			}
			
			
		}
		if(args.length == 1) {
			if(args[0].contains("w")) {
				workers = args[0].replaceAll("[^0-9]", "");
				tracks = String.valueOf(Runtime.getRuntime().availableProcessors() * 4);
			}
			else {
				tracks = args[0].replaceAll("[^0-9]", "");
				workers =String.valueOf(Runtime.getRuntime().availableProcessors());
			}
				
		}
		if(args.length == 0) {
			workers =String.valueOf(Runtime.getRuntime().availableProcessors());
			tracks = String.valueOf(Runtime.getRuntime().availableProcessors() * 4);
		}
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
		FractalViewer.show(new MojProducer(polynom, derived, rootedPolynom,Integer.valueOf(workers), Integer.valueOf(tracks)));

	}
	
	
	/**
	 * Privatna staticka klasa koja predstavlja tvornicu dretvi koje obavljaju odreden posao.
	 * @author Andrea
	 *
	 */
	public static class MojProducer implements IFractalProducer {

		ComplexPolynomial polynom;
		ComplexPolynomial derived;
		ComplexRootedPolynomial rootedPolynom;
		int brojTraka;
		int brojRadnika;

		
		/**
		 * Konstruktor koji prima polinom derivirani polinom i polinom prikazan kao nul tocke
		 * @param polynom faktoriziran polinom
		 * @param derived deriviran polinom
		 * @param rootedPolynom polinom prikazan kao nultocke
		 * @param brojRadnika broj dretvi koje ce izvoditi posao
		 * @param brojTraka broj traka za izvodjenje posla
		 */
		public MojProducer(ComplexPolynomial polynom, ComplexPolynomial derived, ComplexRootedPolynomial rootedPolynom,
				int brojRadnika, int brojTraka) {
			this.polynom = polynom;
			this.derived = derived;
			this.rootedPolynom = rootedPolynom;
			this.brojRadnika = brojRadnika;
			this.brojTraka = brojTraka;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			int brojYPoTraci = height / brojTraka;

			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[brojRadnika];
			for (int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if (p == PosaoIzracuna.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for (int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == brojTraka - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel, polynom, derived, rootedPolynom);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Broj dretvi je " + radnici.length + ", a broj traka je " + brojTraka);
			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
	}

	/**
	 * Razred koji predstavlja posao dretve.
	 * 
	 * @author Andrea
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexPolynomial polynom;
		ComplexPolynomial derived;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		ComplexRootedPolynomial rootedPolynom;

		/**
		 * Konstruktor koji ne prima nijedan argument
		 */
		private PosaoIzracuna() {
		}

		/**
		 * Konstruktor koji prima vazne parametre za posao izracuna
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param cancel
		 */
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexPolynomial polynom,
				ComplexPolynomial derived, ComplexRootedPolynomial rootedPolynom) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.polynom = polynom;
			this.derived = derived;
			this.rootedPolynom = rootedPolynom;
		}

		@Override
		public void run() {
			int offset = yMin * width;
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			Complex znOld = null;
			short index;
			double module;
			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					Complex c = map_to_complex_plain(x, y);
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
		}

		/**
		 * Metoda koja obavlja mapiranje u kompleksnu ravninu
		 * @param x 
		 * @param y
		 * @return novi kompleskni broj
		 */
		public Complex map_to_complex_plain(int x, int y) {
			double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
			double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
			return new Complex(cre, cim);
		}
	}

}
