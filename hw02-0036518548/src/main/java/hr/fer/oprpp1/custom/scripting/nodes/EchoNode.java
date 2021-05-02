package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class EchoNode extends Node {
	Element[] elements;
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	/**
	 * Ispisuje sadrzaj EchoNode
	 */
	@Override
	public String asText() {
		String result = new String();
		result += "{$= ";
		for(int i = 0; i<elements.length; i++) {
			
			result += " " + this.elements[i].asText();
		}
		result += " $}";
		return result;
	}
}
