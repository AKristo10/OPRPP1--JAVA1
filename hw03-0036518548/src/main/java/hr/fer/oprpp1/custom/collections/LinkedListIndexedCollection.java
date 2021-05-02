package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

/**
 * Razred LinkedListIndexedCollection korisnicima omogucava spremanje podataka
 * u dvostruko povezanu listu 
 * Takoder omogucuje manipularanje listom odnosno dodavanje, brisanje, umetanje i ostale metode
 * @author Andrea
 *
 */

public class LinkedListIndexedCollection<T> implements List<T> {

	private int size;
	ListNode<T> first;
	ListNode<T> last;
	/**
	 * Privatna klasa koja opisuje cvor liste 
	 * Sastoji se od vrijednosti, pokazivaca na prethodni i iduci element liste
	 * @author Andrea
	 *
	 * @param <Object>
	 */
	private static class ListNode<T>{
		T item; 
		ListNode<T> previous;
		ListNode<T> next;

	}
	/**
	 * Privatna statička klasa koja stvara novu instancu ElementsGettera
	 * @author Andrea
	 *
	 */
	private static class ElementsGetterClass<T> implements ElementsGetter<T>{
		
		LinkedListIndexedCollection<T> collection;
		int nextElement=0;
		ListNode<T> node = new ListNode<>();
		
		public ElementsGetterClass(LinkedListIndexedCollection<T> collection) {
			this.collection = collection;
			node = collection.first;
		}
		@Override
		public boolean hasNextElement() {
			return nextElement < collection.size();
		}

		@Override
		public T getNextElement() {
			if(nextElement >= collection.size()) {
				throw new NoSuchElementException();
			}
			T result = node.item;
			node = node.next;
			nextElement++;
			return result;
		}
	
	}
		
	/**
	 * Jednostavan konstruktor koji postavlja i glavu i rep na <code>null</code>
	 */
	public LinkedListIndexedCollection() {
		this.first=this.last=null;
	}
	/**
	 * Dodaje cijelu kolekciju other u listu
	 * @param other
	 */
	
	public LinkedListIndexedCollection(Collection<T> other) {
		this.addAll(other);
	}
	
	/**
	 * Metoda koja vraca trenutnu velicinu kolekcije
	 */
	public int size() {
		return this.size;
	}
	/**
	 * Provjerava je li kolekcija prazna
	 * @return <code>true</code> ako je prazna inace <code>false</false>
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size <= 0;
	}
	/**
	 * Pretvara listu u polje objekata
	 * @return polje objekata
	 */
	@Override
	public Object[] toArray() {
		ListNode<T> l = new ListNode<T>();
		Object[] polje = new Object[this.size];
		int i = 0;
		for(l = first; l != null;l = l.next) {
			polje[i++] = l.item;
		}
		return polje;
	}
	/**
	 * Metoda dodaje objekt u listu.
	 * @throws NullPointerException ako je objekt <code>null</code>
	 */

	@Override
	public void add(T value) {
		if(value == null) {
			throw new NullPointerException();
		}
		ListNode<T> newElement = new ListNode<T>();
		newElement.item= value;
		newElement.next=null;
		if(last != null) {
			last.next = newElement;
			newElement.previous = last;
		}
		else {
			first = newElement;
			newElement.previous = null;
		}
		last=newElement;
		this.size++;
	}
	/**
	 * Vraca objekt sa zadane pozicije
	 * @throws IllegalArgumentException ako je index izvan granica liste
	 * @param index
	 * @return objekt sa zeljene pozicije
	 */
	public T get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IllegalArgumentException();
		}
		else {
			ListNode<T> l = new ListNode<T>();
			T result = null;
			int i;
			if(index <= this.size/2) {
				i=0;
				for(l=first; l!=null;l=l.next ) {
					if(i++ ==index) {
						result = l.item;
						break;
					}
				}	
			}
			else {
				i=this.size-1;
				for(l=last; l!=null; l =l.previous) {
					if(i-- ==index) {
						result = l.item;
						break;
					}
				}
			}
			return result;

		}

	}
	/**
	 * Metoda koja brise cijelu listu
	 * 
	 * 	 */
	@Override
	public void clear() {
		ListNode<T> next = new ListNode<T>();
		for(ListNode<T> n = first; n != null; n =next) {
			next = n.next;
			n.item = null;
			n.next=null;
			n.previous=null;
			n = next;    
		}
		this.size=0;
	}
	/**
	 * Dodaje zeljeni objekt na zeljenu pozciju
	 * @throws NullPointerException ako je objekt <code>null</code> vrijednost
	 * @throws IllegalArgumentException ako je pozicija izvan okvira duljine liste
	 * @param value
	 * @param position
	 */
	@Override
	public void insert(T value, int position) {
		if(value == null)
			throw new NullPointerException();
		if(position <0 || position >= size)
			throw new IllegalArgumentException();
		ListNode<T> n = new ListNode<>();
		ListNode<T> novi = new ListNode<T>();
		if(position == 0) {
			novi.item = value;
			novi.previous = null;
			novi.next = first;
			first.previous = novi;
			first = novi;
		}else {
			n = first;
			for(int i=0; i<position; i++) {
				n = n.next;
			}
			novi.item=value;
			novi.next=n;
			novi.previous=n.previous;
			n.previous.next=novi;
			n.previous = novi;	
		}
		this.size++;
	}
	/**
	 * Metoda koja vraca broj pozicije(index) na kojoj se nalazi zadni objekt
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		int result = -1;
		int index = 0;
		for(ListNode<T> n = first; n != null;n = n.next) {
			if(n.item.equals(value)) {
				result = index;
				return result;
			}
			index++;		
		}
		return result;
	}
	/**
	 * Metoda koja uklanja odredeni element s odredene pozicije koja se navodi kao parametar
	 * @throws IllegalArgumentException ako je pozicija izvan okvira zadane kolekcije
	 */
	public void remove(int index) {
		if(index < 0 && index > size-1)
			throw new IllegalArgumentException();
		ListNode<T> n = new ListNode<>();
		n=first;
		if(index == 0) {
			first = n.next;
			n.next.previous = null;
			return;
		}
		for(int i = 0; i != index ; i++) {
			n = n.next;
		}

		if(index == this.size - 1) {
			last = n.previous;
			n.previous.next = null;
		}
		else {
			n.next.previous = n.previous;
			n.previous.next = n.next;
			n.item=null;
			n.next=null;
			n.previous=null;
			this.size--;
		}
	}
	/**
	 * Metoda koja provjerava nalazi li se odredeni objekt u kolekciji
	 * @return <code>true</code> ako se objekt nalazi, inace <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		ListNode<T> l = new ListNode<T>();
		for(l=first; l!=null; l=l.next) {
			if(l.item.equals(value))
				return true;
		}
		return false;
	}
	@Override
	public boolean remove(Object value) {
		if(value == null)
			throw new IllegalArgumentException();
		ListNode<T> n = new ListNode<>();
		for(n=first; n !=null; n= n.next) {
			if(n.item.equals(value)) {
				this.size--;
				if(n==first) {
					first = n.next;
					n.next.previous = null;
					return true;
				}
				else if(n==last) {
					last = n.previous;
					n.previous.next = null;
					n.item=null;
					n.previous=null;
					n.next=null;
					return true;
				}
				else {
					n.next.previous = n.previous;
					n.previous.next = n.next;
					n.item=null;
					n.next=null;
					n.previous=null;
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Metoda koja pravi novu instancu ElementsGetter
	 * @return novi ElementsGetter
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		// TODO Auto-generated method stub
		return new ElementsGetterClass<T>(this);
	} 
	
	
	
}


