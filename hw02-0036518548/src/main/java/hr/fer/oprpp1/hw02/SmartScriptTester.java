package hr.fer.oprpp1.hw02;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
public class SmartScriptTester {


	public static void main(String[] args) {
		/*String docBody = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";*/
		String docBody ="Ovo je isto OK {$ = \"String ide\r\n"
				+ "u \\\"više\\\" \\nredaka\r\n"
				+ "ovdje a stvarno četiri\" $}\r\n"
				+ "";
		SmartScriptParser parser = null;
		try {
		parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		System.out.println("Unable to parse document!");
		System.exit(-1);
		} catch(Exception e) {
		System.out.println("If this line ever executes, you have failed this class!");
		System.exit(-1);
		}
		DocumentNode document = parser.getDocNode();
		String originalDocumentBody = document.toString();
		System.out.println("Ispisujem prvi ");
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2);
		System.out.println();
		System.out.println("Ispisujem drugi");
		System.out.println(document2);
		System.out.println(same);
	}
}
