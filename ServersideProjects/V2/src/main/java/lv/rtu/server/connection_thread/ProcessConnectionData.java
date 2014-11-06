package lv.rtu.server.connection_thread;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lv.rtu.domain.ObjectFile;
import lv.rtu.server.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class ProcessConnectionData {

    private Map<String, Command> menuOptionMap = new HashMap<String, Command>();

    @Inject
    public ProcessConnectionData(@Named("AddUser") Command addUser,
                                 @Named("UpdateUser") Command updateUser,
                                 @Named("DeleteUser") Command deleteUser,
                                 @Named("SetDataStreamMapping") Command setDataStreamMapping,
                                 @Named("SetIPCameraMapping") Command setIPCameraMapping,
                                 @Named("FileTransfer") Command fileTransfer,
                                 @Named("FillDB") Command fillDB,
                                 @Named("Train") Command train,
                                 @Named("RecognizeImage") Command recognizeImage,
                                 @Named("RecognizeAudio") Command recognizeAudio,
                                 @Named("Test") Command test) {

        menuOptionMap.put("Transfer File", fileTransfer);
        menuOptionMap.put("Add User", addUser);
        menuOptionMap.put("Update User", updateUser);
        menuOptionMap.put("Delete User", deleteUser);
        menuOptionMap.put("Set Data Stream Mapping", setDataStreamMapping);
        menuOptionMap.put("Set IP Camera Mapping", setIPCameraMapping);
        menuOptionMap.put("Fill DB", fillDB);
        menuOptionMap.put("Train", train);
        menuOptionMap.put("Recognize Image", recognizeImage);
        menuOptionMap.put("Recognize Audio", recognizeAudio);
        menuOptionMap.put("Test", test);
    }

    public ObjectFile objectAnalysis(ObjectFile objectFile) {
        return menuOptionMap.get(objectFile.getSubCommand()).executeCommand(objectFile);
    }

}
