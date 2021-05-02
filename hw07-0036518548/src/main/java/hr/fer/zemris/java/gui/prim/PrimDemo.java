package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
/**
 * Razred za prikaz ekrana za generiranje prim brojeva.
 * @author Andrea
 *
 */
public class PrimDemo extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setSize(500, 200);
		setLocation(500, 500);
		setTitle("PrimDemo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
	}
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new GridLayout(1, 2));
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JScrollPane scrollPanelList1 = new JScrollPane(list1);
		JScrollPane scrollPanelList2 = new JScrollPane(list2);
		center.add(scrollPanelList1);
		center.add(scrollPanelList2);
		
		cp.add(center);
		
		JButton next = new JButton("Sljedeći");
		cp.add(next, BorderLayout.PAGE_END);
		
		next.addActionListener(e -> model.next());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));

	}

}
