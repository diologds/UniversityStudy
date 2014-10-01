package lv.rtu.ui.forms;

import com.google.inject.Inject;
import javafx.scene.control.Button;
import lv.rtu.connection.sender.Connector;

public class WorkForm extends Form {

    @Inject
    private Connector connector;

    public WorkForm() {
        super();
    }

    public void open(){
        Button addUser = new Button("Add User");
        Button removeUser = new Button("Remove User");
        Button changeUser = new Button("Change User");
        Button addImage = new Button("Add Image to server");
        Button addAudio = new Button("Add audio to server");
        Button train = new Button("Change User");
        Button test = new Button("Change User");
        root.getChildren().add(addUser);
        root.getChildren().add(removeUser);
        root.getChildren().add(changeUser);
        root.getChildren().add(addImage);
        root.getChildren().add(addAudio);
        root.getChildren().add(train);
        root.getChildren().add(test);
        stage.show();
    }
}
