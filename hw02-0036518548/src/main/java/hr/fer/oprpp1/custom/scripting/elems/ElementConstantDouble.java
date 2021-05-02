package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element{
	private double value;
	/**
	 * Konstrukotr koji prima double vrijednost value varijable
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	/**
	 * Vraca vrijednost value zapisanu u string
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	/**
	 * Vraca vrijednost varijable value
	 * @return
	 */
	public double getValue() {
		return value;
	}
}
