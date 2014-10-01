package lv.rtu.network_utils;

import com.google.inject.Singleton;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class Ping extends Thread {

    private final AtomicReference<Boolean> msg = new AtomicReference<Boolean>();

    public Boolean getServerStatus(){
       return msg.get();
    }

    public void run() {
        try {
            DatagramSocket dSocket = new DatagramSocket(5556);
            dSocket.setSoTimeout(100);

            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"), 5555);
                dSocket.send(packet);
                try {
                    dSocket.receive(packet);
                    msg.set(true);
                } catch (SocketTimeoutException e) {
                    msg.set(false);
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
