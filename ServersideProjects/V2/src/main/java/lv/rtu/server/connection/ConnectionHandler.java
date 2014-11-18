package lv.rtu.server.connection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lv.rtu.db.DataBaseFiller;
import lv.rtu.db.DatabaseTools;
import lv.rtu.external_camera.IPCameraThreadController;
import lv.rtu.factories.TreadFactory;
import lv.rtu.maping.DataStreamMapping;
import lv.rtu.maping.IPCamMapping;
import lv.rtu.modules.ServerModule;
import lv.rtu.recognition.RecognitionEngine;
import lv.rtu.server.network_util.Ping;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    public static void main(String args[]) {

        //Dependency injector
        Injector injector = Guice.createInjector(new ServerModule());

        //Checking is database accessible or not;
        DatabaseTools tools = injector.getInstance(DatabaseTools.class);
        if (!tools.checkIsConnectionPossible()) {
            System.out.println("Database not found. Server requires Database data. Please check is database running" +
                    " , or database profile is correct.");
            return;
        }

        // Filling database with testing data;
        DataBaseFiller.fillDB();

        // Training recognizer audio and image recognizer;
        RecognitionEngine.trainRecognizers();

        // Loading server-client mapping;
        DataStreamMapping.mappingFromFile();

        // Loading ip camera mapping;
        IPCamMapping.mappingFromFile();

        // Starting new threads for IP cameras;
        IPCameraThreadController treadController = new IPCameraThreadController();
        treadController.runAllIPCameras();

        // The default port number.
        int portNumber = 2222;

        if (args.length < 1) {
            System.out
                    .println("Usage: java MultiThreadRecognitionServer <portNumber>\n"
                            + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        /*
         * Ping service
         */
        injector.getInstance(Ping.class).start();

		/*
         * Open a server socket on the portNumber (default 2222). Note that we
		 * can not choose a port less than 1023 if we are not privileged users (root);
		 */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

		/*
		 * Create a client socket for each connection and pass it to a new client thread;
		 */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                injector.getInstance(TreadFactory.class).getTread(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
