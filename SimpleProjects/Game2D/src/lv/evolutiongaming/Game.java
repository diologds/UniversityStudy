package lv.evolutiongaming;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -2255019708656555343L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "2D Game";
	public static final double FRAMES = 60D;
	public static final double NS = 1000000000D;

	public boolean running = false;
	public int tickCount = 0;

	private final JFrame frame;

	private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private final int[] pixels = ((DataBufferInt) image.getRaster()
			.getDataBuffer()).getData();

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = NS / FRAMES;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0D;

		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if ((System.currentTimeMillis() - lastTimer) >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " , " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	private synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	private synchronized void stop() {
		running = false;
	}

	private void render() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		graphics.dispose();

		bufferStrategy.show();
	}

	private void tick() {
		tickCount++;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = i + tickCount;
		}
	}

	public static void main(String[] args) {
		new Game().start();

	}

}
