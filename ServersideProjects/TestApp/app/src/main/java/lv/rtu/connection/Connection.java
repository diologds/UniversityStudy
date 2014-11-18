package lv.rtu.connection;

import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import lv.rtu.audio.Converter;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;

public class Connection {
    private static Connection instance;
    private Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private volatile boolean called;

    public static synchronized Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
            Log.e(Connection.class.getName(), "Connected");
        }
        try {
            instance.setConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public synchronized Connection setConnection() throws IOException {
        InetAddress serverAddress = InetAddress.getByName("192.168.0.162");
        clientSocket = new Socket(serverAddress, 2222);
        outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inStream = new ObjectInputStream(clientSocket.getInputStream());
        return this;
    }

    public synchronized void closeConnection() throws IOException {
        outStream.close();
        inStream.close();
        clientSocket.close();
    }

    public synchronized void send(ObjectFile objectFile) throws IOException {
        outStream.writeObject(objectFile);
        outStream.flush();
    }

    public synchronized ObjectFile receive() throws IOException,
            ClassNotFoundException {
        return (ObjectFile) inStream.readObject();
    }

    public synchronized Socket getClientSocket() {
        return clientSocket;
    }

    public synchronized ObjectOutputStream getOutStream() {
        return outStream;
    }

    public synchronized ObjectInputStream getInStream() {
        return inStream;
    }
}