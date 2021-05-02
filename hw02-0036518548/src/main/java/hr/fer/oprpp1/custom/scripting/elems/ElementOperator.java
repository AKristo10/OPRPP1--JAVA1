package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element{
	private String symbol;
	/**
	 * KOnstuktor koji prima ime symbola
	 * @param symbol
	 */
	 public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	 /**
	  * Vraca vrijednost operatora kao string
	  */
	@Override
	public String asText() {
		return symbol;
	}
	
}
