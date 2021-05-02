package hr.fer.oprpp1.custom.collections;

public interface List<T> extends Collection<T>{
	/**
	 * Metoda koja za zadani index vraca objekt na toj poziciji
	 * @param index
	 * @return objekt na poziciji index
	 */
	T get(int index);
	/**
	 * Metoda koja stavlja objekt(value) na zadanu poziciju(position) 
	 * @throws IllegalArgumentException ako je index manji od nula ili veci od velicine kolekcije
	 * @param value
	 * @param position
	 */
	void insert(T value, int position);
	
	/**
	 * Metoda koja vraca index na kojem se mjestu nalazi value
	 * @throws NullPointerException ako je value null
	 * @param value
	 * @return
	 */
	int indexOf(Object value);
	
	/**
	 * Metoda koja brise objekt na zadanoj poziciji index
	 * @throws IllegalArgumentException ako je index manji od nula ili veci od velicine kolekcije
	 * @param index
	 */
	void remove(int index);
}
