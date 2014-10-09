package lv.rtu.ui.forms;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

    protected int FORM_SIZE_X = 500;
    protected int FORM_SIZE_Y = 500;
    protected int UI_BUTTON_SIZE = 100;
    protected int CONTROL_BUTTON_SIZE = 20;

    protected int ELEMENT_GAP = 10;
    protected int PADDING = 25;
    protected int FONT_SIZE_TITLE = 25;
    protected int FONT_SIZE_TEXT = 16;

    @Inject
    public Ping ping;
    @Inject
    public User user;

    protected Pane root;
    protected Stage stage;

    protected String FONT = "Tahoma";
    protected String BACKGROUND_IMAGE = "-fx-background-image: url('/background.jpg'); -fx-background-repeat: stretch; -fx-background-size: 500 500; ";

    protected static String accessToken;

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

    public void addTooltip(ImageButton button, String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        button.setTooltip(tooltip);
    }

    public void addTooltip(TextField field, String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        field.setTooltip(tooltip);
    }
}
