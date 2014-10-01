package lv.rtu.server.commands;

import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;

public class RecognizeAudio implements Command {

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        try {
            AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
            String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";
            AudioUtils.saveAudioStreamToFile(stream, fileName);
            stream.close();
            System.out.println(RecognitionEngine.recogniseAudio(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ObjectFile("Recognized");
    }
}
