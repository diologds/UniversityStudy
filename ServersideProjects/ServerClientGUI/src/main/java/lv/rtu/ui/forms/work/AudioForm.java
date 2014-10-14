package lv.rtu.ui.forms.work;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.AudioFileHandler;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.ui.ui_elements.ImageButton;

import java.io.IOException;

public class AudioForm extends WorkForm {

    @Inject
    private Connector connector;

    TextField userId;
    TextField fileName;
    ComboBox fileType;
    Text fileNameLabel;
    Text userIdLabel;
    Text fileTypeLabel;

    public AudioForm() {
        super();
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
        addValuesToComboBox(fileType);
        fileType.setLayoutX(200);
        fileType.setLayoutY(130);
    }

    public void open() {
        ImageButton sendButton = new ImageButton("/microphone.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (ping.getServerStatus()) {
                try {
                    connector.send(new ObjectFile(Commands.GENERAL.getValue(), "Transfer File", fileType.getValue().toString() + " audio", fileName.getText(), new AudioFileHandler().getRecognitionVoice("temp.wav"), accessToken, user));
                    ObjectFile receivedObject = connector.recive();
                    createDialogWindow(receivedObject.getMessage());
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
        addTooltip(sendButton, "Take voice sample and send it to server");
        if (!root.getChildren().contains(sendButton)) {
            stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
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
