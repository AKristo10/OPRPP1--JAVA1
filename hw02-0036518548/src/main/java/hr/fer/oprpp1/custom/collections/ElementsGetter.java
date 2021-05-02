package hr.fer.oprpp1.custom.collections;

import java.util.NoSuchElementException;

public interface ElementsGetter {
	
	/**
	 * Metoda koja provjerava je li ima jos elemenata u kolekciji
	 * @return <code>true</code> ako ima, inace <code>false</code>
	 */
	public boolean hasNextElement();
	
	/**
	 * Dohvaca iduci element u kolekciji
	 * @throws NoSuchElementException ako nema elemenata vise
	 * @return element kolekcije
	 */
	public Object getNextElement();
	
	/**
	 * Poziva processor nad preostalim elementima u kolekciji
	 * @return void
	 */
	public default void processRemaining(Processor p) {
		while(hasNextElement())
			p.process(getNextElement());
	}
}
