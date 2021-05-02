package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element {
	private String value;
	/**
	 * Konstrukotr koji prima String vrijednost value varijable
	 * @param value
	 */
	public ElementString(String value) {
		this.value=value;
	}
	/**
	 * Vraca vrijednost varijable value;
	 */
	@Override
	public String asText() {
		return new String("\""+ this.value + "\"");
	}
}
