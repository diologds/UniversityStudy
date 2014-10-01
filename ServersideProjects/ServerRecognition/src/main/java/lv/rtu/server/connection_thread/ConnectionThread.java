package lv.rtu.server.connection_thread;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import lv.rtu.domain.LoginUtil;
import lv.rtu.domain.ObjectFile;
import lv.rtu.modules.ServerModule;
import lv.rtu.server.commands.Command;
import lv.rtu.server.object_handler.ObjectTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {

    private final String EXIT_COMMAND = "exit";
    private final String STREAM_COMMAND = "Stream";
    private final String LOGIN = "Login";

    //Accepted client socket
    private Socket clientSocket;

    // Tread Constructor
    public ConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // Tread execution body
    public void run() {

        //Dependency injector
        Injector injector = Guice.createInjector(new ServerModule());

        // Stream objects to store streamed data
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;

        System.out.println("New Connection Established from IP : " + clientSocket.getInetAddress());

        try {
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            boolean connection = true;
            ObjectFile message = null;
            while (connection) {
                ObjectFile objectFile = injector.getInstance(ObjectTransfer.class).receiveFile(inStream);
                if (!objectFile.getMessage().equals(EXIT_COMMAND)) {
                    if (objectFile.getMessage().contains(LOGIN)) {
                        message = injector.getInstance(Key.get(Command.class, Names.named("Login"))).executeCommand(objectFile);
                    } else if (LoginUtil.userMap.containsKey(clientSocket.getInetAddress().toString())) {
                        if (objectFile.getMessage().contains(STREAM_COMMAND)) {
                            injector.getInstance(ProcessStream.class).processStream(objectFile, outStream);
                        } else {
                            System.out.println(objectFile.toString());
                            message = injector.getInstance(ProcessConnectionData.class).objectAnalysis(objectFile);
                        }
                    } else {
                        outStream.writeObject(new ObjectFile("Please log in"));
                    }

                    outStream.writeObject(message);

                } else {
                    connection = false;
                    inStream.close();
                    outStream.close();
                    System.out.println("Connection closed for IP : " + clientSocket.getInetAddress());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
