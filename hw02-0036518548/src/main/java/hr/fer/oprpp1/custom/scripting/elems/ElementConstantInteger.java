package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	private int value;
	/**
	 * Konstruktor koji priva integer vrijednost varijable value
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	/**
	 * Vraca vrijednost varijable value kao string
	 */
	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}	
	/**
	 * Vraca vrijednost varijable value kao integer
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
