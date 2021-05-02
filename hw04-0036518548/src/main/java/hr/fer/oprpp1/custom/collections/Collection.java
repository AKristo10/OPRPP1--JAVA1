package hr.fer.oprpp1.custom.collections;



/**
 * Razred koji omogucuje korisnicima spremanje podataka u kolekcije.
 * Omoguceno je dodavanje, brisanje i racunanje velicine i slicno
 * @author Andrea
 *
 */
public interface Collection<T> {

	
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * @return <code> true </code> ako je kolekcija prazna
	 */
	public default boolean isEmpty() {
		return size() <= 0;
	};
	/**
	 * Metoda koja vraca velicinu kolecije
	 * @return velicina kolekcije
	 */
	public abstract int size();
	/**
	 * Omogucuje spremanje u kolekciju
	 * @param obj
	 */
	public abstract void add(T obj);
	
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
	 * Metoda koja nad svakim objektom iz kolekcije obavlja funkciju process{@link Processor}
	 * @param processor
	 */
	public default void forEach(Processor<T> processor) {
		ElementsGetter<T> eg = createElementsGetter();
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	};
	
	/**
	 * Metoda koja dodaje cijelu drugu kolekciju
	 * @param other
	 */
	public default void addAll(Collection<T> other) {
		class LocalProcessor implements Processor<T>{
			@Override
			public void process(T value) {
				add(value);	
			}
	
		}
		other.forEach(new LocalProcessor());
	}
	/**
	 * Metoda koja brise cijelu kolekciju
	 */
	void clear();
	/**
	 * Metoda koja vraca ElementsGetter
	 * @return
	 */
	public abstract  ElementsGetter<T> createElementsGetter();
	
	/**
	 * Metoda dohvaćat redom sve elemente iz predane kolekcije te
	   u trenutnu kolekciju treba na kraj dodati sve elemente koje predani tester prihvati
	 * @param col
	 * @param tester
	 */
	
	default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
		ElementsGetter<T> s = createElementsGetter();
		while(s.hasNextElement()) {
			T e = s.getNextElement();
			if(tester.test(e))
				add(e);
		}
	}
	

	
	
}
