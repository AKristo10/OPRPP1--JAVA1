package hr.fer.oprpp1.hw04.db;
/**
 * Klasa koja ima 3 finalne staticke varijable FIRST_NAME, LAST_NAME, JMBAG koje poprijamaju vrijednsoti 
 * atributa FIRST_NAME, LAST_NAME I JMBAG od {@link StudentRecord}
 * @author Andrea
 *
 */
public class FieldValueGetters {
	final static IFieldValueGetter FIRST_NAME = (StudentRecord sr) -> sr.getFirstName();
	final static IFieldValueGetter LAST_NAME = (StudentRecord sr) -> sr.getLastName();
	final static IFieldValueGetter JMBAG = (StudentRecord sr) -> sr.getJmbag();
}
