package lv.rtu.server.commands;

import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;
import org.apache.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;

public class RecognizeAudio implements Command {

    static Logger LOGGER = Logger.getLogger(RecognizeAudio.class.getName());

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        String value = "User not recognized";
        try {
            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
            String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";
            AudioUtils.saveAudioStreamToFile(stream, fileName);
            stream.close();
            value = RecognitionEngine.recogniseAudio(fileName)[0];
            LOGGER.info(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ObjectFile(value);
    }
}
