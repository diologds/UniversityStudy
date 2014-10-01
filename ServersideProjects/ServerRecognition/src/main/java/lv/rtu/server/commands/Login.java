package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.NameGenerator;
import lv.rtu.domain.ObjectFile;
import lv.rtu.domain.User;
import lv.rtu.recognition.RecognitionEngine;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        Long userNumber = null;
        if (objectFile.getMessage().contains("Image")) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new ByteArrayInputStream(objectFile.getFileBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = RecognitionEngine.recogniseImage(image);
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                System.out.println(userNumber = Long.valueOf(matcher.group()));
            }
        }

        if (objectFile.getMessage().contains("Audio")) {
            try {
                AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                String fileName = "./resources/tmp/" + NameGenerator.getName() + ".wav";
                AudioUtils.saveAudioStreamToFile(stream, fileName);
                stream.close();
                String[] result = RecognitionEngine.recogniseAudio(fileName);
                System.out.println(userNumber = Long.valueOf(result[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = dao.select(userNumber);
        System.out.println(user);
        if (objectFile.getUser().getId().equals(userNumber))
            return new ObjectFile("Login successful", null, null, user);
        else
            return new ObjectFile("Login failed");
    }
}
