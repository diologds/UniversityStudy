package lv.evolutiongaming.Player;

import lv.evolutiongaming.Field.Field;

public class HumanPlayer implements Player {

	private char playerSimbol;

	public HumanPlayer(char playerSimbol) {
		this.playerSimbol = playerSimbol;
	}

	@Override
	public void makeMove(Field field) {

	}

	public char getPlayerSimbol() {
		return playerSimbol;
	}

	public void setPlayerSimbol(char playerSimbol) {
		this.playerSimbol = playerSimbol;
	}

}
