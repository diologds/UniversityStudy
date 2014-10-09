package lv.rtu.ui.forms.work;

import com.google.inject.Inject;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.ui.forms.Form;
import lv.rtu.ui.ui_elements.ImageButton;

import java.io.IOException;

public class WorkForm extends Form {

    int MENU_BUTTON_SIZE = 30;

    @Inject
    private Connector connector;
    @Inject
    private AddForm addForm;
    @Inject
    private RemoveForm removeForm;
    @Inject
    private EditForm editForm;
    @Inject
    private ImageForm imageForm;
    @Inject
    private AudioForm audioForm;

    ImageButton addUser;
    ImageButton removeUser;
    ImageButton changeUser;
    ImageButton addImage;
    ImageButton addAudio;
    ImageButton train;
    ImageButton test;

    public WorkForm() {
        super();
        addUser = new ImageButton("/addUser.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        addUser.setOnAction((e) -> {
            stage.close();
            addForm.open();
        });
        removeUser = new ImageButton("/removeUser.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        removeUser.setOnAction((e) -> {
            stage.close();
            removeForm.open();
        });
        changeUser = new ImageButton("/editUser.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        changeUser.setOnAction((e) -> {
            stage.close();
            editForm.open();
        });
        addImage = new ImageButton("/addImage.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        addImage.setOnAction((e) -> {
            stage.close();
            imageForm.open();
        });
        addAudio = new ImageButton("/addAudio.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        addAudio.setOnAction((e) -> {
            stage.close();
            audioForm.open();
        });
        train = new ImageButton("/reTrain.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        train.setOnAction((e) -> {
            try {
                connector.send(new ObjectFile(Commands.GENERAL.getValue(), "Train", accessToken));
                createDialogWindow(connector.recive().getMessage());
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        test = new ImageButton("/test.png", MENU_BUTTON_SIZE, MENU_BUTTON_SIZE);
        setButtonStyle();
        addGuiElementsToForm();
    }

    public void addGuiElementsToForm() {
        root.getChildren().add(addUser);
        root.getChildren().add(removeUser);
        root.getChildren().add(changeUser);
        root.getChildren().add(addImage);
        root.getChildren().add(addAudio);
        root.getChildren().add(train);
        root.getChildren().add(test);
    }

    public void setButtonStyle() {
        addUser.setLayoutX(455);
        addUser.setLayoutY(100);
        addTooltip(addUser, "Add user to server database");
        removeUser.setLayoutX(455);
        removeUser.setLayoutY(140);
        addTooltip(removeUser, "Remove user from server database");
        changeUser.setLayoutX(455);
        changeUser.setLayoutY(190);
        addTooltip(changeUser, "Edit user data in server database");
        addImage.setLayoutX(455);
        addImage.setLayoutY(230);
        addTooltip(addImage, "Add image to server database");
        addAudio.setLayoutX(455);
        addAudio.setLayoutY(270);
        addTooltip(addAudio, "Add audio sample to server database");
        train.setLayoutX(455);
        train.setLayoutY(310);
        addTooltip(train, "Re-train recognizer");
        test.setLayoutX(455);
        test.setLayoutY(350);
        addTooltip(test, "Run server test");
    }
}
