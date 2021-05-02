package hr.fer.zemris.java.gui.layouts;

/**
 * Konstruktor koji predstavlja poziciju nekog elementa unutar CalcLayouta
 * 
 * @author Andrea
 *
 */
public class RCPosition {

	int row;
	int column;

	/**
	 * Konstruktor koji prima broj retka i broj stupca
	 * 
	 * @param row    broj retka
	 * @param column broj stupca
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Konstruktor koji vraca broj stupca
	 * 
	 * @return broj stupca
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Konstruktor koji vraca broj retka.
	 * 
	 * @return broj retka
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Metoda koja parsira string u broj retka i broj stupca i vraca novi RCPosition
	 * 
	 * @param text koji parsira
	 * @return novi RCPosition
	 */
	public static RCPosition parse(String text) {
		text = text.replaceAll("[^0-9]", " ");
		String[] array = text.split("\\s+");
		 return new RCPosition(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
	}

	public static void main(String[] args) {
		parse("3, 2");
	}

}