package hr.fer.oprpp1.custom.test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import hr.fer.oprpp1.custom.collections.*;
public class TestArrayIndexCollection {

	@Test
	public void testMethodGet() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		assertEquals(1, array.get(0));
	}

	@Test
	public void testAddingAllCollection() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array);
		assertArrayEquals(array.toArray(), array2.toArray());
	}
	@Test
	public void testMethodToArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		Object[] result = new Object[2];
		result[0]=1;
		result[1]=2;
		assertArrayEquals(result, array.toArray());
	}
	@Test
	public void testConstructor() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(7);
		assertTrue(array != null);
	}
	@Test
	public void testConstructor2() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertTrue(array != null);
	}
	 
	@Test
	public void testMethodAdd() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		assertEquals(1, array.get(0));
	}
	@Test
	public void testThrowExceptionIfNullValue() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			array.insert(null,3);});
	}
	@Test
	public void testInsertMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		array.insert(Integer.valueOf(2), 0);
		assertEquals(2, array.get(0));
	}
	@Test
	public void testIndexOfMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		int i = array.indexOf(Integer.valueOf(1));
		assertEquals(0, i);
	}
	@Test
	public void testRemoveMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		array.remove(0);
		assertEquals(false, array.contains(Integer.valueOf(1)));
		
	}
	@Test
	public void testClearMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.clear();
		assertEquals(false, array.contains(Integer.valueOf(1)));
		assertEquals(false, array.contains(Integer.valueOf(2)));
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		assertEquals(true, array.contains(Integer.valueOf(1)));
	}
	@Test
	public void testSize() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		assertEquals(4, array.size());	
	}
	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		assertEquals(false, array.isEmpty());	
	}
	@Test
	public void testSmallArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		array.add(Integer.valueOf(1));
		array.add(Integer.valueOf(2));
		array.add(Integer.valueOf(3));
		array.add(Integer.valueOf(4));
		assertEquals(4, array.size());
	}
}
