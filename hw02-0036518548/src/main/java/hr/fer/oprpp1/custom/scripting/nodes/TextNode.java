package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node {
	private String text;
	public TextNode(String text) {
		this.text = text;
	}
	/**
	 * Ispisuje sadrzaj TextNoda
	 */
	@Override
	public String asText() {
		
		char[] polje= text.toCharArray();
		String result = new String();
		for(int i = 0; i<polje.length; i++) {
			if(polje[i]!= '\\' && polje[i] != '{') {
				result += polje[i];
			} else {
				result += "\\" + polje[i];
			}
		}
		
		return result;
	
	}



}
