package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa koja opisuje jedan Token
 * @author Andrea
 *
 */
public class Token {

	TokenType tokentype;
	Object value;
	
	/**
	 * Konstrukor koji prima tip tokena i njegovu vrijednost
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.tokentype=type;	
		this.value=value;
	}
	/**
	 * Metoda koja vraca vrijednost Tokena
	 * @return
	 */
	public Object getValue() {
		return this.value;
	}
	/**
	 * Metoda koja vraca tip tokena
	 * @return
	 */
	public TokenType getType() {
		return this.tokentype;
	}

}
