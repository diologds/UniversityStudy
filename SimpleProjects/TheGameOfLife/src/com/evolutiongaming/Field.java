package com.evolutiongaming;

import java.util.HashMap;
import java.util.Map;

public class Field {

	private final Map<Square, Cell> field = new HashMap<Square, Cell>();

	public void addSquare(int x, int y) {
		field.put(new Square(x, y), new Cell());
	}

	public void addSquare(int x, int y, boolean status) {
		field.put(new Square(x, y), new Cell(status));
	}

	public boolean getSquareCurrentValue(int x, int y) {
		return field.get(new Square(x, y)).getCurrentStatus();
	}

	public boolean getSquareFutureValue(int x, int y) {
		return field.get(new Square(x, y)).getFutureStatus();
	}

	public void setSquareCurrentValue(int x, int y, boolean status) {
		field.get(new Square(x, y)).setCurrentStatus(status);
	}

	public void setSquareFutureValue(int x, int y, boolean status) {
		field.get(new Square(x, y)).setFutureStatus(status);
	}
}
