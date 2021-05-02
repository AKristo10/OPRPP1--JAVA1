package hr.fer.oprpp1.hw04.db;
/**
 * Klasa koja  modelira izraze koji ce se usporediti koristenjem {@link IFieldValueGetter}
 * @author Andrea
 *
 */
public class ConditionalExpression {
	IFieldValueGetter attribute ;
	String literal;
	IComparisonOperator operator;
	/**
	 * Konstruktor koje prima paramtere koji predstvaljaju privatne vrijednosti klase
	 * @param attribute
	 * @param literal
	 * @param operator
	 */
	public ConditionalExpression(IFieldValueGetter attribute, String literal, IComparisonOperator operator) {
		this.attribute = attribute;
		this.literal=literal;
		this.operator = operator;
	}
	
	/**
	 * Metoda koja vraca vrijednost atributa
	 * @return vrijednost atributa
	 */
	public IFieldValueGetter getAttribute() {
		return attribute;
	}
	/**
	 * Metoda koja vraca vrijednost literala
	 * @return
	 */
	public String getLiteral() {
		return literal;
	}
	/**
	 * Metoda koja vraca vrijednost operatora
	 * @return vrijednost operatora
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}
}
