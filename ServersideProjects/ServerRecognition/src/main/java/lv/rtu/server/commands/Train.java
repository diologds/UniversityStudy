package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.recognition.RecognitionEngine;

public class Train implements Command{
    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        RecognitionEngine.trainRecognizers();
        return new ObjectFile("Recognizer trained");
    }
}
