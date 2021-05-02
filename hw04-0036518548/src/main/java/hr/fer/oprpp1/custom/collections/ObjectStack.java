package hr.fer.oprpp1.custom.collections;

import java.util.EmptyStackException;
/**
 * Klasa koja omogucuje spremanje objekata u kolekciju koja je implementirana kao stog
 * @author Andrea
 *
 */
public class ObjectStack<T> {

	ArrayIndexedCollection<T> elements = new ArrayIndexedCollection<T>();
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * @return <code>true</code> ako da, inace <code>false</code>
	 */
	public boolean isEmpty() {
		return elements.size() == 0;
	}
	/**
	 * Vraca trenutnu velicinu kolekcije
	 * @return velicina kolekcije
	 */
	public int size() {
		return elements.size();
	}
	/**
	 * Metoda koja dodaje element na vrh stoga
	 * @throws IllegalArgumentException ako je element <code>null</code>
	 * @param value
	 */
	public void push(T value) {
		if(value == null) {
			throw new IllegalArgumentException();
		}
		else {
			elements.add(value);
		}
	}
	/**
	 * Metoda koja skida element s vrha stoga
	 * @throws EmptyStackException ako je stog prazan
	 * @return element koji smo skinuli s vrha
	 */
	public T pop() {
		if(elements.isEmpty() == true)
			throw new EmptyStackException();
		else {
			T obj = elements.get(elements.size()-1);
			elements.remove(elements.size()-1);
			return obj;
		}
	}
	/**
	 * Metoda koja pokazuje koji element je na vrhu stoga
	 * @return element koji je na vrhu
	 */
	public T peek() {
		return elements.get(elements.size()-1);
	}
	/**
	 * Metoda koja brise stog
	 */
	public void clear() {
		elements.clear();
	}

}
