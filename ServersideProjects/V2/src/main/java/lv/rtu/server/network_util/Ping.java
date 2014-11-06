package lv.rtu.server.network_util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Ping extends Thread {

    public void run() {
        try {
            DatagramSocket dSocket = new DatagramSocket(5555);

            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

            while(true){
                dSocket.receive(packet);
                InetAddress clientHost = packet.getAddress();
                int clientPort = packet.getPort();
                byte[] buf = packet.getData();
                DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
                dSocket.send(reply);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
