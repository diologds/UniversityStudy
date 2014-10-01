package lv.rtu.ui.forms;

import com.google.inject.Inject;
import javafx.scene.Scene;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.AudioFileHandler;
import lv.rtu.domain.ObjectFile;
import lv.rtu.ui.ui_elements.ImageButton;

import java.io.IOException;

public class MicrophoneForm extends Form {

    @Inject
    private Connector connector;

    public MicrophoneForm() {
        super();
    }

    public void open() {
        ImageButton microphoneButton = new ImageButton("/microphone.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        microphoneButton.setOnAction((e) -> {
            if (ping.getServerStatus()) {
                try {
                    connector.setConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    connector.send(new ObjectFile("Login Audio", null, new AudioFileHandler().getRecognitionVoice("temp.wav"), user));
                    createDialogWindow(connector.recive().getMessage());
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        microphoneButton.setLayoutX(190);
        microphoneButton.setLayoutY(400);

        stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        root.getChildren().add(microphoneButton);
        stage.show();
    }

}
