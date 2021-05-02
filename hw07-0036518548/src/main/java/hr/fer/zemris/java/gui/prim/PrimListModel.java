package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {
	
	private List<Integer> elements = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		elements.add(1);
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return elements.size();
	}
	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	public void next() {
		int lastNumber = elements.get(elements.size()-1) + 1;
		while(true) {
			boolean notPrime = false;
			for(int i = 2; i<= lastNumber / 2; ++i) {
				if(lastNumber % i == 0) {
					notPrime = true;
					break;
				}
			}
			if(!notPrime) {
				elements.add(lastNumber);
				ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size()-1, elements.size()-1);
				for(ListDataListener l: listeners) {
					l.intervalAdded(event);
				}
				break;
			}
			lastNumber++;
		}
		
	}
}
