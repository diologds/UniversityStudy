package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RecognizeImage implements Command{

    static Logger LOGGER = Logger.getLogger(RecognizeImage.class.getName());

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(objectFile.getFileBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(RecognitionEngine.recogniseImage(image));
        return new ObjectFile("Recognized");
    }
}
