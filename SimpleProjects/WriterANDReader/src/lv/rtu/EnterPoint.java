package lv.rtu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lv.rtu.resource.RequestHandler;
import lv.rtu.user.Reader;
import lv.rtu.user.Writer;

public class EnterPoint {
	public static void main(String[] args) {

		try {
			RequestHandler.init();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Thread> threadlist = new ArrayList<Thread>();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Please input ammaount of readers : ");
		int readerAmount = Integer.parseInt(scanner.nextLine());

		System.out.println("Please input ammaount of writers : ");
		int writerAmount = Integer.parseInt(scanner.nextLine());

		scanner.close();

		for (int i = 0; i < readerAmount; i++) {
			threadlist.add(new Thread(new Reader()));
		}

		for (int i = 0; i < writerAmount; i++) {
			threadlist.add(new Thread(new Writer()));
		}

		for (Thread thread : threadlist) {
			thread.start();
		}

	}
}
