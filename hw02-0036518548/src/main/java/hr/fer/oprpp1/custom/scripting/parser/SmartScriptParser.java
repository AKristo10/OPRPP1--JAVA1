package hr.fer.oprpp1.custom.scripting.parser;
import hr.fer.oprpp1.hw02.prob1.LexerException;

import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class SmartScriptParser {

	DocumentNode docNode;
	Lexer lexer;
	ObjectStack stack;
	ForLoopNode fln;
	int numberOfForLoop = 0;
 /**
  * Konstruktor koji prima text koji ce parsirati
  * @param text
  */
	public SmartScriptParser(String text) {
		docNode = new DocumentNode();
		stack = new ObjectStack();
		stack.push(docNode);
		lexer = new Lexer(text);
		
		runParser();
	}

/**
 * Metoda koja parsira String
 * @throws SmartScriptParser u slucaju bilo kakve greske
 */
	public void runParser() {
		try {
		while (lexer.NextToken().getType() != TokenType.EOF) {
			if (lexer.getToken().getType() == TokenType.TAG) {
				lexer.setState(LexerState.TAG);
				if (lexer.getToken().getValue().equals("FOR")) {
					int counter = 0;
					ElementVariable variable = null;
					Element start = null;
					Element step = null;
					Element end = null;
					if (counter == 0 && lexer.NextToken().getType() == TokenType.VARIABLE) {
						variable = new ElementVariable(lexer.getToken().getValue().toString());
						counter++;
					} else
						throw new SmartScriptParserException();

					while (lexer.NextToken().getType() != TokenType.ENDTAG && lexer.getToken().getType() != TokenType.EOF) {

						if (lexer.getToken().getType() == TokenType.DOUBLE) {
							if (counter == 1)
								start = new ElementConstantDouble(
										Double.valueOf(lexer.getToken().getValue().toString()));
							else if (counter == 2)
								end = new ElementConstantDouble(
										Double.valueOf(lexer.getToken().getValue().toString()));
							else if (counter == 3)
								step = new ElementConstantDouble(Double.valueOf(lexer.getToken().getValue().toString()));
							counter++;
						} else if (lexer.getToken().getType() == TokenType.INTEGER) {
							if (counter == 1)
								start = new ElementConstantInteger(
										Integer.valueOf(lexer.getToken().getValue().toString()));
							else if (counter == 2)
								end = new ElementConstantInteger(
										Integer.valueOf(lexer.getToken().getValue().toString()));
							else if (counter == 3)
								step = new ElementConstantInteger(
										Integer.valueOf(lexer.getToken().getValue().toString()));
							counter++;
						} else if (lexer.getToken().getType() == TokenType.STRING) {
							if (counter == 1)
								start = new ElementString(lexer.getToken().getValue().toString());
							else if (counter == 2)
								end = new ElementString(lexer.getToken().getValue().toString());
							else if (counter == 3)
								step = new ElementString(lexer.getToken().getValue().toString());
							counter++;
						} else if (lexer.getToken().getType() == TokenType.VARIABLE) {
							if (counter == 1)
								start = new ElementVariable(lexer.getToken().getValue().toString());
							else if (counter == 2)
								end = new ElementVariable(lexer.getToken().getValue().toString());
							else if (counter == 3)
								step = new ElementVariable(lexer.getToken().getValue().toString());
							counter++;
						} 
						else
							throw new SmartScriptParserException();
					}

					if ((lexer.getToken().getType() != TokenType.ENDTAG) || counter < 3 || counter > 4)
						throw new SmartScriptParserException();

					else {
						fln = new ForLoopNode(variable, start, end, step);
						if (stack.peek().getClass() == ForLoopNode.class) {
							ForLoopNode node = (ForLoopNode) stack.peek();
							node.addChildNode(fln);
						} else {
							DocumentNode node = (DocumentNode) stack.peek();
							node.addChildNode(fln);
						}
						stack.push(fln);
						lexer.setState(LexerState.BASIC);
						continue;
					}
				} else if (lexer.getToken().getValue().equals("=")) {
					LinkedListIndexedCollection list = new LinkedListIndexedCollection();
					while (lexer.NextToken().getType() != TokenType.ENDTAG) {
						if (lexer.getToken().getType() == TokenType.DOUBLE)
							list.add(new ElementConstantDouble(Double.parseDouble(lexer.getToken().getValue().toString())));
						else if (lexer.getToken().getType() == TokenType.INTEGER)
							list.add(new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString())));
						else if (lexer.getToken().getType() == TokenType.STRING)
							list.add(new ElementString(lexer.getToken().getValue().toString()));
						else if (lexer.getToken().getType() == TokenType.FUNCTION)
							list.add(new ElementFunction(lexer.getToken().getValue().toString()));
						else if (lexer.getToken().getType() == TokenType.OPERATOR)
							list.add(new ElementOperator(lexer.getToken().getValue().toString()));
						else if (lexer.getToken().getType() == TokenType.VARIABLE)
							list.add(new ElementVariable(lexer.getToken().getValue().toString()));
						else
							throw new SmartScriptParserException();
					}
					Element[] elements = new Element[list.size()];
					int i = 0;
					for (Object o : list.toArray()) {
						elements[i++] = (Element) o;
					}
					EchoNode echo = new EchoNode(elements);

					if (stack.peek().getClass() == ForLoopNode.class) {
						ForLoopNode node = (ForLoopNode) stack.peek();
						node.addChildNode(echo);
					} else {
						DocumentNode node = (DocumentNode) stack.peek();
						node.addChildNode(echo);
					}
					lexer.setState(LexerState.BASIC);
					continue;
				} else if (lexer.getToken().getValue().equals("END")) {
					stack.pop();
					if (stack.size() <= 0)
						throw new SmartScriptParserException();
					if(lexer.NextToken().getType() != TokenType.ENDTAG) {
						throw new SmartScriptParserException();
					}
					lexer.setState(LexerState.BASIC);
					}
						
				
			} else if (lexer.getToken().getType() == TokenType.TEXTNODE) {
				TextNode tn = new TextNode(lexer.getToken().getValue().toString());
				if (stack.peek().getClass() == ForLoopNode.class) {
					ForLoopNode node = (ForLoopNode) stack.peek();
					node.addChildNode(tn);
				} else {
					DocumentNode node = (DocumentNode) stack.peek();
					node.addChildNode(tn);
				}
				continue;
			} else
				throw new SmartScriptParserException();
		}
	}
		 catch(LexerException l) {
				throw new SmartScriptParserException();
			}
}


	/**
	 * vraca vrijednost Document Nodea
	 * @return
	 */
	public DocumentNode getDocNode() {
		return docNode;
	}

}