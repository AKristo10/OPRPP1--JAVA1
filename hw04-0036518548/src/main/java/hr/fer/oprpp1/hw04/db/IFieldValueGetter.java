package hr.fer.oprpp1.hw04.db;
/**
 * Sucelje koje predstavlja neki od atributa StudentRecord
 * @author Andrea
 *
 */
public interface IFieldValueGetter {
	/**
	 * Metoda koja vraca neki od atributa {@link StudentRecord}
	 * @param record
	 * @return atribut od {@link StudentRecord}
	 */
	public String get(StudentRecord record);
}
