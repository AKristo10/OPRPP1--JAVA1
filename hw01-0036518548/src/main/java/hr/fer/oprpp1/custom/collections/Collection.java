package hr.fer.oprpp1.custom.collections;
/**
 * Razred koji omogucuje korisnicima spremanje podataka u kolekcije
 * Omoguceno je dodavanje, brisanje i racunanje velicine i slicno
 * @author Andrea
 *
 */
public class Collection {

	protected Collection() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * @return <code> true </code> ako je kolekcija prazna
	 */
	public boolean isEmpty() {
		return size() < 0;
	}
	/**
	 * Metoda koja vraca velicinu kolecije
	 * @return velicina kolekcije
	 */
	public int size() {
		return 0;
	}
	/**
	 * Omogucuje spremanje u kolekciju
	 * @param value
	 */
	public void add(Object value) {

	}
	/**
	 * Provjerava sadrzi li kolekcija zadani objekt
	 * @param value
	 * @return <code> true</code> ako sadrzi
	 */
	public boolean contains(Object value) {
		return false;
	}
	/**
	 * Uklanja odredeni objekt
	 * @param objekt koji treba ukloniti
	 * @return
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * Metoda koja pretvara kolekciju u polje objekata
	 * @return
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Metoda koja bi treba iterirati ali ovdje ne radi nista
	 * @param processor
	 */
	void forEach(Processor processor) {

	}
	/**
	 * Metoda koja dodaje cijelu drugu kolekciju
	 * @param other
	 */
	void addAll(Collection other) {
		class LocalProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);	
			}	
		}
		other.forEach(new LocalProcessor());
	}
	/**
	 * Metoda koja brise cijelu kolekciju
	 */
	void clear() {

	}
	
}
