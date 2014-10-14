package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.test.java.audio_recognition_test.AudioRecognitionTest;
import lv.rtu.test.java.db_tests.UserTest;
import lv.rtu.test.java.video_recognition_test.VideoRecognitionTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test implements Command{
    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        Result result = JUnitCore.runClasses(AudioRecognitionTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        result = JUnitCore.runClasses(UserTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        result = JUnitCore.runClasses(VideoRecognitionTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        return new ObjectFile("Test executed");
    }
}
