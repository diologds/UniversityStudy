package com.evolutiongaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListTest {

	LinkedList<String> list;
	String[] message = { "Hello", "World", "Me", "You", "Cat", "Dog" };

	@Before
	public void testSetup() {

		list = new LinkedList<String>();

		for (int i = 0; i <= message.length - 1; i++)
			list.add(message[i]);
	}

	@After
	public void testCleanup() {
		list.removeAll();
	}

	@Test
	public void testEmpty() {
		LinkedList<String> list = new LinkedList<String>();
		assertTrue("List is not empty ", list.isEmpty());
	}

	@Test
	public void testAdd() {
		assertEquals(list.getFirst(), message[0]);
	}

	@Test
	public void testFirst() {
		assertEquals(list.getFirst(), message[0]);
	}

	@Test
	public void testLast() {
		assertEquals(list.getLast(), message[message.length - 1]);
	}

	@Test
	public void testContains() {
		assertTrue("Element not resent in the list", list.contains(message[3]));
	}

	@Test
	public void testIterator() {
		Iterator<String> iterator = list.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			assertEquals(iterator.next(), message[counter]);
			counter++;
		}
	}

	@Test
	public void testRemoveLast() {
		String value = list.get(list.size());
		list.remove();
		assertFalse("Element was not deleted", list.contains(value));
	}

	@Test
	public void testRemoveObject() {
		list.remove(message[3]);
		assertFalse("Element was not deleted", list.contains(message[3]));
	}

	@Test
	public void testRemoveIndex() {
		list.remove(3);
		assertFalse("Element was not deleted", list.contains(message[3]));
	}

	@Test
	public void testRemoveAll() {
		list.removeAll();
		for (int i = 0; i < message.length; i++)
			assertFalse("Element was not deleted", list.contains(message[i]));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testOutOfBound() {
		list.get(list.size() + 3);
	}

}
