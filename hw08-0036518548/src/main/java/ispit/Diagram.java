package ispit;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Diagram extends JComponent {
	private static final int INTERSPACE = 25;
	ArrayList<Integer> prirodni = new ArrayList<>();
	int suma;
	public Diagram(ArrayList<Integer> prirodni) {
		this.prirodni = prirodni;
		suma = 0;
		for(int i : prirodni) {
			suma += i;
		}
	}
	
	 @Override
	    public void paintComponent(Graphics g) {
		 Graphics2D g2 = (Graphics2D) g;
		 FontMetrics fm = g2.getFontMetrics();
	       
	        
		 int sirina = (int) (getParent().getSize().getWidth() / prirodni.size());
		 g2.setColor(new Color(0xF1784B));
		 int pomak = 0;
		 for( int i : prirodni) {
			
			 double postotak = i / (double) suma;
			 
			 g2.fillRect(pomak, (int) (getHeight() - postotak * getHeight()), sirina, (int) (postotak * (getHeight())) );
			 pomak += 1 + sirina;
		 }
	 }
}
