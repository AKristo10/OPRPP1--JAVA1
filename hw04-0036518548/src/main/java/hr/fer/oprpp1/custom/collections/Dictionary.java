package hr.fer.oprpp1.custom.collections;

public class Dictionary<K,V> {


	/**
	 * Privatna staticka klasa koja predstavlja par te stoga ima dvije varijable; kljuc, vrijednost.
	 * @author Andrea
	 * @param <K>
	 * @param <V>
	 */
	private static class Pair<K,V>{
		K key;
		V value;
		/**
		 * Konstruktor koji prima vrijednosti kljuca i vrijednosti
		 * @param key
		 * @param value
		 */
		public Pair(K key, V value) {
			if(key != null)
				this.key = key;
			else
				throw new NullPointerException();
			this.value = value;
		}
		/**
		 * Metoda koja vraca vrijednost kljuca
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		/**
		 * Metoda koja vraca vrijednost vrijednosti
		 * @return key
		 */
		public V getValue() {
			return value;
		}
		/**
		 * Metoda koja postavlja vrijednost kljuca
		 * @return key
		 */
		@SuppressWarnings("unused")
		public void setKey(K key) {
			this.key = key;
		}
		/**
		 * Metoda koja postavlja vrijednost vrijednosti
		 * @return key
		 */
		public void setValue(V value) {
			if(value == null)
				throw new IllegalArgumentException();
			else
				this.value = value;
		}
	}

	ArrayIndexedCollection<Pair<K,V>> array;
	public Dictionary() {
		// TODO Auto-generated constructor stub
		array = new ArrayIndexedCollection<Pair<K,V>>();
	}
	

	/**
	 * Metoda koja provjerava je li rjecnik prazan
	 * @return <code>true</code> ako je prazan, inace <code>false</code>
	 */
	public boolean isEmpty() {
		if(array.isEmpty())
			return true;
		else return false;
	}

	/**
	 * Metoda koja vraca velicnu rjecnika
	 * @return velicina polja
	 */
	public int size() {
		return array.size();
	}
	/**
	 * Metoda koja brise sve clanove kolekcije
	 */
	public void clear() {
		array.clear();
	}
	/**
	 * Metoda koja postavlja Par(kljuc, element) u kolekciju
	 * @param key
	 * @param value
	 * @return <code>null</code> ako nije postojao isti key kao parametar ili staru vrijednost value
	 */
	public V put(K key, V value) {
		for(int i = 0; i<array.size(); i++) {
			Pair<K, V> pair = array.get(i);
			if(pair.getKey().equals(key)) {
				pair.setValue(value);
				return pair.getValue();
			}
		}
		Pair<K,V> p = new Pair<K,V>(key, value);
		array.add(p);
		return null;
	}
	/**
	 * Metoda koja za zadani kljuc vraca vrijednost
	 * @param key
	 * @return vrijednost osim ako kljuc nije <code>null</code>
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		else {
			for(int i = 0; i<array.size(); i++) {
				Pair<K, V> pair = array.get(i);
				if(pair.getKey().equals(key))
					return pair.value;	
			}
			return null;
		}
	}
	/**
	 * Metoda koja brise iz kolekcije Par koji ima kljuc isti kao sto je zadan u parametrima
	 * @param key
	 * @return <code>null</code> ako ne postoji kljuc kao u parametru, inace vrijednost para koji je obrisan
	 */
	public V remove(K key) {
		if(key == null)
			return null;
		for(int i = 0; i<array.size(); i++) {
			Pair<K, V> pair = array.get(i);
			if(pair.getKey().equals(key)) {
				array.remove(pair);
				return pair.getValue();
			}		
		}
		return null;
	}
}
