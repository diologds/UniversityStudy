package lv.rtu.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import lv.rtu.modules.ClientModule;
import lv.rtu.network_utils.Ping;
import lv.rtu.ui.forms.authorization.LoginForm;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(Ping.class).start();
        injector.getInstance(LoginForm.class).open();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
