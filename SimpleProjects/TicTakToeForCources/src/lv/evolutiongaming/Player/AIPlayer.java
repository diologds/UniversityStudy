package lv.evolutiongaming.Player;

import java.util.Random;

import lv.evolutiongaming.Field.Field;

public class AIPlayer implements Player {

	private char playerSimbol;

	public AIPlayer(char playerSimbol) {
		this.playerSimbol = playerSimbol;
	}

	@Override
	public void makeMove(Field field) {
		int[] move = nextMove(playerSimbol, field);
		field.placeToken(move[0], move[1], playerSimbol);
	}

	@Override
	public char getPlayerSimbol() {
		return playerSimbol;
	}

	public void setPlayerSimbol(char playerSimbol) {
		this.playerSimbol = playerSimbol;
	}

	public int[] nextWinningMove(char token, Field field) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (field.getFieldEliment(i, j) == '*') {
					field.placeToken(i, j, token);
					boolean win = isWin(field);
					field.placeToken(i, j, '*');
					if (win)
						return new int[] { i, j };
				}
		return null;
	}

	public int[] nextMove(char token, Field field) {
		int winMove[] = nextWinningMove(token, field);
		if (winMove != null)
			return winMove;

		if ((new Random().nextInt(100) + 1) < 50) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (field.getFieldEliment(i, j) == '*') {
						field.placeToken(i, j, token);
						int[] step = nextWinningMove(token == 'X' ? 'O' : 'X',
								field);
						field.placeToken(i, j, '*');
						if (step != null)
							return step;
					}
		}

		if (field.getFieldEliment(1, 1) == '*')
			return new int[] { 1, 1 };

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (field.getFieldEliment(i, j) == '*')
					return new int[] { i, j };
		return null;
	}

	public boolean isWin(Field field) {
		for (int i = 0; i < 3; i++) {

			if (field.getElimentAmountInRow(i) == 3
					&& field.compareElimentsInRow(i))
				return true;

			if (field.getElimentAmountInColum(i) == 3
					&& field.compareElimentsInColum(i))
				return true;
		}

		if (field.getElimentAmountInMainDiagonal() == 3
				&& field.compareElimentsInMainDiagonal())
			return true;

		if (field.getElimentAmountInSubDiagonal() == 3
				&& field.compareElimentsInSubDiagonal())
			return true;

		return false;
	}
}
