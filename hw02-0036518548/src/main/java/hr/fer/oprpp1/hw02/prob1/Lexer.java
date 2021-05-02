package hr.fer.oprpp1.hw02.prob1;
/**
 * Klasa koja simulira provedbu rada leksickog analizatora
 * @author Andrea
 *
 */

public class Lexer {

	private char[] data;
	private Token token; 
	private int currentIndex; 
	LexerState state = LexerState.BASIC;
	
	/**
	 * Konstruktor koji zadani text pretvara u polje znakova data
	 * @param text
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new  NullPointerException();
		}
		this.data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generira i vraca iduci token
	 * @throws LexerException ako dode do pogreske
	 * @return novi token
	 */
	public Token nextToken() {

		
		if(this.token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}
		blanksSkip();

		if(currentIndex >= data.length ) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		if(state == LexerState.BASIC) {
			boolean escape = false;
			int start = currentIndex;
			String word = new String();
			String resultWord = new String();
			if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				if(data[currentIndex] == '\\') {
					if((currentIndex == data.length-1) 
							||!(Character.isDigit(data[currentIndex+1]))) {
						throw new LexerException();
					}
					start = currentIndex + 1;
					currentIndex += 2;
				}
				else {
					start = currentIndex;
					currentIndex++;
				}
				while(currentIndex < data.length) {
					if(data[currentIndex] =='\\' && escape == false){
						escape = true;
						word = new String(data, start, currentIndex-start);
						resultWord += word;
						currentIndex++;
						start = currentIndex;
						continue;
					}
					else if((data[currentIndex] == '\\' && escape == true) 
							||(Character.isDigit(data[currentIndex]) && escape == true)
							||(Character.isLetter(data[currentIndex]) && escape == false)) {
						currentIndex++;
						escape = false;
						continue;
					}
					else if(Character.isLetter(data[currentIndex]) && escape == true) {
						throw new LexerException();
					}
					else break;

				}

				String tmp = new String(data, start, currentIndex-start);
				resultWord += tmp;
				this.token = new Token(TokenType.WORD, resultWord);
				return this.token;
			}

			else if(Character.isDigit(data[currentIndex])) {
				start = currentIndex;
				while(currentIndex < data.length) {
					if(Character.isDigit(data[currentIndex])) {
						currentIndex++;
					}
					else break;
				}
				word = new String(data, start, currentIndex-start); 
				try {
					long number = Long.parseLong(word);
					this.token = new Token(TokenType.NUMBER, number);
					return this.token;
				}
				catch (Exception e) {
					throw new LexerException();
				}}
			else {
				this.token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return this.token;

			}
		}
		else {
			if(data[currentIndex] =='#') {
				this.token = new Token(TokenType.SYMBOL, '#');
				currentIndex++;
			}
			else {
				int start = currentIndex;
				currentIndex++;
				while(currentIndex<data.length) {	
					if(data[currentIndex] == ' ' || data[currentIndex]== '\n' || data[currentIndex]=='\t'
							|| data[currentIndex] == '\r'||data[currentIndex] =='#') {
		
						break;
					}
					currentIndex++;
				}
				String tmp = new String(data, start, currentIndex-start);
				this.token = new Token(TokenType.WORD, tmp);
			}
			return this.token;
		}
	}

/**
 * Metoda koja postavlja stanje Lexera
 * @param state moze biti BASIC i EXTENDED
 */
	public void setState(LexerState state) {
		if(state == null)
			throw new NullPointerException();
		this.state = state;
	}

	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	public Token getToken() {
		return this.token;
	}

	/**
	 * Metoda koja sluzi za preskakanje praznina
	 */
	public void blanksSkip() {
		while(currentIndex < data.length) {
			if(data[currentIndex] == ' ' || data[currentIndex]== '\n' || data[currentIndex]=='\t'
					|| data[currentIndex] == '\r') {
				currentIndex++;
				continue;
			}
			else break;
		}
	}
	

}

