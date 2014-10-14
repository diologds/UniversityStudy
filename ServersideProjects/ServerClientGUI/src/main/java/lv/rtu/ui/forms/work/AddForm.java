package lv.rtu.ui.forms.work;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.ObjectFile;
import lv.rtu.domain.User;
import lv.rtu.ui.ui_elements.ImageButton;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddForm extends WorkForm {

    @Inject
    private Connector connector;

    Text userNameLabel;
    Text userSurnameLabel;
    Text userPrivilegesLabel;
    Text imageFilesLabel;
    Text audioFilesLabel;
    Text userIdLabel;
    TextField userId;
    TextField userName;
    TextField userSurname;
    ComboBox userPrivileges;
    TextField imageFiles;
    TextField audioFiles;
    ImageButton sendButton;

    public AddForm() throws ClassNotFoundException {
        super();
        userIdLabel = new Text("User id");
        userNameLabel = new Text("User name");
        userSurnameLabel = new Text("User surname");
        userPrivilegesLabel = new Text("User privileges");
        imageFilesLabel = new Text("Image names");
        audioFilesLabel = new Text("Audio names");

        userId = new TextField("");
        userName = new TextField("");
        userSurname = new TextField("");
        userPrivileges = new ComboBox();
        addValuesToComboBox(userPrivileges);
        imageFiles = new TextField("");
        audioFiles = new TextField("");

        sendButton = new ImageButton("/send.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (Pattern.matches("[0-9]+", userId.getText()) && !userName.getText().isEmpty() && !userSurname.getText().isEmpty()) {
                User createdUser = new User(Long.parseLong(userId.getText()), userName.getText(),
                        userSurname.getText(), userPrivileges.getValue().toString(), audioFiles.getText(), imageFiles.getText());
                try {
                    connector.send(new ObjectFile("general", "Add User", createdUser, accessToken));
                    createDialogWindow(connector.recive().getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setUserElementCoordinates();
    }

    public void open() {
        if (!root.getChildren().contains(sendButton)) {
            root.getChildren().add(userId);
            root.getChildren().add(userName);
            root.getChildren().add(userSurname);
            root.getChildren().add(userPrivileges);
            root.getChildren().add(imageFiles);
            root.getChildren().add(audioFiles);
            root.getChildren().add(userNameLabel);
            root.getChildren().add(userSurnameLabel);
            root.getChildren().add(userPrivilegesLabel);
            root.getChildren().add(imageFilesLabel);
            root.getChildren().add(audioFilesLabel);
            root.getChildren().add(userIdLabel);
            root.getChildren().add(sendButton);
            stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        }
        stage.show();
    }

    public void setUserElementCoordinates() {

        userIdLabel.setLayoutX(95);
        userIdLabel.setLayoutY(120);
        userIdLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userId.setLayoutX(200);
        userId.setLayoutY(100);

        userNameLabel.setLayoutX(95);
        userNameLabel.setLayoutY(150);
        userNameLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userName.setLayoutX(200);
        userName.setLayoutY(130);

        userSurnameLabel.setLayoutX(95);
        userSurnameLabel.setLayoutY(180);
        userSurnameLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userSurname.setLayoutX(200);
        userSurname.setLayoutY(160);

        userPrivilegesLabel.setLayoutX(95);
        userPrivilegesLabel.setLayoutY(210);
        userPrivilegesLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userPrivileges.setLayoutX(200);
        userPrivileges.setLayoutY(190);

        imageFilesLabel.setLayoutX(95);
        imageFilesLabel.setLayoutY(240);
        imageFilesLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        imageFiles.setLayoutX(200);
        imageFiles.setLayoutY(220);

        audioFilesLabel.setLayoutX(95);
        audioFilesLabel.setLayoutY(270);
        audioFilesLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        audioFiles.setLayoutX(200);
        audioFiles.setLayoutY(250);

        sendButton.setLayoutX(200);
        sendButton.setLayoutY(300);
    }

    public void addValuesToComboBox(ComboBox comboBox) {
        if (comboBox != null) {
            ObservableList<String> options =
                    FXCollections.observableArrayList(
                            "admin",
                            "user"
                    );
            comboBox.setItems(options);
        }
    }
}
