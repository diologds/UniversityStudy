package lv.rtu.connection.sender;

import com.google.inject.Singleton;
import lv.rtu.domain.ObjectFile;

import java.io.*;
import java.net.Socket;

@Singleton
public class Connector {

    private Socket clientSocket;
    private DataOutputStream os;
    private DataInputStream is;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private volatile boolean called;

    public void setConnection() throws IOException {
        if (!called) {
            clientSocket = new Socket("localhost", 2222);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());

            outStream = new ObjectOutputStream(
                    clientSocket.getOutputStream());

            inStream = new ObjectInputStream(
                    clientSocket.getInputStream());
            called = true;
        }
    }

    public synchronized void send(ObjectFile objectFile) throws IOException {
        outStream.writeObject(objectFile);
        outStream.flush();
    }

    public synchronized ObjectFile recive() throws IOException, ClassNotFoundException {
        return (ObjectFile) inStream.readObject();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectOutputStream getOutStream() {
        return outStream;
    }

    public ObjectInputStream getInStream() {
        return inStream;
    }


}
