package lv.rtu.resource;

import java.io.IOException;

import lv.rtu.user.UserType;

public class RequestHandler {

	private static boolean trafficLight;
	private static Resource resource;

	public static void init() throws IOException {
		trafficLight = true;
		resource = new Resource();
		resource.createFile();
		resource.writeToFile("Initial text\n");
	}

	public static boolean isAvailable() {
		return trafficLight;
	}

	public static Resource getAccess(UserType userType) {
		if (isAvailable()) {
			if (userType.equals(UserType.WRITER))
				trafficLight = false;
			return resource;
		}
		return null;
	}

	public static void returnAccess(UserType userType) {
		if (!isAvailable())
			if (userType.equals(UserType.WRITER)) {
				trafficLight = true;
			}
	}

	public static void close() {
		if (isAvailable()) {
			trafficLight = false;
			resource.deleteFile();
		}
	}

}
