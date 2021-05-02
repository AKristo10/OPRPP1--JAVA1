package hr.fer.oprpp1.hw04.db;

public class Lexer {

	private char[] data;
	private Token token;
	private int currentIndex;
	String operator;

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
	 ** Metoda koja vraca iduci token
	 * 
	 * @return iduci token
	 */
	public Token nextToken() {
		skipBlanks();
		String variable = new String();
		isLogicalAND();
		skipBlanks();
		//ako smo na kraju
		if (currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		// rijec je o operatoru
		else if ( isOperator()) {
			this.token = new Token(TokenType.OPERATOR, operator);
			return this.token;
		}

		// kada je u pitanju kljucna rijec (firstName, lastName, jmbag)
		else if (Character.isLetter(data[currentIndex])) {
			while (currentIndex < data.length && Character.isLetter(data[currentIndex]) && !isOperator()) {
				variable += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
			if (variable.equals("jmbag")) {
				this.token = new Token(TokenType.VARIABLE, "jmbag");

			} else if (variable.equals("firstName")) {
				this.token = new Token(TokenType.VARIABLE, "firstName");

			} else if (variable.equals("lastName")) {
				this.token = new Token(TokenType.VARIABLE, "lastName");

			}
			else 
				throw new IllegalArgumentException("Neprepoznat token!");
			return this.token;
		}

		// ako je String u pitanju
		else if (data[currentIndex] == '\"') {
			currentIndex++;
			String value = new String();
			boolean wildcard = false;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				if (this.token.getValue().equals("LIKE") && wildcard && data[currentIndex] =='*') {
					throw new IllegalArgumentException("Ne moze se dva puta pojavljivati znak * !");
				} else if (this.token.getValue().equals("LIKE") && !wildcard && data[currentIndex] == '*') {
					wildcard = true;
				}
				value += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
			currentIndex++;
			this.token = new Token(TokenType.STRING, value);
			return this.token;
		}


		else 
			throw new IllegalArgumentException("Nema Tokena!");
	}

	/**
	 * Metoda koja provjerava jesmo li naisli na operator. Operator moze biti >,
	 * <, >=, <=, =, !=.
	 * 
	 * @return
	 */
	public boolean isOperator() {
		operator = new String();
		if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
			if (data[currentIndex] == '<' || data[currentIndex] == '>' || data[currentIndex] == '!') {
				operator = String.valueOf(data[currentIndex]) + String.valueOf(data[currentIndex + 1]);
				currentIndex += 2;
				return true;
			}

		} else if (data[currentIndex] == '=' || data[currentIndex] == '<' || data[currentIndex] == '>') {
			operator = String.valueOf(data[currentIndex]);
			currentIndex++;
			return true;
		}
		if (currentIndex + 3 < data.length) {
			operator = String.valueOf(data[currentIndex]) + String.valueOf(data[currentIndex + 1])
			+ String.valueOf(data[currentIndex + 2]) + String.valueOf(data[currentIndex + 3]);
			if (operator.toUpperCase().equals("LIKE")) {
				currentIndex += 4;
				return true;
			}
		}
		return false;
	}


	/**
	 * Metoda koja provjerava jesmo li nasli na logicki operator AND
	 * 
	 * @return <code>true</code> ako jesmo, inace <code>false</code>
	 */
	public boolean isLogicalAND() {
		if (currentIndex + 2 < data.length) {
			String logicalOperator = String.valueOf(data[currentIndex]) + String.valueOf(data[currentIndex + 1])
			+ String.valueOf(data[currentIndex + 2]);
			if (logicalOperator.toUpperCase().equals("AND")) {
				currentIndex += 3;
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda koja preskace sve praznine.
	 */
	public void skipBlanks() {
		while (currentIndex < data.length) {
			if (data[currentIndex] == ' ' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
					|| data[currentIndex] == '\r') {
				currentIndex++;
				continue;
			} else
				break;
		}
	}
	/**
	 * Metoda koja vraca trenutni token
	 * @return trenutni token
	 */
	public Token getToken() {
		return token;
	}
	public static void main(String[] args) {
		String text = " lastName LIKE \"Be*\"";
		Lexer lex = new Lexer(text);
		while(lex.nextToken().getType() != TokenType.EOF )
			System.out.println(lex.token.getType() + " " + lex.token.getValue());
	System.out.println(lex.token.getType());
	}

}
