package lv.evolutiongaming.Field;

public class Field {

	private final int FIELD_SIZE = 3;
	private final char field[][] = new char[FIELD_SIZE][FIELD_SIZE];

	public void generateField() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				field[i][j] = '*';
			}
		}
	}

	public boolean placeToken(int cordX, int cordY, char token) {
		if (field[cordX][cordY] == '*') {
			field[cordX][cordY] = token;
			return true;
		}
		return false;
	}
}
