package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node {

	

	public DocumentNode() {
	
	}
	/**
	 * Ispisuje sadrzaj cijelog stabla
	 */
	@Override
	public String toString() {
		String result = new String();
		for (int i = 0; i < numberOfChildren(); i++) {
			if (getChild(i) instanceof ForLoopNode) {
				result += printForLoopNode((ForLoopNode) getChild(i));
			}
			else
				result += getChild(i).asText();
		}
		return result;
	}
/**
 * Ispisuje FOR petlju i djecu FOR petlje
 * @param node
 * @return
 */
	public String printForLoopNode(ForLoopNode node) {
		String result = new String();
		result += "{$ FOR ";
		result += node.getVariable().asText() + " " + node.getStartExpression().asText() + " " + node.getEndExpression().asText() + " ";
		if(node.getStepExpression() != null)
			result += node.getStepExpression().asText() + " ";
		
		result += "$}";
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i) instanceof ForLoopNode) {
				result += printForLoopNode((ForLoopNode) node.getChild(i));
			} else {
				result += node.getChild(i).asText();
			}
		}
		
		return result + "{$END$}";
	}
	/**
	 * Provjeravala jesu li dva objekta jednaka. Jednaki su ako i samo ako im je toString() metoda jednaka
	 * odnosno daje isti String
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DocumentNode) {
			if(this.toString().equals(obj.toString()))
				return true;
		}
			
		return false;
	}
}
