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

	public boolean isCellTaken(int i, int j) {
		return field[i][j] == emptySimbol;
	}

	public void placeToken(int cordX, int cordY, char token) {
		field[cordX][cordY] = token;
	}

	public char getFieldEliment(int i, int j) {
		return field[i][j];
	}

	public int getElimentAmountInRow(int row) {
		int counter = 0;
		if (row >= 0 && row < FIELD_SIZE)
			for (int i = 0; i < FIELD_SIZE; i++) {
				if (field[row][i] != emptySimbol)
					counter++;
			}
		return counter;
	}

	public boolean compareElimentsInRow(int row) {
		if (row >= 0 && row < FIELD_SIZE)
			for (int i = 0; i <= 1; i++) {
				if (field[row][i] != field[row][i + 1])
					return false;
			}
		return true;
	}

	public int getElimentAmountInColum(int colum) {
		int counter = 0;
		if (colum >= 0 && colum < FIELD_SIZE)
			for (int i = 0; i < FIELD_SIZE; i++) {
				if (field[i][colum] != emptySimbol)
					counter++;
			}
		return counter;
	}

	public boolean compareElimentsInColum(int colum) {
		if (colum >= 0 && colum < FIELD_SIZE)
			for (int i = 0; i <= 1; i++) {
				if (field[i][colum] != field[i + 1][colum])
					return false;
			}
		return true;
	}

	public int getElimentAmountInMainDiagonal() {
		int counter = 0;
		for (int i = 0; i < FIELD_SIZE; i++) {
			if (field[i][i] != emptySimbol)
				counter++;
		}
		return counter;
	}

	public boolean compareElimentsInMainDiagonal() {
		for (int i = 0; i <= 1; i++) {
			if (field[i][i] != field[i + 1][i + 1])
				return false;
		}
		return true;
	}

	public int getElimentAmountInSubDiagonal() {
		int counter = 0;
		for (int i = 0; i < FIELD_SIZE; i++) {
			if (field[i][2 - i] != emptySimbol)
				counter++;
		}
		return counter;
	}

	public boolean compareElimentsInSubDiagonal() {
		for (int i = 0; i <= 1; i++) {
			if (field[i][2 - i] != field[i + 1][1 - i])
				return false;
		}
		return true;
	}

	public boolean isAllCelsTaken() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (field[i][j] == emptySimbol)
					return false;
			}
		}
		return true;
	}

	public void printField() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
	}
}
