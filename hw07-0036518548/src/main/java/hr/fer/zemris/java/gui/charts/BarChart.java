package hr.fer.zemris.java.gui.charts;

import java.util.List;
/**
 * Razred koji predstavlja komponenete dijagrama.
 * @author Andrea
 *
 */
public class BarChart {

	List<XYValue> list;
	String xDescription;
	String yDescription;
	int yMin;
	int yMax;
	int yspace;

	/**
	 * Konstruktor koji prima komponente
	 * @param list
	 * @param xDescription
	 * @param yDescription
	 * @param yMin
	 * @param yMax
	 * @param yspace
	 */
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int yMin, int yMax, int yspace) {
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMax = yMax;
		this.yMin = yMin;
		this.yspace = yspace;
		for (XYValue y : list) {
			if (y.getY() < yMin) {
				throw new IllegalArgumentException();
			}
		}
	}
	public List<XYValue> getList() {
		return list;
	}
	public String getxDescription() {
		return xDescription;
	}
	public String getyDescription() {
		return yDescription;
	}
	public int getyMax() {
		return yMax;
	}
	public int getyMin() {
		return yMin;
	}
	public int getYspace() {
		return yspace;
	}
	
}
