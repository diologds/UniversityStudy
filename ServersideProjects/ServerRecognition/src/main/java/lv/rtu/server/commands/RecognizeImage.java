package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RecognizeImage implements Command{

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(objectFile.getFileBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("***" + RecognitionEngine.recogniseImage(image));
        return new ObjectFile("Recognized");
    }
}
