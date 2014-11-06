package lv.rtu.streaming.video;

import lv.rtu.maping.DataStreamMapping;
import lv.rtu.recognition.RecognitionEngine;
import org.apache.log4j.Logger;

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

    static Logger LOGGER = Logger.getLogger(VideoStreaming.class.getName());

    public VideoStreaming(int portServer, int portClient) {
        this.portServer = portServer;
        this.portClient = portClient;
    }

    @Override
    public void run() {
        try {

            DatagramSocket dSocket = new DatagramSocket(portServer);
            DatagramSocket sSocket = new DatagramSocket();

            byte[] buffer = new byte[10048];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            do {
                dSocket.receive(packet);
                if (packet.getLength() > 0) {

                    InputStream in = new ByteArrayInputStream(packet.getData());
                    BufferedImage image = ImageIO.read(in);
                    String host = DataStreamMapping.getDestination(packet.getAddress().toString().substring(1));
                    LOGGER.info("Client IP : " + packet.getAddress().toString());
                    String result = RecognitionEngine.recogniseImage(image);
                    LOGGER.info("Result : " + result);
                    LOGGER.info("Sending data to port : " + portClient + " :: Client host :" + host);
                    sSocket.send(new DatagramPacket(result.getBytes(), result.getBytes().length, InetAddress.getByName(host), portClient));
                }

            } while (packet.getLength() > 0);
            LOGGER.info("User has left stream ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

