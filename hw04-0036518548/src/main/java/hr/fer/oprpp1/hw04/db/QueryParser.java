package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;

/**
 * Klasa koja predstavlja parser.
 * @author Andrea
 *
 */

public class QueryParser {
	Lexer lexer;
	String query;
	ArrayList<ConditionalExpression> list = new ArrayList<ConditionalExpression>();
	
	/**
	 * Konstruktor koji prima parametara koji predstavlja query.
	 * @param query
	 */
	public QueryParser(String query) {
		this.query = query;
		lexer = new Lexer(query);
		parse();
	}
	
	/**
	 * Metoda koja parsira dobiveni query.
	 */
	public void parse() {
		IFieldValueGetter attribute ;
		String literal;
		IComparisonOperator operator;
		while(lexer.nextToken().getType() != TokenType.EOF) {
			if(lexer.getToken().getType() == TokenType.VARIABLE) {
				attribute = getAttribute();
				if(lexer.nextToken().getType() == TokenType.OPERATOR) {
					operator = getOperator();
					if(lexer.nextToken().getType() == TokenType.STRING) {
						literal = (String) lexer.getToken().getValue();
						list.add(new ConditionalExpression(attribute, literal, operator));
					}
					else
						throw new IllegalArgumentException();
				}
				else 
					throw new IllegalArgumentException();
			}
			else 
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Metoda koja vraca vrstu atributa
	 * @return vrsta atributa
	 */
	public IFieldValueGetter getAttribute() {
		IFieldValueGetter attribute = null;
		if(lexer.getToken().getValue().equals("jmbag")) {
			attribute = FieldValueGetters.JMBAG; 
		}
		else if(lexer.getToken().getValue().equals("firstName")) {
			attribute = FieldValueGetters.FIRST_NAME;
		}
		else if (lexer.getToken().getValue().equals("lastName")) {
			attribute = FieldValueGetters.LAST_NAME;
		}
		return attribute;
	}
	
	/**
	 * Metoda koja vraca vrijednost operatora.
	 * @return vrijednost operatora.
	 */
	public IComparisonOperator getOperator() {
		IComparisonOperator operator = null;
		if(lexer.getToken().getValue().equals("<=")) {
			operator = ComparisonOperators.LESS_OR_EQUALS;
		}
		else if(lexer.getToken().getValue().equals(">=")) {
			operator = ComparisonOperators.GREATER_OR_EQUALS;
		}
		else if(lexer.getToken().getValue().equals("!=")) {
			operator = ComparisonOperators.NOT_EQUALS;
		}
		else if(lexer.getToken().getValue().equals("=")) {
			operator = ComparisonOperators.EQUALS;
		}
		else if(lexer.getToken().getValue().equals("<")) {
			operator = ComparisonOperators.LESS;
		}
		else if(lexer.getToken().getValue().equals(">")) {
			operator = ComparisonOperators.GREATER;
		}
		else if(lexer.getToken().getValue().equals("LIKE")) {
			operator = ComparisonOperators.LIKE;
		}
		return operator;
	}
	
	/**
	 * Metoda koja provjerava je li u listi spremljen samo jedan jednostavan izraz: jmbag = "neki jmbag"
	 * @return
	 */
	public boolean isDirectQuery() {
		if(list.size() == 1) {
			if(list.get(0).attribute.equals(FieldValueGetters.JMBAG)) {
				if(list.get(0).operator.equals(ComparisonOperators.EQUALS)) {
					return true;
				}
			}			
		}
		return false;
	}
	
	/**
	 * Metoda koja vraca jmbag koji je jedini pohranjen
	 * @throws IllegalArgumentException ako nije zadovoljeno da je u listi spremljen samo jedan izraz jmbag = "neki jmbag"
	 * @return
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery())
			throw new IllegalArgumentException();
		else {
			return list.get(0).literal;
		}
	}
	/**
	 * Metoda koja vraca trenutnu listu
	 * @return
	 */
	 public ArrayList<ConditionalExpression> getList() {
		return list;
	}
	 
	

}
