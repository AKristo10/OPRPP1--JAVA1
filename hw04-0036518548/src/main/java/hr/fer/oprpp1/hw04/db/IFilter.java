package hr.fer.oprpp1.hw04.db;
/**
 * Klasa koja predstavlja filter
 * @author Andrea
 *
 */
public interface IFilter {
	/**
	 * Metoda koja prihvaca samo ako je odreden uvijet zadovoljen
	 * @param record
	 * @return
	 */
	public boolean accepts(StudentRecord record);
}
