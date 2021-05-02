package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void forJMBAGTest() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(lines);
		
		var records1 = db.filter((record) -> { return true; });
		var records2 = db.filter((record) -> { return false; });
		
		assertEquals(db.students.size(), records1.size());
		assertEquals(0, records2.size());
	}

}