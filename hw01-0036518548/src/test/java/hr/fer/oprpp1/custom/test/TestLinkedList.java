package hr.fer.oprpp1.custom.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;



public class TestLinkedList {
	@Test
	public void testMethodGet() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		assertEquals(1, list.get(0));
		assertEquals(2, list.get(1));
		assertEquals(3, list.get(2));
		assertEquals(4, list.get(3));
	}
	@Test
	public void testAddingAllCollection() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		LinkedListIndexedCollection list2 = new LinkedListIndexedCollection(list);
		assertArrayEquals(list.toArray(), list2.toArray());
	}
	@Test
	public void testConstructor() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertTrue(list != null);
	}
	@Test
	public void testMethodClear() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.clear();
		assertEquals(0, list.size());
	}
	@Test
	public void testMethodToArray() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		Object[] result = new Object[2];
		result[0]=1;
		result[1]=2;
		assertArrayEquals(result, list.toArray());
	}
	@Test
	public void testMethodAdd() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		assertEquals(1, list.get(0));
	}
	@Test
	public void testThrowExceptionIfNullValue() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			list.insert(null,3);});
	}
	
	@Test
	public void testInsertMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.insert(Integer.valueOf(2), 0);
		assertEquals(2, list.get(0));
	}
	@Test
	public void testRemoveMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.remove(0);
		assertEquals(false, list.contains(Integer.valueOf(1)));
		
	}
	@Test
	public void testContains() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		assertEquals(true, list.contains(Integer.valueOf(1)));
	}
	@Test
	public void testIndexOfMehtod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		assertEquals(0, list.indexOf(Integer.valueOf(1)));
	}
	@Test
	public void testSize() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		assertEquals(4, list.size());	
	}
	@Test
	public void testIsEmpty() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		assertEquals(false, list.isEmpty());	
	}
	@Test
	public void testSmallArray() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		assertEquals(4, list.size());
	}
}
