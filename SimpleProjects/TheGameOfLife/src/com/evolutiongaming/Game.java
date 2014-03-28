package com.evolutiongaming;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private final Map<Boolean, Strategy> stratygy = new HashMap<Boolean, Strategy>() {
		{
			put(false, new DeadStrategy());
			put(true, new LiveStrategy());
		}
	};

	public Game() {
		runGame();
	}

	private void runGame() {
		Field field = new Field();
		initGame(field);

	}

	private void initGame(Field field) {
		field.addSquare(3, 3, true);
		field.addSquare(3, 4, true);
		field.addSquare(3, 5, true);
	}

	private void gameStep() {

	}

}
