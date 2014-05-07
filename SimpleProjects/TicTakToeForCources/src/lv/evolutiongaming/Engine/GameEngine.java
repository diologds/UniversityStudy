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

	public void startGame() {
		Scanner userInputScanner = new Scanner(System.in);
		printMenu();
		int userInput = Integer.parseInt(userInputScanner.nextLine());
		procesUserInput(userInput);
		userInputScanner.close();
	}

	public void printMenu() {
		System.out.println("Please chouse game mode : ");
		System.out.println("/t 1.PLayer vs Player");
		System.out.println("/t 2.Player vs AI");
		System.out.println("/t 3.AI vs Player");
		System.out.println("/t 4.AI vs AI");
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
			players.add(new HumanPlayer('X'));
			players.add(new HumanPlayer('O'));
			break;
		case 2:
			players.add(new HumanPlayer('X'));
			players.add(new AIPlayer('O'));
			break;
		case 3:
			players.add(new AIPlayer('X'));
			players.add(new HumanPlayer('O'));
			break;
		case 4:
			players.add(new AIPlayer('X'));
			players.add(new AIPlayer('O'));
			break;
		}
	}

	public void gameProcess() {
		do {
			for (Player player : players) {
				player.makeMove(field);
				if (analyzeCurrentGameState()) {
					break;
				}
			}
		} while (true);
	}

	private boolean analyzeCurrentGameState() {

		return false;
	}
}
