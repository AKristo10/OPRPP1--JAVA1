package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestComparisonOperators {

	@Test
	public void testLess() {
		String name1 = "Ana";
		String name2 = "Marin";
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testLessOrEquals() {
		String name1 = "Ana";
		String name2 = "AnaBanana";
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testGreater() {
		String name1 = "Vanja";
		String name2 = "Marin";
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testGreaterOrEquals() {
		String name1 = "Vlasta";
		String name2 = "Marina";
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testEquals() {
		String name1 = "Ana";
		String name2 = "Ana";
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testNotEquals() {
		String name1 = "Ana";
		String name2 = "Marin";
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertTrue(oper.satisfied(name1, name2));
	}
	@Test
	public void testLike() {
		String name1 = "Ana";
		String name2 = "*na";
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied(name1, name2));
		String name21 = "Ana";
		String name22 = "An*";
		IComparisonOperator operator = ComparisonOperators.LIKE;
		assertTrue(operator.satisfied(name21, name22));
		
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		
		
	}
	
}
