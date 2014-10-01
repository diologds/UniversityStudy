package lv.rtu.server.commands;

import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class FileTransfer implements Command{

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        try {
            if (objectFile.getData().contains(".")) {

                if (objectFile.getData().contains("jpg")) {
                    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                    BufferedImage image = ImageIO.read(byteArrayStream);
                    String fileName = "./resources" + objectFile.getData();
                    ImageIO.write(image, "jpg", new File(fileName));
                    byteArrayStream.close();
                }

                if (objectFile.getData().contains("wav")) {
                    AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                    String fileName = "./resources" + objectFile.getData();
                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();
                }

            } else {

                if (objectFile.getData().contains("image")) {
                    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                    BufferedImage image = ImageIO.read(byteArrayStream);
                    RecognitionEngine.recogniseImage(image);
                    System.out.println(objectFile.toString());
                    byteArrayStream.close();
                }

                if (objectFile.getData().contains("audio")) {
                    AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                    String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";
                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();
                    RecognitionEngine.recogniseAudio(fileName);
                    System.out.println(objectFile.toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectFile("File recived");
    }
}
