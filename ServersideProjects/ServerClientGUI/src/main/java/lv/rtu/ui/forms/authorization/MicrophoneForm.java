package lv.rtu.ui.forms.authorization;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.scene.Scene;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.AudioFileHandler;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.ui.forms.Form;
import lv.rtu.ui.forms.work.AddForm;
import lv.rtu.ui.ui_elements.ImageButton;

import java.io.IOException;

public class MicrophoneForm extends Form {

    @Inject
    private Connector connector;

    @Inject
    private AddForm addForm;

    ImageButton sendButton;

    public MicrophoneForm() {
        super();

        sendButton = new ImageButton("/microphone.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (ping.getServerStatus()) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            connector.send(new ObjectFile(Commands.LOGIN.getValue(), Commands.AUDIO.getValue(), null, null, new AudioFileHandler().getRecognitionVoice("temp.wav"), accessToken, user));
                            ObjectFile receivedObject = connector.recive();
                            createDialogWindow(receivedObject.getMessage());
                            accessToken = receivedObject.getAccessToken();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        stage.close();
                        addForm.open();
                    }
                });
            }
        });

        sendButton.setLayoutX(190);
        sendButton.setLayoutY(250);
        addTooltip(sendButton, "Take voice sample and send it to server");
    }

    public void open() {
        stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        root.getChildren().add(sendButton);
        stage.show();
    }

}
