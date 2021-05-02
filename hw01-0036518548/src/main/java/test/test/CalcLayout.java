package test.test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred koji predstavlja Layout za izradu kalkulatora.
 * 
 * @author Andrea
 *
 */
public class CalcLayout implements LayoutManager2 {

	Map<Component, RCPosition> map = new HashMap<>();
	int space;

	/**
	 * Konstruktor koji prima razmak izmedu svake celije
	 * 
	 * @param space razmak izmedu svake celije
	 */
	public CalcLayout(int space) {
		this.space = space;
	}

	/**
	 * Konstruktor koji postavlja razmak izmedu celija na 0;
	 */
	public CalcLayout() {
		this.space = 0;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		map.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		for(Component c : parent.getComponents()) {
			if(c != null) {
				if(c.getPreferredSize().height > dim.height && c.getPreferredSize().width > dim.width) {
					dim =c.getPreferredSize();
				}
			}
		}
		
		return new Dimension(dim.width, dim.height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension maxOfMin = new Dimension(0, 0);
		for (Component c : parent.getComponents()) {
			if (c != null) {
				if (c.getMinimumSize().height > maxOfMin.height && c.getMinimumSize().width > maxOfMin.width) {
					maxOfMin = c.getMinimumSize();
				}
			}
		}
		return maxOfMin;
	}

	@Override
	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();
		Dimension size = parent.getSize();
		int plusOne = 0;
		
		// totalna visina i sirina
		double widthOfWindow = size.width - (insets.left + insets.right);
		double heightOfWindow = size.height - (insets.top + insets.bottom);

		double widthOfCell = widthOfWindow / 7;
		//System.out.println(widthOfCell);
		double heightOfCell = heightOfWindow / 5;
		
      // ako sirina nije cijeli broj
        if(widthOfCell - Math.floor(widthOfCell) != 0) {
        	if(widthOfCell - Math.floor(widthOfCell) >= 0.5) {
        		widthOfCell = Math.floor(widthOfCell);
        	}
        	else {
        		widthOfCell = Math.floor(widthOfCell) - 1;
        	}
        	plusOne = 1;
        }
        //ako visina nije cijeli broj
        if(heightOfCell - Math.floor(heightOfCell) != 0) {
        	if((heightOfCell - Math.floor(heightOfCell)) >= 0.5) 
        		heightOfCell = Math.floor(heightOfCell);  	
        	else 
        		heightOfCell = Math.floor(heightOfCell) - 1;
        	plusOne = 1;
        }
        
       
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component c = parent.getComponent(i);
			RCPosition position = (RCPosition) map.get(c);
			if (position != null) {
				
				if (position.row == 1 && position.column == 1) {
					int x = (int) (insets.left + (widthOfCell * (position.column - 1)) + space);
					int y = (int) (insets.top + (heightOfCell * (position.row - 1)) + space);
					int w = (int) (widthOfCell * 5 + plusOne - 2 * space);
					
					int h = (int) (heightOfCell   + plusOne - 2 * space);
					
					//dodavanje uvijeta za ako nije preferirane velicine itd
					
					c.setBounds(x, y, w, h);
				} else {
					int x = (int) (insets.left + (widthOfCell * (position.column - 1)) + space);
					int y = (int) (insets.top + (heightOfCell * (position.row - 1)) + space);
					int w = (int) ((position.row % 2) != 0 ? widthOfCell + plusOne - 2*space 
							:  widthOfCell - 2*space);
					//System.out.println("w je " + w);
					int h = (int) ((position.column % 2) != 0 ? heightOfCell + plusOne - 2 *space
							: heightOfCell - 2 * space);
				//	System.out.println("h je "+ h);
					c.setBounds(x, y, w, h);
				}

			}
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position;
		if (!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException();
		} else if (comp == null || constraints == null) {
			throw new NullPointerException();
		}

		else if (constraints instanceof String || constraints instanceof RCPosition) {
			if (constraints instanceof String)
				position = RCPosition.parse((String) constraints);
			else
				position = (RCPosition) constraints;

			// baci iznimku ako je nedozvoljen broj retka ili stupca
			if((position.getRow() < 1 || position.getRow() > 5) || (position.getColumn() < 1 || position.getColumn() > 7)
					|| (position.getRow() == 1 && (position.getColumn() < 6 && position.getColumn() >1)) ) {
				//throw new CalcLayoutException();
			}
			if (map.containsValue(position)) {
				//throw new CalcLayoutException();
			}
			map.put(comp, position);
			// ???????????????????
			if(constraints instanceof LayoutManager2) {
				((LayoutManager2) constraints).addLayoutComponent(comp, constraints);
			}
		}

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension max = new Dimension(0, 0);
		Boolean isFirst = true;
		for (Component c : target.getComponents()) {
			max.height += c.getMaximumSize().height;
			max.width += c.getMaximumSize().width;
		}
		return max;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

}
