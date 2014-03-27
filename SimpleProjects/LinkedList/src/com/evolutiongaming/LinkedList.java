package com.evolutiongaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> {

	private Element<T> first;

	public int size() {
		int size = 0;
		if (!isEmpty()) {
			Element<T> next = first;
			while (next.getNext() != null) {
				size++;
				next = next.getNext();
			}
		}

		return size;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	public boolean contains(T o) {

		if (!isEmpty()) {
			Element<T> next = first;
			while (next != null) {
				if (next.getData().equals(o)) {
					return true;
				}
				next = next.getNext();
			}
		}

		return false;
	}

	public Iterator<T> iterator() {
		return new MyIterator<T>(this);
	}

	public boolean add(T e) {
		Element<T> next;
		if (first != null) {
			next = first;
		} else {
			first = new Element<T>(e, null);
			return true;
		}

		while (next.getNext() != null) {
			next = next.getNext();
		}
		next.setNext(new Element<T>(e, null));
		return true;
	}

	public boolean remove() {

		if (first == null)
			return false;

		Element<T> next;
		Element<T> previous;

		if (first.getNext() != null) {
			next = first;
			previous = null;
		} else {
			first = null;
			return true;
		}

		while (next.getNext() != null) {
			previous = next;
			next = next.getNext();
		}
		previous.setNext(null);

		return true;
	}

	public boolean remove(T o) {

		Element<T> next;
		Element<T> previous;

		if (first.getNext() != null) {
			next = first;
			previous = null;
		} else {
			first = null;
			return true;
		}

		while (next.getNext() != null) {
			if (next.getData().equals(o)) {
				previous.setNext(next.getNext());
				return true;
			}
			previous = next;
			next = next.getNext();
		}

		return false;
	}

	public boolean remove(int index) {

		Element<T> next;
		Element<T> previous;

		if (first.getNext() != null) {
			next = first;
			previous = null;
		} else {
			first = null;
			return true;
		}

		for (int i = 0; i <= index; i++) {
			if (next == null)
				return false;
			if (i == index) {
				previous.setNext(next.getNext());
				return true;
			}
			previous = next;
			next = next.getNext();
		}
		return false;
	}

	public void removeAll() {
		if (first != null) {
			first = null;
		}
	}

	public T get(int index) throws IndexOutOfBoundsException {
		Element<T> next;
		if (first != null) {
			next = first;
		} else {
			return null;
		}

		for (int i = 0; i <= index; i++) {
			if (next == null) {
				throw new IndexOutOfBoundsException("Index " + index
						+ " is out of bounds!");
			}
			if (i == index)
				return next.getData();
			next = next.getNext();
		}
		return null;
	}

	public T getLast() {
		Element<T> next;
		if (first != null) {
			next = first;
		} else {
			return null;
		}

		while (next.getNext() != null) {
			next = next.getNext();
		}
		return next.getData();
	}

	public T getFirst() {
		if (first != null) {
			return first.getData();
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= size(); i++) {
			builder.append("Element " + i + " :" + get(i) + "\n");
		}
		return builder.toString();
	}

	public static class MyIterator<T> implements Iterator<T> {

		private final LinkedList<T> list;
		private int current;

		MyIterator(LinkedList<T> list) {
			this.list = list;
			this.current = 0;
		}

		@Override
		public boolean hasNext() {
			return current < list.size();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return list.get(current++);
		}

		@Override
		public void remove() {

			if (!hasNext())
				throw new NoSuchElementException();
			list.remove(current);
			current--;
		}
	}
}
