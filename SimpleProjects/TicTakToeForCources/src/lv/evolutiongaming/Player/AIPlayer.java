package lv.evolutiongaming.Player;

import lv.evolutiongaming.Field.Field;

public class AIPlayer implements Player {

	private char playerSimbol;

	public AIPlayer(char playerSimbol) {
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
