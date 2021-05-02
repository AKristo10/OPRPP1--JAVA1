package hr.fer.oprpp1.hw04.db;
/**
 * Sucelje koje modelira akcije koje obavlja odredeni operator
 * @author Andrea
 *
 */
public interface IComparisonOperator {
	/**
	 * Radi odredenu akciju nad parametrima u ovisnosti o operatoru
	 * @param value1
	 * @param value2
	 * @return
	 */
	public boolean satisfied(String value1, String value2);
}
