package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
/**
 * Klasa koja filtrira odredenu listu
 * @author Andrea
 *
 */
public class QueryFilter implements IFilter {
	ArrayList<ConditionalExpression> list;
	public QueryFilter(ArrayList<ConditionalExpression> list) {
		this.list = list;
	}
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression ce : list) {
			if(!ce.getOperator().satisfied(ce.getAttribute().get(record), ce.getLiteral()))
				return false;
		}
		return true;
	}

	
}
