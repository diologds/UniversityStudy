package lv.evolutiongaming.Field;

public class Field {

	private final int FIELD_SIZE = 3;
	private final char field[][] = new char[FIELD_SIZE][FIELD_SIZE];
	private final char emptySimbol = '*';

	public void generateField() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				field[i][j] = emptySimbol;
			}
		}
	}

	public boolean placeToken(int cordX, int cordY, char token) {
		if (field[cordX][cordY] == emptySimbol) {
			field[cordX][cordY] = token;
			return true;
		}
		return false;
	}

	public char getFieldEliment(int i, int j) {
		return field[i][j];
	}

	public int getElimentAmountInRow(int row) {
		int counter = 0;
		if (row >= 1 && row <= FIELD_SIZE)
			for (int i = 0; i < FIELD_SIZE; i++) {
				if (field[row][i] != emptySimbol)
					counter++;
			}
		return counter;
	}

	public int getElimentAmountInColumn(int column) {
		int counter = 0;
		if (column >= 1 && column <= FIELD_SIZE)
			for (int i = 0; i < FIELD_SIZE; i++) {
				if (field[i][column] != emptySimbol)
					counter++;
			}
		return counter;
	}

	public int getElimentAmountInMainDiagonal() {
		int counter = 0;
		for (int i = 0; i < FIELD_SIZE; i++) {
			if (field[i][i] != emptySimbol)
				counter++;
		}
		return counter;
	}

	public int getElimentAmountInSubDiagonal() {
		int counter = 0;
		for (int i = 0; i < FIELD_SIZE; i++) {
			if (field[i][2 - i] != emptySimbol)
				counter++;
		}
		return counter;
	}
}
