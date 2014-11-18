package lv.rtu.factories;

import lv.rtu.server.connection_thread.ConnectionThread;

import java.net.Socket;

public class TreadFactory {

    public ConnectionThread getTread(Socket socket){
        return new ConnectionThread(socket);
    }

}
