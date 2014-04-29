package lv.rtu.user;

import java.io.IOException;
import java.util.Random;

import lv.rtu.resource.RequestHandler;
import lv.rtu.resource.Resource;

public class Writer extends Thread implements User {

	private final UserType userType = UserType.WRITER;

	public UserType getuserType() {
		return userType;
	}

	@Override
	public void doSomthing(Resource resource) {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resource.writeToFile(this.getName() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printMessage(String text) {
		System.out.println("Writer with name : " + this.getName() + "\t &&& "
				+ text);
	}

	@Override
	public void run() {

		try {
			Thread.sleep(new Random().nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
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
			RequestHandler.returnAccess(userType);
			printMessage("Resource was returned");
		} else {
			printMessage("Resource already taken");
		}

	}

}
