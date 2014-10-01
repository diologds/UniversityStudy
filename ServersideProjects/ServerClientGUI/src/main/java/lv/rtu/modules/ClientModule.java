package lv.rtu.modules;

import com.google.inject.AbstractModule;
import lv.rtu.connection.sender.Connector;
import lv.rtu.domain.User;
import lv.rtu.network_utils.Ping;
import lv.rtu.ui.forms.CameraForm;
import lv.rtu.ui.forms.LoginForm;
import lv.rtu.ui.forms.MicrophoneForm;
import lv.rtu.ui.forms.WorkForm;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MicrophoneForm.class);
        bind(CameraForm.class);
        bind(WorkForm.class);
        bind(LoginForm.class);
        bind(Ping.class);
        bind(Connector.class);
        bind(User.class);
    }
}
