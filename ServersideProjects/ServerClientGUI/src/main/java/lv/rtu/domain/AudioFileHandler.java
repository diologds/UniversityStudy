package lv.rtu.domain;

import java.io.IOException;

public class AudioFileHandler {

    final String path = "C:/Users/Aleksandrs/IdeaProjects/ServerClientGUI/src/main/resources";

    String filePath;
    FileHandler fileHandler;
    AudioRecorder recorder;

    public byte[] getRecognitionVoice(String fileName){

        filePath = path+fileName;
        fileHandler = new FileHandler();
        try {
            fileHandler.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder = new AudioRecorder(filePath);
        recorder.soundRecorder();
        return AudioRecorder.getSoundBytes(path+fileName);
    }
}
