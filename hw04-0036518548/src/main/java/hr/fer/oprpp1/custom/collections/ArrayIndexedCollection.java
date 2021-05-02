package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Klasa koja omogucuje korisniku spremanje elmenata u polje objekata
 * Omoguceno je manipuliranje tim poljem, dodavanje elemenata, brisanje i slicno
 * @author Andrea
 *
 */
public class ArrayIndexedCollection<T> implements List<T>{

	 int size;
	  T[] elements;
	
	/**
	 * Privatna staticka klasa koja vraca korisniku element po element na njegov zahtjev
	 * @author Andrea
	 *
	 */
	private static class ElementsGetterClass<T> implements ElementsGetter<T>{
		ArrayIndexedCollection<T> array;
		int sljedeci = 0;
		public ElementsGetterClass(ArrayIndexedCollection<T> array) {
			this.array = array;
		}
		public boolean hasNextElement() {
			return	sljedeci < array.size();
		}
		public T getNextElement() {
			if(sljedeci == array.size) {
				throw new NoSuchElementException();
			}
			return array.elements[sljedeci++];
		}
	}
	/**
	 * Pravi instancu objekta velicine 16
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection() {
		this.elements = (T[]) new Object[16];
	}
	/**
	 *   *Pravi instancu objekta velicine initialCapacity
	 * @param initalCapacity velicina polja
	 * @throws IllegalArgumentException ako je velicina manja od 1
	 */

	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initalCapacity) {
		if (initalCapacity < 1)
			throw new IllegalArgumentException();
		else {
			this.elements = (T[]) new Object[initalCapacity];
		}
	}
	/**
	 * Konstruktor koji pravi instancu objekta tako da kopira drugu kolekciju
	 * @throws NullPointerException ako je je kolekcija <code>null</code>
	 * @param other
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<T> other) {
		if(other == null) {
			throw new NullPointerException();
		}
		if(16 < other.size()) {
			this.elements = (T[]) new Object[other.size()];
		}
		else {
			this.elements = (T[])new Object[16];
		}
		this.addAll(other);
		this.size = other.size();
	}
	
	/**
	 * Metoda koja postavlja polju odredjenu velicinu i kopira drugu kolekciju
	 * Ako je velicina polja manja od velicine kolekcije, velicina polja postaje velicina kolekcije other 
	 * @param initalCapacity
	 * @param other
	 */

	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initalCapacity, Collection<T> other) {
		if(other == null)
			throw new NullPointerException();
		if(initalCapacity < other.size()) {
			this.elements =(T[]) new Object[other.size()];
		}
		else {
			this.elements = (T[]) new Object[initalCapacity];
		}
		this.addAll(other);
		this.size = other.size();
	}
	
	/**
	 * Metoda koja dodaje zadani objekt u kolekciju
	 * @throws NullPointerException ako je objekt <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(T value) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(elements.length <= this.size) {
			T[] elements2 = (T[])new Object[elements.length * 2];
			int i =0;
			for(T o : elements) {
				elements2[i++] = o;
			}
			elements2[elements.length] = value;
			elements = elements2;
		}
		else {
			for(int i = 0; i<elements.length ;i++) {
				if( elements[i] == null) {
					elements[i] = value;
					break;
				}
			}
		}
		this.size += 1;
	}
	/**
	 * Metoda koja vraca objekt sa zadane pozicije
	 * @throws IndexOutOfBoundsException ako je index izvan granica kolekcije
	 * @param index
	 * @return
	 */
	public T get(int index) {
		if(index <= this.size-1 && index >= 0)
			return elements[index];
		else 
			throw new IndexOutOfBoundsException();
	}
	/**
	 * Metoda koja brise cijelu kolekciju
	 * 
	 */
	@Override
	public void clear() {
		for(int i = 0; i<this.size; i++) {
			this.elements[i]=null;
		}
		this.size=0;
	}
	
	/**
	 * Umece odreden element na odredenu poziciju
	 * @throws NullPointerException ako je objekt <code>null</code>
	 * @param value
	 * @param position
	 */
	public void insert(T value, int position) {
		if(value == null)
			throw new NullPointerException();
		if(position<0 || position > this.size - 1 || elements.length <= this.size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			if(position < this.size) {
				int newSize = this.size;
				for(int i = this.size - 1; i >= position; i--) {
					elements[newSize--]=elements[i];
				}
			}
			elements[position]=value;
			this.size += 1;
		}
	}
	/**
	 * Metoda koja vraca index poslanog objekta
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		int index= -1;
		for(int i = 0; i<this.size; i++) {
			if(this.elements[i].equals(value)) {
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	 * Metoda koja provjerava je li neki objekt u kolekciji
	 * @return <code>true</code>ako je inace <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		for(int i =0; i<this.size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Brise odreden objekt sa zadanog indexa 
	 * @throws IndexOutOfBoundsException ako je index van granica kolekcije
	 * @param index
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		int j = index;
		for(int i = index + 1; i < size ; i++ ) {
			this.elements[j++]= elements[i];
		}
		this.elements[this.size-1] = null;
		this.size--;
	}
	/**
	 * Metoda koja vraca velicinu polja
	 */
	@Override
	public int size() {
		return this.size;
	}


	/**
	 * Metoda koja pretvara nasu kolekciju u polje objekata
	 * @return polje objekata
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray() {
		Object[] result =(T[]) new Object[this.size]; // ???????????????
		for(int i = 0; i<this.size ; i++) {
			result[i] = elements[i];
		}
		return result;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = 0;
		boolean found = false;
		for(int i =0; i< this.size; i++) {
			if(elements[i].equals(value)) {
				elements[i]=null;
				index = i;
				found = true;
				break;
			}
		}
		if(found==false)
			return false;
		else {
			int j = index;
			for(int i = index + 1; i < size ; i++ ) {
				this.elements[j++]= elements[i];
			}
			this.elements[this.size-1] = null;
			this.size--;
			return true;
		}
	}
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterClass<T>(this);
	}

	
}






