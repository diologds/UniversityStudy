package lv.rtu.ui.forms;

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
import lv.rtu.ui.ui_elements.ImageButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CameraForm extends Form {

    @Inject
    private Connector connector;

    @Inject
    private WorkForm workForm;

    private Timer timer;
    private Webcam webcam;

    public CameraForm() {
        super();
    }

    public void open() {
        Canvas canvas = new Canvas(300, 250);
        canvas.setLayoutX(100);
        canvas.setLayoutY(125);

        ImageButton sendButton = new ImageButton("/camera.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if(ping.getServerStatus()){
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
                    connector.send(new ObjectFile("Login Image", null, baStream.toByteArray(), user));
                    createDialogWindow(connector.recive().getMessage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                stage.hide();
                workForm.open();
            }
        });
        sendButton.setLayoutX(190);
        sendButton.setLayoutY(400);

        stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        root.getChildren().add(canvas);
        root.getChildren().add(sendButton);
        stage.show();

        timer = new Timer();
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320, 240));
        webcam.open();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        BufferedImage image = webcam.getImage();
                        canvas.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(image, null), 10, 10);
                    }
                });
            }
        }, 100, 100);

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
    }

    public void closeOperation() {
        timer.cancel();
        timer.purge();
        webcam.close();
    }
}
