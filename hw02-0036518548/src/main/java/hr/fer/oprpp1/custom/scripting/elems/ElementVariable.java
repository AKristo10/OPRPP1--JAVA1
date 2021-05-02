package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element{
	private String name;
	/**
	 * Konstruktor koji prima String vrijednost varijable name
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name=name;
	}
	/**
	 * Vraca vrijednost varijable name kao text
	 */
	@Override
	public String asText() {
		return this.name;
	}
	/**
	 * Vraca vrijednost varijable name
	 * @return
	 */
	public String getName() {
		return name;
	}
}
