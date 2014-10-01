package audio_recognition_test;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.recognition.RecognitionEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AudioRecognitionTest {
    private final double passLimit = 0.9;
    private Map<Long, String> user = new HashMap<>();

    @Before
    public void setup() {
        DataBaseFiller.fillDB();
        RecognitionEngine.trainRecognizers();

        user.put(13l, "./resources/data/audio/testing/aleksandrs1.wav");
        user.put(14l, "./resources/data/audio/testing/aihua1.wav");
        user.put(15l, "./resources/data/audio/testing/alexandrm1.wav");
        user.put(16l, "./resources/data/audio/testing/emily1.wav");
        user.put(17l, "./resources/data/audio/testing/jim1.wav");
        user.put(18l, "./resources/data/audio/testing/ke1.wav");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void recognizeByVoice() {
        int counter = 0;
        for (Long file : user.keySet()) {
            String rec = RecognitionEngine.recogniseAudio(user.get(file))[0];
            if (rec.contains(String.valueOf(file))) {
                counter++;
            }
        }
        assertThat("Recognition percentage is to low", (user.size() / counter > passLimit), is(true));
    }

}

