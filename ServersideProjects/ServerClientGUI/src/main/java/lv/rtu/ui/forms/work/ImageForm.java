package lv.rtu.ui.forms.work;

import com.github.sarxos.webcam.Webcam;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
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
    TextField userId;
    TextField fileName;
    ComboBox fileType;
    Text fileNameLabel;
    Text userIdLabel;
    Text fileTypeLabel;

    public ImageForm() {
        super();
        canvas = new Canvas(300, 250);
        canvas.setLayoutX(100);
        canvas.setLayoutY(160);

        userIdLabel = new Text("User Id");
        userIdLabel.setLayoutX(120);
        userIdLabel.setLayoutY(80);
        userIdLabel.setFont(javafx.scene.text.Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        fileNameLabel = new Text("File name");
        fileNameLabel.setLayoutX(120);
        fileNameLabel.setLayoutY(110);
        fileNameLabel.setFont(javafx.scene.text.Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        fileTypeLabel = new Text("File type");
        fileTypeLabel.setLayoutX(120);
        fileTypeLabel.setLayoutY(140);
        fileTypeLabel.setFont(javafx.scene.text.Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));

        userId = new TextField("");
        userId.setLayoutX(200);
        userId.setLayoutY(70);
        fileName = new TextField("");
        fileName.setLayoutX(200);
        fileName.setLayoutY(100);
        fileType = new ComboBox();
        fileType.setLayoutX(200);
        fileType.setLayoutY(130);

        addValuesToComboBox(fileType);
        sendButton = new ImageButton("/camera.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (ping.getServerStatus()) {
                BufferedImage img = webcam.getImage();
                ByteArrayOutputStream baStream = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baStream);
                try {
                    ImageIO.write(img, "jpg", bos);
                    if (fileType.getValue().toString().equals("training")) {
                        if (userId.getText().matches("[0-9]+")) {
                            user.setId(Long.parseLong(userId.getText()));
                        } else {
                            createDialogWindow("Incorrect user id");
                            return;
                        }
                    }
                    connector.send(new ObjectFile(Commands.GENERAL.getValue(), "Transfer File", fileType.getValue().toString() + " image", fileName.getText(), baStream.toByteArray(), accessToken, user));
                    ObjectFile receivedObject = connector.recive();
                    createDialogWindow(receivedObject.getMessage());
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
            root.getChildren().add(fileName);
            root.getChildren().add(fileNameLabel);
            root.getChildren().add(fileType);
            root.getChildren().add(fileTypeLabel);
            root.getChildren().add(userId);
            root.getChildren().add(userIdLabel);
            root.getChildren().add(sendButton);
        }
        stage.show();
    }

    public void closeOperation() {
        timer.cancel();
        timer.purge();
        webcam.close();
    }

    public void addValuesToComboBox(ComboBox comboBox) {
        if (comboBox != null) {
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "training",
                            "testing"
                    );
            comboBox.setItems(options);
        }
    }
}
