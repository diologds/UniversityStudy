package lv.rtu.server.connection_thread;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import lv.rtu.domain.AuthorizationTokenGenerator;
import lv.rtu.domain.LoginInformation;
import lv.rtu.domain.LoginUtil;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.modules.ServerModule;
import lv.rtu.server.commands.Command;
import lv.rtu.server.object_handler.ObjectTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {

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
            while (connection) {
                ObjectFile objectFile = injector.getInstance(ObjectTransfer.class).receiveFile(inStream);
                String messageCommand = objectFile.getCommand();

               /* if(!LoginUtil.isValid(objectFile.getAccessToken()) && !Commands.fromValue(messageCommand).equals(Commands.LOGIN)){
                    outStream.writeObject(new ObjectFile("Please Login"));
                    continue;
                }*/

                switch(Commands.fromValue(messageCommand)){
                    case LOGIN:{
                        ObjectFile message = injector.getInstance(Key.get(Command.class, Names.named("Login"))).executeCommand(objectFile);
                        String token = AuthorizationTokenGenerator.nextToken();
                        System.out.print(token);
                        message.setAccessToken(token);
                        LoginUtil.addUser(clientSocket.getInetAddress().toString(), new LoginInformation(token));
                        outStream.writeObject(message);
                    } break;
                    case GENERAL:{
                        ObjectFile message = injector.getInstance(ProcessConnectionData.class).objectAnalysis(objectFile);
                        outStream.writeObject(message);
                    } break;
                    case STREAM:{
                        injector.getInstance(ProcessStream.class).processStream(objectFile, outStream);
                        outStream.writeObject("Streaming thread started");
                    } break;
                    case EXIT:{
                        connection = false;
                        inStream.close();
                        outStream.close();
                        System.out.println("Connection closed for IP : " + clientSocket.getInetAddress());
                    } break;
                    default:{
                        outStream.writeObject("Unknown command");
                    }
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
