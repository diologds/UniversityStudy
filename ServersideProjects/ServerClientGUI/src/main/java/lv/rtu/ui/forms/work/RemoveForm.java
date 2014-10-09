package lv.rtu.ui.forms.work;

import com.google.inject.Inject;
import javafx.scene.Scene;
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

public class RemoveForm extends WorkForm {

    @Inject
    private Connector connector;
    Text sceneTitle;
    Text userNameLabel;
    Text userSurnameLabel;
    Text userIdLabel;

    TextField userName;
    TextField userSurname;
    TextField userId;

    ImageButton sendButton;

    public RemoveForm() {
        super();
        sceneTitle = new Text("OR");
        userNameLabel = new Text("User name");
        userSurnameLabel = new Text("User surname");
        userIdLabel = new Text("User Id");

        userId = new TextField("");
        userName = new TextField("");
        userSurname = new TextField("");

        sendButton = new ImageButton("/send.png", UI_BUTTON_SIZE, UI_BUTTON_SIZE);
        sendButton.setOnAction((e) -> {
            if (Pattern.matches("[0-9]+", userId.getText())) {
                User createdUser = new User(Long.parseLong(userId.getText()), userName.getText(), userSurname.getText(), null,null,null);
                try {
                    connector.setConnection();
                    connector.send(new ObjectFile("general","Add User",createdUser, accessToken));
                    connector.recive();
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
            root.getChildren().add(userIdLabel);
            root.getChildren().add(userName);
            root.getChildren().add(userSurname);
            root.getChildren().add(userNameLabel);
            root.getChildren().add(userSurnameLabel);
            root.getChildren().add(sceneTitle);
            root.getChildren().add(sendButton);
            stage.setScene(new Scene(root, FORM_SIZE_X, FORM_SIZE_Y));
        }
        stage.show();
    }

    public void setUserElementCoordinates() {

        sceneTitle.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TITLE));
        sceneTitle.setLayoutX(225);
        sceneTitle.setLayoutY(180);

        userIdLabel.setLayoutX(95);
        userIdLabel.setLayoutY(115);
        userIdLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userId.setLayoutX(200);
        userId.setLayoutY(100);

        userNameLabel.setLayoutX(95);
        userNameLabel.setLayoutY(225);
        userNameLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userName.setLayoutX(200);
        userName.setLayoutY(210);

        userSurnameLabel.setLayoutX(95);
        userSurnameLabel.setLayoutY(255);
        userSurnameLabel.setFont(Font.font(FONT, FontWeight.NORMAL, FONT_SIZE_TEXT));
        userSurname.setLayoutX(200);
        userSurname.setLayoutY(240);

        sendButton.setLayoutX(200);
        sendButton.setLayoutY(300);
    }

}
