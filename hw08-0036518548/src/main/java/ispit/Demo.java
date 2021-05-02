package ispit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;



public class Demo extends JFrame {

	public Demo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
		
	}
	private void initGUI() {
		Container cp = getContentPane();
		ExamLayoutManager exlm = new ExamLayoutManager(20);
		cp.setLayout(exlm);
		cp.add(
		 makeLabel("Ovo je tekst za područje 1.", Color.RED),
		 ExamLayoutManager.AREA1);
		cp.add(
		 makeLabel("Područje 2.", Color.GREEN),
		 ExamLayoutManager.AREA2);
		cp.add(
		 makeLabel("Područje 3.", Color.YELLOW),
		 ExamLayoutManager.AREA3);
		}
	
	private Component makeLabel(String txt, Color col) {
		JLabel lab = new JLabel(txt);
		lab.setOpaque(true);
		lab.setBackground(col);
		return lab;
		}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Demo().setVisible(true);
		});
	}

}
