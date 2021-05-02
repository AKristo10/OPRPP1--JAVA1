package hr.fer.oprpp1.hw04.db;

import java.util.*;
/**
 * Klasa koja predstavlja bazu podataka svih studenata
 * @author Andrea
 *
 */
public class StudentDatabase {
	List<String> rows;
	List<StudentRecord> students;
	Map<String, StudentRecord> studentMap = new LinkedHashMap<String, StudentRecord>();
	/**
	 * Konstruktor koji prima parametar koji predstavlja privatnu varijablu klase
	 * @param rows
	 */
	public StudentDatabase(List<String> rows) {
		students = new ArrayList<StudentRecord>();
		this.rows = rows;
		for (String s : rows) {
			String[] attributes = s.split("\\t");
			String jmbag = attributes[0];
			String lastName = attributes[1];
			String firstName = attributes[2];
			int finalGrade = Integer.valueOf(attributes[3]);
			if (studentMap.containsKey(jmbag)) {
				System.err.println("Ne mogu dva studenta imati isti jmbag");
				System.exit(1);
			}
			else if (finalGrade < 1 || finalGrade > 5) {
				System.err.println("Ocjena nije u zadanoj domeni");
				System.exit(1);
			}
			StudentRecord student = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			students.add(student);
			studentMap.put(jmbag, student);
		}
	}
	/**
	 * Metoda koja za zadani jmbag vrati studenta s tim jmbag-om.
	 * @param jmbag
	 * @return <code>null</code>a ako student s tim jmbag-om ne postoji inace {@link StudentRecord}
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (studentMap.containsKey(jmbag))
			return studentMap.get(jmbag);
		else
			return null;
	}
	/**
	 * Metoda koja filtrira listu studenata u ovisnosti o filteru
	 * @param filter
	 * @return
	 */
	public ArrayList<StudentRecord> filter(IFilter filter) {
		ArrayList<StudentRecord> tmp = new ArrayList<StudentRecord>();
		for(StudentRecord student : students) {
			if(filter.accepts(student)) {
				tmp.add(student);
			}
		}
		return tmp;
	}
}
