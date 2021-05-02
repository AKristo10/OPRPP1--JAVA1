package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestFieldValueGetters {
	@Test
	public void testFirstName() {
		StudentRecord student = new StudentRecord("0036518548", "Ivic", "Ivo", 5);
		assertTrue(FieldValueGetters.FIRST_NAME.get(student).equals("Ivo"));
	}
	@Test
	public void testLastName() {
		StudentRecord student = new StudentRecord("0036518548", "Ivic", "Ivo", 5);
		assertTrue(FieldValueGetters.LAST_NAME.get(student).equals("Ivic"));
	}
	@Test
	public void testJMBAG() {
		StudentRecord student = new StudentRecord("0036518548", "Ivic", "Ivo", 5);
		assertTrue(FieldValueGetters.JMBAG.get(student).equals("0036518548"));
	}
}
