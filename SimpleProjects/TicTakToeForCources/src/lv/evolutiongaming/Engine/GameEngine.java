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

	}

	public void printMenu() {
		System.out.println("Please chouse game mode : ");
		System.out.println("/t 1.PLayer vs Player");
		System.out.println("/t 2.Player vs AI");
		System.out.println("/t 3.AI vs AI");
	}

	public boolean validateUserInput(int userInput) {
		if (userInput >= 1 && userInput <= 3)
			return true;
		else
			return false;
	}

	public void procesUserInput(int userInput) {
		if (validateUserInput(userInput))
			players.removeAll(players);
		switch (userInput) {
		case 1:
			players.add(new HumanPlayer());
			players.add(new HumanPlayer());
			break;
		case 2:
			players.add(new HumanPlayer());
			players.add(new AIPlayer());
			break;
		case 3:
			break;
		}
	}
}
