package hr.fer.oprpp1.hw04.db;


/**
 * Klasa koja opisuje jedan Token
 * @author Andrea
 *
 */
public class Token {

	private TokenType tokentype;
	private Object value;
	//private LexerState state;

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
