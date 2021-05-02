package hr.fer.oprpp1.hw02.parser;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class ParserTest {
 
	@Test
	public void testExample1() {
		String docBody ="Ovo je \r\n"
				+ "sve jedan \\{$ text node\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		assertEquals(document, document2);	
	}
	
	@Test 
	public void testExample2() {
		String docBody ="Ovo je \r\n"
				+ "sve jedan \\{$ text node\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		assertEquals(document, document2);
	}
	@Test
	public void testExample3() {
		String docBody ="Ovo je \r\n"
				+ "sve jedan \\\\\\{$text node\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		assertEquals(document, document2);
	}
	@Test
	public void testExample4() {
		String docBody ="Ovo je OK ${ = \"String ide\r\n"
				+ "u više redaka\r\n"
				+ "čak tri\" $}\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		assertEquals(document, document2);
	}
	@Test
	public void testExample5() {
		String docBody ="Ovo je isto OK {$ = \"String ide\r\n"
				+ "u \\\"više\\\" \\nredaka\r\n"
				+ "ovdje a stvarno četiri\" $}\r\n"
				+ "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		assertEquals(document, document2);
	}
	
	
	
	
}
