package lv.rtu.ui.forms.work;

import com.github.sarxos.webcam.Webcam;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.WindowEvent;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.ui.ui_elements.ImageButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ImageForm extends WorkForm {

    @Inject
    private Connector connector;

    private Timer timer;
    private Webcam webcam;

    Canvas canvas;
    ImageButton sendButton;

    public ImageForm() {
        super();
        canvas = new Canvas(300, 250);
        canvas.setLayoutX(100);
        canvas.setLayoutY(125);

        sendButton = new ImageButton("/camera.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (ping.getServerStatus()) {
                try {
                    connector.setConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                BufferedImage img = webcam.getImage();
                ByteArrayOutputStream baStream = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baStream);
                try {
                    ImageIO.write(img, "jpg", bos);
                    connector.send(new ObjectFile(Commands.LOGIN.getValue(), Commands.IMAGE.getValue(), null, baStream.toByteArray(), user));
                    ObjectFile receivedObject = connector.recive();
                    createDialogWindow(receivedObject.getMessage());
                    System.out.println(receivedObject.getAccessToken());
                    accessToken = receivedObject.getAccessToken();
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendButton.setLayoutX(190);
        sendButton.setLayoutY(400);
        addTooltip(sendButton, "Take photo and send it to server");

        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320, 240));
    }

    public void open() {
        webcam.open();
        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (webcam.isOpen()) {
                            BufferedImage image = webcam.getImage();
                            canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image, null), 10, 10);
                        }
                    }
                });
            }
        }, 100, 100);

        if (!root.getChildren().contains(sendButton)) {
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    closeOperation();
                }
            });
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    closeOperation();
                }
            });
            stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
            root.getChildren().add(canvas);
            root.getChildren().add(sendButton);
        }
        stage.show();
    }

    public void closeOperation() {
        timer.cancel();
        timer.purge();
        webcam.close();
    }
}
