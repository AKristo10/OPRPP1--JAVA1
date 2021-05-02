package ispit;

/**
 * Konstruktor koji predstavlja poziciju nekog elementa unutar CalcLayouta
 * 
 * @author Andrea
 *
 */
public class RCPosition {

	double visina;
	double sirina;
	int oznaka;

	/**
	 * Konstruktor koji prima broj retka i broj stupca
	 * 
	 * @param row    broj retka
	 * @param column broj stupca
	 */
	public RCPosition(double visina, double sirina, int oznaka) {
		this.visina = visina;
		this.sirina = sirina;
		this.oznaka = oznaka;
	}

	public double getSirina() {
		return sirina;
	}
	public double getVisina() {
		return visina;
	}
	public int getOznaka() {
		return oznaka;
	}

}