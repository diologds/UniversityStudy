package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.AudioUtils;
import lv.rtu.domain.ObjectFile;
import lv.rtu.domain.User;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class FileTransfer implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        try {
            if (objectFile.getMessage().contains("training")) {

                if (objectFile.getUser().getId() == null) {
                    return new ObjectFile("Incorrect user id");
                }

                User user = dao.select(objectFile.getUser().getId());

                if (user == null) {
                    return new ObjectFile("User don't exists");
                }

                if (objectFile.getMessage().contains("image")) {
                    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                    BufferedImage image = ImageIO.read(byteArrayStream);
                    String fileName = "./resources/data/images/training/" + objectFile.getData() + ".jpg";
                    ImageIO.write(image, "jpg", new File(fileName));
                    byteArrayStream.close();
                    user.setPhotoFilesName(user.getPhotoFilesName() + "," + objectFile.getData() + ".jpg");
                    dao.update(user);
                }

                if (objectFile.getMessage().contains("audio")) {
                    AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                    String fileName = "./resources/data/audio/training/" + objectFile.getData() + ".wav";
                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();
                    user.setAudioFilesName(user.getAudioFilesName() + "," + objectFile.getData() + ".wav");
                    dao.update(user);
                }
            } else if (objectFile.getMessage().contains("testing")) {
                if (objectFile.getMessage().contains("image")) {
                    ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(objectFile.getFileBytes());
                    BufferedImage image = ImageIO.read(byteArrayStream);
                    String fileName = "./resources/data/images/testing/" + objectFile.getData() + ".jpg";
                    ImageIO.write(image, "jpg", new File(fileName));
                    byteArrayStream.close();
                }

                if (objectFile.getMessage().contains("audio")) {
                    AudioInputStream stream = AudioUtils.soundBytesToAudio(objectFile.getFileBytes());
                    String fileName = "./resources/data/audio/testing/" + objectFile.getData() + ".wav";
                    AudioUtils.saveAudioStreamToFile(stream, fileName);
                    stream.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectFile("File received");
    }
}
