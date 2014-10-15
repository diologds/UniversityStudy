package lv.rtu.ui.forms.authorization;

import com.google.inject.Inject;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lv.rtu.ui.forms.Form;
import lv.rtu.ui.forms.work.AddForm;
import lv.rtu.ui.ui_elements.ImageButton;
import org.apache.commons.lang3.StringUtils;

public class LoginForm extends Form {

    @Inject
    private CameraForm cameraForm;
    @Inject
    private MicrophoneForm microphoneForm;
    @Inject
    private AddForm addForm;

    public LoginForm() {
        super();
    }

    public void open() {

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TITLE));
        sceneTitle.setLayoutX(125);
        sceneTitle.setLayoutY(180);
        root.getChildren().add(sceneTitle);

        GridPane grid = new GridPane();
        grid.setHgap(ELEMENT_GAP);
        grid.setVgap(ELEMENT_GAP);
        grid.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        Label userName = new Label("User credential:");
        userName.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userName.setTextFill(Color.BLACK);
        grid.add(userName, 0, 0);

        TextField userTextField = new TextField();
        addTooltip(userTextField, "Insert you'r user credential here");
        userTextField.setText("");
        grid.add(userTextField, 1, 0);


        grid.setLayoutX(100);
        grid.setLayoutY(200);
        root.getChildren().add(grid);

        ImageButton microphoneButton = new ImageButton("/microphone.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        addTooltip(microphoneButton, "Voice authentication");
        ImageButton cameraButton = new ImageButton("/camera.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        addTooltip(cameraButton, "Audio authentication");

        cameraButton.setLayoutX(300);
        cameraButton.setLayoutY(320);

        microphoneButton.setLayoutX(100);
        microphoneButton.setLayoutY(320);

        root.getChildren().add(cameraButton);
        root.getChildren().add(microphoneButton);

        if (ping.getServerStatus()) {
            establishConnection();
        }

        microphoneButton.setOnAction((e) -> {
            if (StringUtils.isNumeric(userTextField.getText())) {
                stage.hide();
                user.setId(Long.valueOf(userTextField.getText()));
                microphoneForm.open();
            } else {
                createDialogWindow("Please input user credentials and check that it is numeric");
            }
        });

        cameraButton.setOnAction((e) -> {
            if (StringUtils.isNumeric(userTextField.getText())) {
                stage.hide();
                user.setId(Long.valueOf(userTextField.getText()));
                cameraForm.open();
            } else {
                createDialogWindow("Please input user credentials and check that it is numeric");
            }
        });

        stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        stage.show();
    }

}
