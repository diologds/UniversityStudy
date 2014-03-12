package lv.rtu.streaming.video;

import lv.rtu.maping.Mapping;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VideoStreaming implements Runnable {

    private int portServer;
    private int portClient;

    public VideoStreaming(int portServer, int portClient) {
        this.portServer = portServer;
        this.portClient = portClient;
    }

    @Override
    public void run() {
        try {

            // Create a socket to listen on the port.
            DatagramSocket dsocket = new DatagramSocket(portServer);
            DatagramSocket ssocket = new DatagramSocket();
            // Create a buffer to read datagrams into. If a
            // packet is larger than this buffer, the
            // excess will simply be discarded!
            byte[] buffer = new byte[10048];

            // Create a packet to receive data into the buffer
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Now loop forever, waiting to receive packets and printing them.
            do {
                // Wait to receive a datagram
                dsocket.receive(packet);
                if (packet.getLength() > 0) {
                    InputStream in = new ByteArrayInputStream(packet.getData());
                    BufferedImage image = ImageIO.read(in);
                    String host = Mapping.getDestination(packet.getAddress().toString().substring(1));
                    System.out.println("Client IP : " + packet.getAddress().toString());
                    String result = RecognitionEngine.recogniseImage(image);
                    System.out.println("Result : " + result);
                    System.out.println("Sending data to port : " + portClient + " :: Client host :" + host);
                    ssocket.send(new DatagramPacket(result.getBytes(), result.getBytes().length, InetAddress.getByName(host),
                            portClient));
                }

            } while (packet.getLength() > 0);
        System.out.println("User has left stream ");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }

}
