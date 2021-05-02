package hr.fer.oprpp1.custom.collections;

import hr.fer.oprpp1.custom.demo.EvenIntegerTester;

/**
 * Razred koji omogucuje korisnicima spremanje podataka u kolekcije
 * Omoguceno je dodavanje, brisanje i racunanje velicine i slicno
 * @author Andrea
 *
 */
public interface Collection {

	
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * @return <code> true </code> ako je kolekcija prazna
	 */
	public default boolean isEmpty() {
		return size() < 0;
	};
	/**
	 * Metoda koja vraca velicinu kolecije
	 * @return velicina kolekcije
	 */
	public abstract int size();
	/**
	 * Omogucuje spremanje u kolekciju
	 * @param value
	 */
	public abstract void add(Object value);
	/**
	 * Provjerava sadrzi li kolekcija zadani objekt
	 * @param value
	 * @return <code> true</code> ako sadrzi
	 */
	public abstract boolean contains(Object value);
	/**
	 * Uklanja odredeni objekt
	 * @param objekt koji treba ukloniti
	 * @return
	 */
	public abstract boolean remove(Object value);
	/**
	 * Metoda koja pretvara kolekciju u polje objekata
	 * @return
	 */
	public abstract Object[] toArray();
	/**
	 * Metoda koja bi treba iterirati ali ovdje ne radi nista
	 * @param processor
	 */
	
	public default void forEach(Processor processor) {
		ElementsGetter eg = createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	};
	
	/**
	 * Metoda koja dodaje cijelu drugu kolekciju
	 * @param other
	 */
	public default void addAll(Collection other) {
		class LocalProcessor implements Processor{
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
	void clear();
	
	public abstract  ElementsGetter createElementsGetter();
	
	/**
	 * Metoda dohvaćat redom sve elemente iz predane kolekcije te
	   u trenutnu kolekciju treba na kraj dodati sve elemente koje predani tester prihvati
	 * @param col
	 * @param tester
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		for(Object obj : col.toArray()) {
			if(tester.test(obj))
				this.add(obj);
		}
	}
	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
		}
	
}
