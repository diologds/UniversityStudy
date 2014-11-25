package lv.rtu.server.connection;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RoundRobinPool;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lv.rtu.db.DataBaseFiller;
import lv.rtu.db.DatabaseTools;
import lv.rtu.external_camera.IPCameraThreadController;
import lv.rtu.maping.DataStreamMapping;
import lv.rtu.maping.IPCamMapping;
import lv.rtu.modules.ServerModule;
import lv.rtu.recognition.RecognitionEngine;
import lv.rtu.server.connection_thread.Worker;
import lv.rtu.server.network_util.Ping;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    static Logger LOGGER = Logger.getLogger(ConnectionHandler.class.getName());

    public static void main(String args[]) {

        //Dependency injector
        Injector injector = Guice.createInjector(new ServerModule());

        //Checking is database accessible or not;
        DatabaseTools tools = injector.getInstance(DatabaseTools.class);
        if (!tools.checkIsConnectionPossible()) {
            LOGGER.info("Database not found. Server requires Database data. Please check is database running" +
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
        int portNumber = 9999;

        if (args.length < 1) {
            LOGGER.info("Usage: java MultiThreadRecognitionServer <portNumber>\n"
                            + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        /*
         * Ping service
         */
        injector.getInstance(Ping.class).start();

        /*
         * Creating actors system
         */
        ActorSystem system = ActorSystem.create("serverActors");

        /*
         * Creating clients processing workers
         */
        ActorRef workerRouter = system.actorOf(Worker.createWorker().withRouter(new RoundRobinPool(4)), "workerRouter");

		/*
         * Open a server socket on the portNumber (default 2222). Note that we
		 * can not choose a port less than 1023 if we are not privileged users (root);
		 */
        try {
            serverSocket = new ServerSocket(portNumber);
            LOGGER.info("Server address=" + InetAddress.getLocalHost().getHostAddress());
        } catch (IOException e) {
            LOGGER.info(e);
        }

		/*
         * Create a client socket for each connection and pass it to a new client thread;
		 */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                workerRouter.tell(clientSocket, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
