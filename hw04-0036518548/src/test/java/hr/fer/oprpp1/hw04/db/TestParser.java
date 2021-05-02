package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestParser {
	
	@Test
	public void testIsDirectQuery() {
		String text = "jmbag = \"0000003\"";
		QueryParser parser = new QueryParser(text);
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testgetQueriedJMBAG() {
		String text = "jmbag = \"0000003\"";
		QueryParser parser = new QueryParser(text);
		assertEquals(parser.getQueriedJMBAG(), "0000003");
	}
	
	@Test
	public void testParser() {
		String text = "firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"";
		QueryParser parser = new QueryParser(text);
		
		assertEquals(parser.list.size(), 4);
		assertEquals("A", parser.list.get(0).literal);
		assertEquals(ComparisonOperators.LIKE, parser.list.get(2).operator);
		/*for(ConditionalExpression ce : parser.list) {
			System.out.println(ce.attribute + " "+ ce.operator + " " + ce.literal);
		}*/
	}

}
