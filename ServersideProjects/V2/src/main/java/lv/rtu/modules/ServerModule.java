package lv.rtu.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lv.rtu.db.DatabaseTools;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.factories.TreadFactory;
import lv.rtu.server.commands.*;
import lv.rtu.server.connection_thread.ProcessConnectionData;
import lv.rtu.server.connection_thread.ProcessStream;
import lv.rtu.server.network_util.Ping;
import lv.rtu.server.object_handler.ObjectTransfer;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(DatabaseTools.class);
        bind(ObjectTransfer.class);
        bind(ProcessConnectionData.class);
        bind(ProcessStream.class);
        bind(TreadFactory.class);

        bind(Ping.class);

        bind(UserTableImplementationDAO.class);

        bind(Command.class)
                .annotatedWith(Names.named("AddUser"))
                .to(AddUser.class);
        bind(Command.class)
                .annotatedWith(Names.named("DeleteUser"))
                .to(DeleteUser.class);
        bind(Command.class)
                .annotatedWith(Names.named("FileTransfer"))
                .to(FileTransfer.class);
        bind(Command.class)
                .annotatedWith(Names.named("FillDB"))
                .to(FillDB.class);
        bind(Command.class)
                .annotatedWith(Names.named("SetDataStreamMapping"))
                .to(SetDataStreamMapping.class);
        bind(Command.class)
                .annotatedWith(Names.named("SetIPCameraMapping"))
                .to(SetIPCameraMapping.class);
        bind(Command.class)
                .annotatedWith(Names.named("Train"))
                .to(Train.class);
        bind(Command.class)
                .annotatedWith(Names.named("UpdateUser"))
                .to(UpdateUser.class);
        bind(Command.class)
                .annotatedWith(Names.named("RecognizeImage"))
                .to(RecognizeImage.class);
        bind(Command.class)
                .annotatedWith(Names.named("RecognizeAudio"))
                .to(RecognizeImage.class);
        bind(Command.class)
                .annotatedWith(Names.named("Login"))
                .to(Login.class);
        bind(Command.class)
                .annotatedWith(Names.named("Test"))
                .to(Test.class);
    }
}
