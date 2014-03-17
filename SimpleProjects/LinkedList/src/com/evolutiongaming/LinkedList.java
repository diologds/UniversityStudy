package com.evolutiongaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> {

	private Element<T> first;

	public int size() {
		int size = 0;
		if (!isEmpty()) {
			Element<T> next = first;
			while (next != null) {
				size++;
				next = next.getNext();
			}
		} else {
			return 0;
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
		} else {
			return false;
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

		Element<T> next;
		if (first.getNext() != null) {
			next = first;
		} else {
			first = null;
			return true;
		}

		while (next.getNext() != null) {
			next = next.getNext();
		}
		next = null;
		return true;
	}

	public boolean remove(T o) {

		Element<T> next;
		if (first.getNext() != null) {
			next = first;
		} else {
			first = null;
			return true;
		}

		while (next != null) {
			if (next.getData().equals(o)) {
				next = null;
				return true;
			}
			next = next.getNext();
		}
		next = null;
		return false;
	}

	public boolean remove(int index) {

		Element<T> next;
		if (first.getNext() != null) {
			next = first;
		} else {
			first = null;
			return true;
		}

		for (int i = 0; i <= index; i++) {
			if (next == null)
				return false;
			if (i == index) {
				next = null;
				return true;
			}
			next = next.getNext();
		}
		return false;
	}

	public T get(int index) {
		Element<T> next;
		if (first != null) {
			next = first;
		} else {
			return null;
		}

		for (int i = 0; i <= index; i++) {
			if (next == null)
				return null;
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
