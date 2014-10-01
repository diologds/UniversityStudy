package lv.rtu.ui.forms;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lv.rtu.domain.User;
import lv.rtu.network_utils.Ping;
import lv.rtu.ui.ui_elements.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class Form {

    int FORM_SIZE_X = 500;
    int FORM_SIZE_Y = 500;
    int UI_BUTTON_SIZE = 100;
    int CONTROL_BUTTON_SIZE = 20;

    int ELEMENT_GAP = 10;
    int PADDING = 25;
    int FONT_SIZE_TITLE = 25;
    int FONT_SIZE_TEXT = 13;

    @Inject
    public Ping ping;
    @Inject
    public User user;

    Pane root;
    Stage stage;

    String FONT = "Tahoma";
    String BACKGROUND_IMAGE = "-fx-background-image: url('/background.jpg'); -fx-background-repeat: stretch; -fx-background-size: 500 500; ";

    public Form() {
        root = new Pane();
        stage = new Stage();

        Label pingLabel = new Label("");
        ImageButton closeButton = new ImageButton("/close_symbol.png", CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);

        closeButton.setLayoutX(460);
        closeButton.setLayoutY(10);

        pingLabel.setLayoutX(10);
        pingLabel.setLayoutY(480);

        closeButton.setOnAction((e) -> {
            stage.close();
            System.exit(0);
        });

        new Timer().schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        pingLabel.setText(ping.getServerStatus() ?  "Online" : "Offline");
                    }
                });
            }
        }, 1000, 1000);

        root.setStyle(BACKGROUND_IMAGE);
        root.getChildren().add(pingLabel);
        root.getChildren().add(closeButton);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void createDialogWindow(String message){
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Button closeDialogWindow = new Button("OK");
        closeDialogWindow.setOnAction((e) -> {
            dialogStage.close();
        });
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(new Text(message),closeDialogWindow).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();
    }
}
