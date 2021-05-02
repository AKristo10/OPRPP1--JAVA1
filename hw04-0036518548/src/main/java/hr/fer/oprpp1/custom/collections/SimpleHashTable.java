package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K,V>> {
	int size;
	TableEntry<K, V>[] array;
	int modificationCount;
	/**
	 * Privatna staticka klasa koja opisuje jedan par kljuc, vrijednost
	 * @author Andrea
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K,V>{
		private K key;
		private V value;
		TableEntry<K,V> next;
		/**
		 * Konstruktor koji prima vrijednost kljuca, vrijednosti i iduceg para
		 * @param key
		 * @param value
		 * @param next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}
		/**
		 * Metoda koja vraca vrijednost kljuca
		 * @return vrijednost kljuca
		 */
		public K getKey() {
			return key;
		}
		/**
		 * Metoda koja vraca vrijednost Vrijednosti
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		/**
		 * Metoda koja postavlja Vrijednost
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}


	/**
	 * Jednostavan konstruktor koji inicijalizira polje velicine 16
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {
		array = new TableEntry[16];
	}
	/**
	 * Konstuktor koji stvara tablicu veličine koja je potencija broja 2 
	 * koja je prva veća ili jednaka predanom broju 
	 * @throws IllegalArgumentException ako je @param size <1
	 * @param size
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int size){
		if(size < 1)
			throw new IllegalArgumentException();
		double x = Math.log(size)/Math.log(2);
		int result = (int) Math.ceil(x);
		result = (int) Math.pow(2, result);
		array = new TableEntry[result];
	}
	/**
	 * Metoda koja postavlja novu vrijednost kljuca i vrijednosti u tablicu,
	 *  osim ako kljuc taj vec postoji onda samo mijenja vrijednost Vrijednosti
	 *  Ako je prepunjenost veca od 75% tada se velicina tablice dupla
	 * @param key
	 * @param value
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null)
			throw new NullPointerException();
		if(1. * this.size / array.length > 0.75) {
			TableEntry<K, V>[] newTable = new TableEntry[array.length];
			int i = 0;
			for(TableEntry<K, V> t : this.toArray()) {
				newTable[i++] = t;
			}
			int doubleSize = array.length*2;
			this.clear();
			this.array = new TableEntry[doubleSize];
			for(TableEntry<K, V> t : newTable) {
				this.put(t.getKey(), t.getValue());
			}
		}
		int hash = key.hashCode() % array.length;
		hash = Math.abs(hash);
		TableEntry<K, V> node = array[hash];
		if(node == null) {
			node = new TableEntry<K, V>(key, value, null);
			array[hash] = node;
			this.size++;
			modificationCount++;
			return null;
		}
		for(; node.next != null ; node = node.next ) {
			if(node.getKey().equals(key)) {
				node.setValue(value);
				return node.getValue();
			}	
		}

		if(node.getKey().equals(key)) {
			node.setValue(value);
			return node.getValue();
		}
		node.next = new TableEntry<K, V>(key, value, null);
		modificationCount++;
		size++;


		return null;
	}
	/**
	 * Metoda koja za zadani kljuc vraca vrijednost Value
	 * @param key
	 * @return <code>null</code> ako kljuc ne postoji u kolekciji inace vraca value
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		int hash = key.hashCode() % array.length;
		hash = Math.abs(hash);
		TableEntry<K, V> node = array[hash];
		for(;node != null; node = node.next) {
			if(node.getKey().equals(key)) {
				return node.getValue();
			}
		}
		return null;
	}
	/**
	 * Vraca broj parova
	 * @return broj parova
	 */
	public int size() {
		return size;
	}
	/**
	 * Metoda koja ispituje nalazi li se neki kljuc u kolekciji ili ne
	 * @param key
	 * @return <code>true</code> ako se nalazi, inace <code>false</code>
	 */
	public boolean containsKey(Object key) {
		if(key == null)
			return false;
		int hash = key.hashCode() % array.length;
		hash = Math.abs(hash);
		TableEntry<K, V> node = array[hash];
		for(;node != null; node = node.next) {
			if(node.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Metoda koja ispituje nalazi li se neka vrijednost(value) u kolekciji ili ne
	 * @param key
	 * @return <code>true</code> ako se nalazi, inace <code>false</code>
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < array.length; i++) {
			TableEntry<K, V> node = array[i];
			for(; node != null; node = node.next) {
				if((node.getValue() == null && value == null) ||node.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Metoda koja uklanja par kljuc-vrijednost za zadani kljuc
	 * @param key
	 * @return vrijednost uklonjenog para 
	 */
	public V remove(Object key) {
		if(key == null)
			return null;
		int hash = key.hashCode() % array.length;
		hash = Math.abs(hash);
		TableEntry<K, V> node = array[hash];
		TableEntry<K, V> previousPom=null;
		//idi po nodovima dok ne nadjes key
		for(;node != null; node = node.next) {
			if(node.getKey().equals(key) && previousPom == null && node.next == null) {
				V resultValue = node.getValue();
				array[hash] = null;
				this.size--;
				modificationCount++;
				return resultValue;
			}
			if(node.getKey().equals(key) && previousPom == null) {
				V resultValue = node.getValue();
				array[hash] = node.next;
				node = null;
				this.size--;
				modificationCount++;
				return resultValue;
			}
			else if(node.getKey().equals(key)) {
				previousPom.next=node.next;
				V resultValue = node.getValue();
				node = null;
				this.size--;
				modificationCount++;
				return resultValue;
			}
			previousPom = node;
		}
		return null;
	}
	/**
	 * Metoda koja provjerava je li tablica prazna
	 * @return <code>true</code> ako da, inace <code>false</code>
	 */
	public boolean isEmpty() {
		return size <= 0;
	}
	@Override
	public String toString() {
		String str = "[";
		boolean prvi = true;
		for(int i = 0; i < array.length; i++) {
			TableEntry<K, V> node = array[i];
			for(; node != null; node = node.next) {
				if(prvi) {
					str += node.getKey() + "=" + node.getValue();
					prvi = false;
				}
				else
					str += ", " + node.getKey() + "=" + node.getValue();
			}
		}
		str += "]";
		return str;
	}
	/**
	 * Pretvara nasu tablicu u polje
	 * @return polje u koje su spremljeni svi parovi kljuc-vrijednost.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K, V>[] result = new TableEntry[this.size];
		int index = 0;
		for(int i = 0; i < array.length; i++) {
			TableEntry<K, V> node = array[i];
			for(; node != null; node = node.next) {
				result[index++] = node;
			}
		}
		return result;
	}
	/**
	 * Metoda koja brise cijelu tablicu
	 */
	//PREPRAVI OVU METODU!!!!!!!!!!!!!!!!!!!
	public void clear() {
		for(int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		this.size = 0;

	}
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K,V>> {
		int count = modificationCount;
		int index = 0;
		boolean canRemove = false;
		TableEntry<K, V> lastNodeSend = null;
		public boolean hasNext() {
			if(count != modificationCount)
				throw new ConcurrentModificationException();
			return index <size;

		}
		
		public SimpleHashTable.TableEntry<K, V> next() {
			if(count != modificationCount)
				throw new ConcurrentModificationException();
			if(index >= size)
				throw new NoSuchElementException();
			
			lastNodeSend = toArray()[index];
			
			canRemove = true;
			index ++;
			return lastNodeSend;
		}

		public void remove() {
			if(!canRemove)
				throw new IllegalStateException();
			else {
				if(count != modificationCount)
					throw new ConcurrentModificationException();
			}
			SimpleHashTable.this.remove(lastNodeSend.getKey());
			canRemove = false;
			index--;
			count++;
		}
	}
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new IteratorImpl();
	}



}
