package com.evolutiongaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ListTest {

	static LinkedList<String> list;
	static String[] message = { "Hello", "World", "Me", "You", "Cat", "Dog" };

	@BeforeClass
	public static void testSetup() {

		list = new LinkedList<String>();

		for (int i = 0; i <= message.length - 1; i++)
			list.add(message[i]);
	}

	@AfterClass
	public static void testCleanup() {

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
	public void testRemove() {
		String value = list.get(list.size());
		list.remove();
		assertFalse("Element wasent deleted", list.contains(value));
	}

}
