package com.evolutiongaming;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Field {

	private Map<Square, Cell> field = new HashMap<Square, Cell>();

	public void addSquare(Square square) {
		field.put(square, new Cell());
	}

	public boolean existSquare(Square square) {
		return field.containsKey(square);
	}

	public void addSquare(Square square, boolean status) {
		field.put(square, new Cell(status));
	}

	public boolean getSquareCurrentValue(Square square) {
		if (existSquare(square))
			return field.get(square).getCurrentStatus();
		else
			return false;
	}

	public boolean getSquareFutureValue(Square square) {
		if (existSquare(square))
			return field.get(square).getFutureStatus();
		else
			return false;
	}

	public void setSquareCurrentValue(Square square, boolean status) {
		field.get(square).setCurrentStatus(status);
	}

	public void setSquareFutureValue(Square square, boolean status) {
		field.get(square).setFutureStatus(status);
	}

	public Set<Square> getSuqares() {
		Square[] fieldData = (Square[]) field.keySet().toArray(new Square[0]);
		for (int i = 0; i < fieldData.length; i++) {
			Square square = fieldData[i];
			colculateNeighbours(square);
		}

		return field.keySet();
	}

	public void colculateNeighbours(Square square) {
		int x = square.getX();
		int y = square.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (!existSquare(new Square(i, j)))
					addSquare(new Square(i, j));
			}
		}
	}

	public int colculateLiveNeighbours(Square square) {
		int x = square.getX();
		int y = square.getY();
		int counter = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (!existSquare(new Square(i, j)))
					addSquare(new Square(i, j));
				if (getSquareCurrentValue(new Square(i, j)))
					counter++;
			}
		}
		if (getSquareCurrentValue(square))
			counter--;
		return counter;
	}

	public void changeCellValueToFuture() {
		for (Square square : field.keySet()) {
			field.get(square).setCurrentStatus(
					field.get(square).getFutureStatus());
		}
	}

	public void clearField() {
		Square[] fieldData = (Square[]) field.keySet().toArray(new Square[0]);
		for (int i = 0; i < fieldData.length; i++) {
			Square square = fieldData[i];
			if (!field.get(square).getCurrentStatus())
				field.remove(square);
		}
	}
}
