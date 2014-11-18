package lv.rtu.connection;

import android.util.Log;
import java.io.IOException;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;

public class Sender implements Runnable {

    private ObjectFile toSend;
    private ObjectFile received;

    public synchronized ObjectFile getToSend() {
        return toSend;
    }

    public synchronized void setToSend(ObjectFile toSend) {
        this.toSend = toSend;
    }

    public synchronized ObjectFile getReceived() {
        return received;
    }

    public synchronized void setReceived(ObjectFile received) {
        this.received = received;
    }

    @Override
    public void run() {
        Log.e(Sender.class.getName(), "Send method execution");
        Connection connection = Connection.getInstance();
        try {
            connection.send(getToSend());
            Log.e(Sender.class.getName(), "Sent");
            setReceived(connection.receive());
            Log.e(Sender.class.getName(), "Received");
            connection.send(new ObjectFile(Commands.EXIT.getValue(), "Exit"));
            connection.closeConnection();
            Log.e(Sender.class.getName(), "Disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}