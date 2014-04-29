package lv.rtu.user;

import java.io.IOException;
import java.util.Random;

import lv.rtu.resource.RequestHandler;
import lv.rtu.resource.Resource;

public class Reader extends Thread implements User {

	private final UserType userType = UserType.READER;

	public UserType getuserType() {
		return userType;
	}

	@Override
	public void doSomthing(Resource resource) {
		try {
			resource.readFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printMessage(String text) {
		System.out.println("Reader with name : " + this.getName() + "\t ### "
				+ text);
	}

	@Override
	public void run() {

		try {
			Thread.sleep(new Random().nextInt(100));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (!RequestHandler.isAvailable()) {
			try {
				printMessage("Waiting for resource");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Resource resource;

		if (!((resource = RequestHandler.getAccess(userType)) == null)) {
			printMessage("Resource acquired");
			doSomthing(resource);
			printMessage("Resource returned");
		} else {
			printMessage("Resource already taken");
		}

	}

}
