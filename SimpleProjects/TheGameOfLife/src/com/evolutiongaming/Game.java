package com.evolutiongaming;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Game extends JPanel {

	private static final long serialVersionUID = -8843548711947421667L;
	private final Field field;
	private final int[][] grid;
	private final int gridModifier = 4;

	private final Map<Boolean, Strategy> stratygy = new HashMap<Boolean, Strategy>() {
		private static final long serialVersionUID = 1L;

		{
			put(false, new DeadStrategy());
			put(true, new LiveStrategy());
		}
	};

	public Game(int x, int y) {
		field = new Field();
		this.grid = new int[x / gridModifier][y / gridModifier];
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX() / 4;
				int y = e.getY() / 4;
				field.addSquare(new Square(y, x), true);
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

	}

	public void initGame() {

		field.addSquare(new Square(2, 1), true);
		field.addSquare(new Square(3, 2), true);
		field.addSquare(new Square(1, 3), true);
		field.addSquare(new Square(2, 3), true);
		field.addSquare(new Square(3, 3), true);

		field.addSquare(new Square(10, 10), true);
		field.addSquare(new Square(9, 11), true);
		field.addSquare(new Square(8, 12), true);
		field.addSquare(new Square(9, 13), true);
		field.addSquare(new Square(10, 14), true);
		field.addSquare(new Square(11, 13), true);
		field.addSquare(new Square(12, 12), true);
		field.addSquare(new Square(11, 11), true);
		field.addSquare(new Square(10, 12), true);
		field.addSquare(new Square(10, 13), true);
	}

	public void gameStep() {
		Square[] fieldData = field.getSuqares().toArray(new Square[0]);
		for (int i = 0; i < fieldData.length; i++) {
			Square square = fieldData[i];
			field.setSquareFutureValue(square,
					stratygy.get(field.getSquareCurrentValue(square))
							.nextStatus(field.colculateLiveNeighbours(square)));
		}
		field.changeCellValueToFuture();
		field.clearField();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color gColor = g.getColor();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (field.getSquareCurrentValue(new Square(i, j))) {
					g.setColor(Color.black);
					g.fillRect(j * gridModifier, i * gridModifier,
							gridModifier, gridModifier);
				}
			}
		}
		g.setColor(gColor);
	}
}
