package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class ForLoopNode extends Node{
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
 /**
  * Konstruira FOR petlju. Ubacuje ime varijable, start step i end
  * @param variable
  * @param startExpression
  * @param endExpression
  * @param stepExpression
  */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, 
			Element stepExpression) {
		this.variable=variable;
		this.endExpression= endExpression;
		this.stepExpression = stepExpression;
		this.startExpression = startExpression;
	}
	/**
	 * Vraca vrijednost varijable end
	 * @return
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	/**
	 * Vraca vrijednost varijable start
	 * @return
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	/**
	 * Vraca vrijednost varijable step
	 * @return
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	/**
	 * Vraca vrijednost varijable Variable
	 * @return
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	public static void main(String[] args) {
		
	}
}
