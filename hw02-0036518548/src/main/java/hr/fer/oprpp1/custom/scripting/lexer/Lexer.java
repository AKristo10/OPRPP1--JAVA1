package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.Collection;

import hr.fer.oprpp1.hw02.prob1.LexerException;

public class Lexer {

	private char[] data;
	private Token token;
	private int currentIndex;
	LexerState state = LexerState.BASIC;
	
	/**
	 * Konstruktor koji prima text i nad njim ce vrsiti postupak leksicke analize
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Funkcija koja generira iduci token.
	 * 
	 * @return
	 */
	public Token NextToken() {
		/*
		 * if(this.token != null && token.getType().equals(TokenType.EOF)) { throw new
		 * LexerException(); }
		 */
		
		if (currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}

		if (data[currentIndex] != '{' && currentIndex + 1 < data.length && data[currentIndex + 1] != '$'
				&& getState().equals(LexerState.BASIC)) {

			String word = new String();
			word += data[currentIndex];
			currentIndex++;
			while (currentIndex < data.length) {
				if (isThatTagStart())
					break;
				else {
					if (data[currentIndex] == '\\' && data[currentIndex + 1] == '\\') {
						word += '\\';
						currentIndex++;
					} else if (data[currentIndex] == '\\' && data[currentIndex + 1] == '{') {
						currentIndex++;
						word += '{';
					}
					else if (data[currentIndex] == '\\' && data[currentIndex + 1] != '{'
							&& data[currentIndex + 1] != '\\')
						throw new LexerException();
					else
						word += data[currentIndex];
				}
				currentIndex++;
			}

			this.token = new Token(TokenType.TEXTNODE, word);
			return this.token;
		}

		if (data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
			currentIndex += 2;
			skipBlanks();
			String result = new String();
			if(data[currentIndex] =='=') {
				result += data[currentIndex];
				currentIndex++;
			}
			else {
				while (currentIndex < data.length
						&& (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]))) {
					result += data[currentIndex];
					currentIndex++;
				}
			}
			this.token = new Token(TokenType.TAG, result.toUpperCase());
			return this.token;
		}

		else if (getState().equals(LexerState.TAG)) {
			skipBlanks();
			String result = new String();
			boolean isNegative = false;
			if (Character.isLetter(data[currentIndex])) {
				result += data[currentIndex];
				currentIndex++;
				while ((currentIndex < data.length) && (data[currentIndex] == '_'
						|| Character.isDigit(data[currentIndex]) || Character.isLetter(data[currentIndex]))) {
					result += data[currentIndex];
					currentIndex++;
				}
				this.token = new Token(TokenType.VARIABLE, result);
				return this.token;
			} else if (currentIndex+1 < data.length && Character.isDigit(data[currentIndex])
					|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {
				if (data[currentIndex] == '-') {
					isNegative = true;
				}
				result += data[currentIndex];
				currentIndex++;
				while (data[currentIndex] == '.'  || Character.isDigit(data[currentIndex])) {
					result += data[currentIndex];
					currentIndex++;
				}
				if (isNegative)
					result = "-" + result;

				if (result.contains("."))
					this.token = new Token(TokenType.DOUBLE, result);
				else
					this.token = new Token(TokenType.INTEGER, result);
				return this.token;
			} else if (data[currentIndex] == '\"') {
				currentIndex++;
				while ((currentIndex < data.length) && (data[currentIndex] != '\"')) {
					if (data[currentIndex] == '\\' && currentIndex + 1 < data.length && checkEscape()) {
						if(data[currentIndex+1] == 'n')
							result += '\n';
						else if(data[currentIndex+1] == 't')
							result += '\t';
						else if(data[currentIndex+1] == 'r')
							result += '\r';
						else if(data[currentIndex+1] == '"')
							result += '\"';
						else if(data[currentIndex+1] == '\\')
							result += '\\';
						currentIndex += 2;
						continue;
					}
					else if(data[currentIndex] == '\\' && currentIndex + 1 < data.length && !checkEscape()) {
						throw new LexerException();
					}
					else {
					result += data[currentIndex];
					currentIndex++;
					}
				}
				this.token = new Token(TokenType.STRING, result);
				currentIndex++;
				return this.token;
			} else if (data[currentIndex] == '@' && Character.isLetter(data[currentIndex + 1])) {
				result += data[currentIndex];
				currentIndex++;
				while (currentIndex < data.length
						&& (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]))) {
					result += data[currentIndex];
					currentIndex++;
				}
				this.token = new Token(TokenType.FUNCTION, result);
				return this.token;
			} else if (isOperator()) {
				result += data[currentIndex];
				this.token = new Token(TokenType.OPERATOR, result);
				currentIndex++;
				return this.token;
			} else if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
				result = "&}";
				this.token = new Token(TokenType.ENDTAG, result);
				currentIndex += 2;
				return this.token;
			} else
				throw new LexerException();

		}

		return this.token;
	}

	/**
	 * Metoda koja provjerava Escape sekvencu
	 * @return
	 */
	public boolean checkEscape() {
		if(data[currentIndex+1] == 'n' || data[currentIndex+1] =='t' || data[currentIndex+1] == '\\'
				|| data[currentIndex+1] =='r' || data[currentIndex+1] == '\"')
			return true;
		else return false;

	}
	/**
	 * Provjerava je li znak na trenutnoj poziciji operator
	 * @return <code>true</code> ako da, inace <code>false</code>
	 */
	public boolean isOperator() {
		if(data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/'
				|| data[currentIndex] == '^'
				|| (data[currentIndex] == '-' && data.length > currentIndex+1 && !Character.isDigit(data[currentIndex+1]) ))
			return true;
		else return false;
	}
	/**
	 * Funkcija koja postavlja stanje Lexera, moze biti basic ili tag
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		this.state = state;
	}

	/**
	 * Funkcija koja vraca stanje Lexera, moze biti basic ili tag
	 * 
	 * @param state
	 */

	public LexerState getState() {
		return state;
	}
	/**
	 * Metoda koja vraca zadnji izgeneriran token
	 * @return
	 */
	public Token getToken() {
		return token;
	}
 public boolean isThatTagStart() {
	 return (data[currentIndex] == '{' && currentIndex+1<data.length && data[currentIndex + 1] == '$' && data[currentIndex - 1] != '\\');
 }
	/**
	 * Metoda koja preskace znak razmaka
	 */
	public void skipBlanks() {
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
