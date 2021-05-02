package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	
	ArrayIndexedCollection children;
	boolean first = true;
	public void addChildNode(Node child) {
		if(first) {
			children = new ArrayIndexedCollection();
			first = false;
		}
		children.add(child);		
	}
	
	/**
	 * Metoda koja vraca trenutnu velicinu kolekcije
	 * @return velicinu kolekcije
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**Metoda koja za zadani index vraca Node koji se nalazi na tom mjestu
	 * @throws IllegalArgumentException ako index nije valjan
	 * @param index
	 * @return
	 */
	public Node getChild(int index) {
		if(index <0 || index >= children.size()) {
			throw new IllegalArgumentException();
		}
		else return (Node) children.get(index);
	}

	public String asText() {
		// TODO Auto-generated method stub
		return null;
	}
}
