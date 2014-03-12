package lv.rtu.server.connection;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.external_camera.IPCameraThreadController;
import lv.rtu.maping.IPCamMapping;
import lv.rtu.maping.Mapping;
import lv.rtu.recognition.RecognitionEngine;
import lv.rtu.server.connection_thread.ConnectionThread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;

	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 10;
	private static final ConnectionThread[] threads = new ConnectionThread[maxClientsCount];

	public static void main(String args[]) {

        DataBaseFiller.fillDB();
        RecognitionEngine.trainRecognizers();
        Mapping.mappingFromFile();
        IPCamMapping.mappingFromFile();

        IPCameraThreadController treadController = new IPCameraThreadController();
        treadController.runAllIPCameras();

        // The default port number.
		int portNumber = 2222;
		if (args.length < 1) {
			System.out
					.println("Usage: java MultiThreadChatServer <portNumber>\n"
							+ "Now using port number=" + portNumber);
		} else {
			portNumber = Integer.valueOf(args[0]).intValue();
		}

		/*
		 * Open a server socket on the portNumber (default 2222). Note that we
		 * can not choose a port less than 1023 if we are not privileged users
		 * (root).
		 */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		/*
		 * Create a client socket for each connection and pass it to a new
		 * client thread.
		 */
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++) {
					if (threads[i] == null) {
						(threads[i] = new ConnectionThread(clientSocket))
								.start();
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(
							clientSocket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
