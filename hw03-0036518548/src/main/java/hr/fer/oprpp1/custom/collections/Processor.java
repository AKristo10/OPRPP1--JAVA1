package hr.fer.oprpp1.custom.collections;

public interface Processor<T> {
	
	/**
	 * Metoda koja za zadani objekt napravi neki proces
	 * @param value
	 */
	public void process(T value);
	
	
}
