package lv.rtu.test.java.audio_recognition_test;

import lv.rtu.recognition.RecognitionEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AudioRecognitionTest {
    private final double passLimit = 0.8;
    private Map<Long, List<String>> user = new HashMap<>();
    private final String path = "./resources/data/audio/testing/";
    private final File folder = new File("./resources/data/audio/testing");
    @Before
    public void setup() {
        for (final File fileEntry : folder.listFiles()) {
            Long userId = Long.parseLong(fileEntry.getName().replaceAll("\\D+",""));
            if (!user.containsKey(userId)) {
                List<String> list = new ArrayList<>();
                list.add(fileEntry.getName());
                user.put(userId, list);
            } else {
                user.get(userId).add(fileEntry.getName());
            }
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void recognizeByVoice() {
        int counter = 0;
        int testCounter = 0;
        for (Long file : user.keySet()) {
            for(String fileName : user.get(file)){
                testCounter++;
                System.out.println(path+fileName);
                String[] rec = RecognitionEngine.recogniseAudio(path+fileName);
                if (rec[0].contains(String.valueOf(file)) || rec[1].contains(String.valueOf(file))) {
                    counter++;
                }
            }
        }
        System.out.println("Test pass :" + counter + " , Test amount :" + testCounter +", Coverage :" + ((float)counter / testCounter));
        assertThat("Recognition percentage is to low", (((float)counter / testCounter) > passLimit), is(true));
    }
}

