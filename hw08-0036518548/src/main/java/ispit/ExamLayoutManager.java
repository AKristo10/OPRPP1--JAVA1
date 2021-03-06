package ispit;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;




public class ExamLayoutManager implements LayoutManager2{

	private double postotak;
	
	static RCPosition AREA1;
	static RCPosition AREA2;
	static RCPosition AREA3;
	Map<Component, RCPosition> map = new HashMap<>();
	 public ExamLayoutManager(double postotak) {
		 setPostotak(postotak);
	 }
	 
	 
	 public void setPostotak(double postotak) {
		 if(postotak < 10 && postotak > 90) {
			 throw new IllegalArgumentException("Postotak mora biti od 10 do 90!");
		 } 
		 this.postotak = postotak;
		 double postotakZakomponentu = (double)postotak / 100;
		 this.AREA1 = new RCPosition(postotakZakomponentu, 1,1);
		 this.AREA2 = new RCPosition(1 - postotakZakomponentu, postotakZakomponentu, 2);
		 this.AREA3 = new RCPosition(1- postotakZakomponentu, 1 - postotakZakomponentu, 3);
	}


	

	@Override
	public void layoutContainer(Container parent) {
		
		int postotakVisina = (int) (parent.getSize().getHeight() * postotak / 100);
		int postotakSirina = (int) (parent.getSize().getWidth() * postotak / 100);
		
		System.out.println("Visina s obzirom na postotak " + postotakVisina + " a sirina s obzirom na postotak " + postotakSirina);
		System.out.println("ukupna visina " + parent.getSize().getHeight());
		
		for (int i = 0; i < parent.getComponentCount(); i++) {
			Component c = parent.getComponent(i);
			RCPosition position = (RCPosition) map.get(c);
			if(position != null) {
				
				if(position.getOznaka() == 1) {
					System.out.println("Pravim prvi postotak visine sirine mu je " + postotakSirina + " a visine " + postotakVisina) ;
					
					c.setBounds(0,0, (int) parent.getSize().getWidth(), postotakVisina);
				}
				else if(position.getOznaka() == 2) {
					
					System.out.println("Pravim drugi postotak visine sirine mu je " + postotakSirina + " a visine " + (1 - postotakVisina)) ;
					
					c.setBounds(0, postotakVisina, postotakSirina, (int) (parent.getSize().getHeight()-postotakVisina));
				}
				else if(position.getOznaka() == 3) {
					
					System.out.println("Pravim treci   postotak visine sirine mu je " + ( parent.getSize().getWidth() - postotakSirina) + " a visine " + ( parent.getSize().getHeight()-postotakVisina)) ;
					
					c.setBounds(postotakSirina + 1, postotakVisina, (int)parent.getSize().getWidth() - postotakSirina,(int) parent.getSize().getHeight()-postotakVisina);
				}
			}		
		}
	}


	@Override
	public void addLayoutComponent(Component comp, Object constraints) {

		if(constraints instanceof RCPosition) {
			if(constraints == AREA1) {
				
				map.put(comp, AREA1);
			}
			else if (constraints == AREA2) {
				map.put(comp, AREA2);
			}
			else if(constraints == AREA3) {
				map.put(comp, AREA3);
			}
			if(constraints instanceof LayoutManager2) {
				((LayoutManager2) constraints).addLayoutComponent(comp, constraints);
			}
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}


	



}
