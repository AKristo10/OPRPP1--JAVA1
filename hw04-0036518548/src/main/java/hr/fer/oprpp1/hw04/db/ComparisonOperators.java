package hr.fer.oprpp1.hw04.db;
/**
 * Klasa koja modelira operatore usporedbe
 * @author Andrea
 *
 */
public class ComparisonOperators {

	public static final IComparisonOperator LESS = (s1, s2) -> {
		return s1.compareTo(s2) < 0;
	};
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) <= 0;
	};
	public static final IComparisonOperator GREATER = (s1, s2) -> {
		return s1.compareTo(s2) > 0;
	};
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) >= 0;
	};
	public static final IComparisonOperator EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) == 0;
	};
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) != 0;
	};
	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {
			if (value2.contains("*")) {
				int index = value2.indexOf("*");
				if (index == 0) {
					String str = value2.substring(1, value2.length());
					if (value1.endsWith(str))
						return true;
				} else if (index == value2.length() - 1) {
					String str = value2.substring(0, value2.length() - 1);
					if(value1.startsWith(str))
						return true;
				}
				else {
					String firstPart = value2.substring(0, index);
					String secondPart = value2.substring(index+1, value2.length());
					if(value2.endsWith(secondPart) && value2.startsWith(firstPart) && (firstPart.length() + secondPart.length() <= value1.length()))
						return true;
				}
			}
			else if(!value2.contains("*")) {
				return value1.equals(value2);
			}
			return false;
		}
	};
	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
		System.out.println(oper.satisfied("Abramovic", "B*"));
		
		IComparisonOperator o = ComparisonOperators.LESS;
		System.out.println(o.satisfied("alo", "brate"));
	}

}
