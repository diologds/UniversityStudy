package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.external_camera.IPCamera;
import lv.rtu.maping.IPCamMapping;

public class SetIPCameraMapping implements Command{
    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        String[] configData = objectFile.getData().split("-");
        IPCamera camera = new IPCamera(configData[0], configData[1], configData[2]);
        IPCamMapping.addElementToMap(camera, configData[3]);
        return new ObjectFile("Added to mapping");
    }
}
