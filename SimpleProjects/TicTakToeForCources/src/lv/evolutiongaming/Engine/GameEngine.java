package lv.evolutiongaming.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lv.evolutiongaming.Field.Field;
import lv.evolutiongaming.Player.AIPlayer;
import lv.evolutiongaming.Player.HumanPlayer;
import lv.evolutiongaming.Player.Player;

public class GameEngine {

	private final Field field = new Field();
	private final List<Player> players = new ArrayList<Player>();
	private Scanner userInputScanner;

	public void startGame() {
		userInputScanner = new Scanner(System.in);
		printMenu();
		int userInput = Integer.parseInt(userInputScanner.nextLine());
		procesUserInput(userInput);
		gameProcess();
		userInputScanner.close();
	}

	public void printMenu() {
		System.out.println("Please chouse game mode : ");
		System.out.println("\t 1.PLayer vs Player");
		System.out.println("\t 2.Player vs AI");
		System.out.println("\t 3.AI vs Player");
		System.out.println("\t 4.AI vs AI");
	}

	public boolean validateUserInput(int userInput) {
		if (userInput >= 1 && userInput <= 4)
			return true;
		else
			return false;
	}

	public void procesUserInput(int userInput) {
		if (validateUserInput(userInput))
			players.removeAll(players);
		switch (userInput) {
		case 1:
			players.add(new HumanPlayer('X', userInputScanner));
			players.add(new HumanPlayer('O', userInputScanner));
			break;
		case 2:
			players.add(new HumanPlayer('X', userInputScanner));
			players.add(new AIPlayer('O'));
			break;
		case 3:
			players.add(new AIPlayer('X'));
			players.add(new HumanPlayer('O', userInputScanner));
			break;
		case 4:
			players.add(new AIPlayer('X'));
			players.add(new AIPlayer('O'));
			break;
		}
	}

	public void gameProcess() {
		field.generateField();
		endOFGame: do {
			for (Player player : players) {
				field.printField();
				player.makeMove(field);
				if (analyzeCurrentGameState()) {
					System.out.println("Player : " + player.getPlayerSimbol()
							+ " - Won");
					break endOFGame;
				}

				if (field.isAllCelsTaken()) {
					System.out.println("Game result DRAW");
					break endOFGame;
				}

			}
		} while (true);

		field.printField();

	}

	private boolean analyzeCurrentGameState() {
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
