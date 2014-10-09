package lv.rtu.modules;

import com.google.inject.AbstractModule;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.User;
import lv.rtu.network_utils.Ping;
import lv.rtu.ui.forms.authorization.CameraForm;
import lv.rtu.ui.forms.authorization.LoginForm;
import lv.rtu.ui.forms.authorization.MicrophoneForm;
import lv.rtu.ui.forms.work.*;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MicrophoneForm.class);
        bind(CameraForm.class);
        bind(EditForm.class);
        bind(AddForm.class);
        bind(ImageForm.class);
        bind(AudioForm.class);
        bind(RemoveForm.class);
        bind(LoginForm.class);
        bind(Ping.class);
        bind(Connector.class);
        bind(User.class);
    }
}
