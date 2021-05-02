package ispit;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;




public class DiagramDemo extends JFrame {

	public DiagramDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("diagram");
		setLocation(30, 40);
		setSize(800, 700);
		initGUI();
	}

	public void initGUI() {
		 Container cp = getContentPane();
		 ArrayList<Integer> list = new ArrayList<Integer>();
		 list.add(Integer.valueOf(2));
		 list.add(Integer.valueOf(4));
		 list.add(Integer.valueOf(2));
	        cp.add(new Diagram(list));
	        cp.setBackground(Color.WHITE);

	}
	
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(() -> {
	            DiagramDemo prozor = new DiagramDemo();
	            prozor.setVisible(true);
	        });
	}
}
