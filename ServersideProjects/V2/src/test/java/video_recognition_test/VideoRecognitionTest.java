package video_recognition_test;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.recognition.RecognitionEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VideoRecognitionTest {
    private final double passLimit = 0.9;
    private Map<Long, List<String>> user = new HashMap<>();
    private final String path = "./resources/data/images/testing/";
    private final File folder = new File("./resources/data/images/testing");

    @Before
    public void setup() {
        DataBaseFiller.fillDB();
        RecognitionEngine.trainRecognizers();

        for (final File fileEntry : folder.listFiles()) {
            Long userId = Long.parseLong(fileEntry.getName().replaceAll("\\D+", ""));
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
    public void recognizeByFace() {

        int counter = 0;
        int testCounter = 0;
        for (Long file : user.keySet()) {
            for (String fileName : user.get(file)) {
                testCounter++;
                try {
                    File imgLoc = new File(path + fileName);
                    BufferedImage img;
                    img = ImageIO.read(imgLoc);
                    if (RecognitionEngine.recogniseImage(img).contains(String.valueOf(file))) {
                        counter++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println("Test pass :" + counter + " , Test amount :" + testCounter + ", Coverage :" + ((float) counter / testCounter));
        assertThat("Recognition percentage is to low", (((float) counter / testCounter) > passLimit), is(true));
    }
}
