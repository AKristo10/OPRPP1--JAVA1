package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element {
	private String name;
	/**
	 * Jednostavan konsturktor koji prima ime varijable
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	/**
	 * Vraca vrijednost varijable name kao String
	 */
	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
