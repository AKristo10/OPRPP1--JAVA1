package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;
/**
 * Klasa koja predstavlja kontekst.
 * @author Andrea
 *
 */
public class Context {
	private ObjectStack<TurtleState> stack = new ObjectStack<TurtleState>();

	/**
	 * Metoda koja vraca zadnje stanje sa stoga
	 * @return zadnje stanje stoga
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	/**
	 * Metoda koja stavlja stanje na stog.
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	/**
	 * Metoda koja skida zadnje stanje sa stoga.
	 */
	public void popState() {
	stack.pop();
	}
}
