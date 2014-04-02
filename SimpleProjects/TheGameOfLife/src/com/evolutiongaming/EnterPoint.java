package com.evolutiongaming;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public class EnterPoint {
	public static void main(String[] args) {
		final Game c = new Game(200, 200);
		c.initGame();
		c.setPreferredSize(new Dimension(200, 200));
		JFrame frame = new JFrame();

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Verdana", Font.PLAIN, 11));
		JMenuItem run = new JMenuItem("Run");
		run.setFont(new Font("Verdana", Font.PLAIN, 11));
		fileMenu.add(run);
		JMenuItem stop = new JMenuItem("Stop");
		run.setFont(new Font("Verdana", Font.PLAIN, 11));
		fileMenu.add(stop);

		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				c.gameStep();
				c.repaint();
			}
		};

		final Timer timer = new Timer(100, action);
		timer.setInitialDelay(0);

		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			}
		});

		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
			}
		});

		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);

		frame.getContentPane().add(c);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

	}
}
