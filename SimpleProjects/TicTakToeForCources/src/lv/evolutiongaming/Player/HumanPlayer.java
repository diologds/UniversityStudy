package lv.evolutiongaming.Player;

import java.util.Scanner;

import lv.evolutiongaming.Field.Field;

public class HumanPlayer implements Player {

	private char playerSimbol;
	private final Scanner userInputScanner;

	public HumanPlayer(char playerSimbol, Scanner userInputScanner) {
		this.playerSimbol = playerSimbol;
		this.userInputScanner = userInputScanner;
	}

	@Override
	public void makeMove(Field field) {
		String[] input;
		do {
			System.out.println("Please chouse cell: (example 1 1)");
			input = userInputScanner.nextLine().split("\\s+");
		} while (!field.isCellTaken(Integer.parseInt(input[0]),
				Integer.parseInt(input[1])));

		field.placeToken(Integer.parseInt(input[0]),
				Integer.parseInt(input[1]), getPlayerSimbol());
	}

	@Override
	public char getPlayerSimbol() {
		return playerSimbol;
	}

	public void setPlayerSimbol(char playerSimbol) {
		this.playerSimbol = playerSimbol;
	}

}
