package hr.fer.oprpp1.hw04.db;
/**
 * Klasa koja modelira studenta pohranjenog u bazi podataka
 * @author Andrea
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade; 
	
	/**
	 * Konstrukor koji prima parametre koji predstvaljaju privatne varijable klase
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	/**
	 * Metoda koja vraca finalnu ocjenu
	 * @return finalna ocjena
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	/**
	 * Metoda koja vraca ime studenta
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Metoda koja vraca jmbag studenta
	 * @return jmbag studenta
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * Metoda koja vraca prezime studenta
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
}
