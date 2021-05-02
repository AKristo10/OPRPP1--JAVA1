package hr.fer.oprpp1.custom.collections;
/**
 * Klasa koja omogucuje korisniku spremanje elmenata u polje objekata
 * Omoguceno je manipuliranje tim poljem, dodavanje elemenata, brisanje i slicno
 * @author Andrea
 *
 */
public class ArrayIndexedCollection extends Collection{

	private int size;
	private Object[] elements;
 /**
  * Pravi instancu objekta velicine 16
  */
	public ArrayIndexedCollection() {
		this.elements = new Object[16];
	}
/**
 *   *Pravi instancu objekta velicine initialCapacity
 * @param initalCapacity velicina polja
 * @throws IllegalArgumentException ako je velicina manja od 1
 */
	
	public ArrayIndexedCollection(int initalCapacity) {
		if (initalCapacity < 1)
			throw new IllegalArgumentException();
		else {
			this.elements = new Object[initalCapacity];
		}
	}
/**
 * Konstruktor koji pravi instancu objekta tako da kopira drugu kolekciju
 * @throws NullPointerException ako je je kolekcija <code>null</code>
 * @param other
 */
	public ArrayIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException();
		}
		if(16 < other.size()) {
			this.elements = new Object[other.size()];
		}
		else {
			this.elements = new Object[16];
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

	public ArrayIndexedCollection(int initalCapacity, Collection other) {
		if(other == null)
			throw new NullPointerException();
		if(initalCapacity < other.size()) {
			this.elements = new Object[other.size()];
		}
		else {
			this.elements = new Object[initalCapacity];
		}
		this.addAll(other);
		this.size = other.size();
	}
	/**
	 * Metoda koja dodaje zadani objekt u kolekciju
	 * @throws NullPointerException ako je objekt <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(elements.length <= this.size) {
			Object[] elements2 = new Object[elements.length * 2];
			int i =0;
			for(Object o : elements) {
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
	public Object get(int index) {
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
	 * Metoda koja iterira kolekcijom i nad svakim objektom poziva metodu process
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i<this.size; i++) {
			processor.process(elements[i]);
		}
	}
	/**
	 * Umece odreden element na odredenu poziciju
	 * @throws NullPointerException ako je objekt <code>null</code>
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
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
	@Override
	/**
	 * Metoda koja provjerava je li kolekcija prazna
	 * @return <code>true</code> ako da inace <code>false</code>
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size <= 0;
	}
	/**
	 * Metoda koja pretvara nasu kolekciju u polje objekata
	 * @return polje objekata
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size];
		for(int i = 0; i<this.size ; i++) {
			result[i] = elements[i];
		}
		return result;
	}

}




