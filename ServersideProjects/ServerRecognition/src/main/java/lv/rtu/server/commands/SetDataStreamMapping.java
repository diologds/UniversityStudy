package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;
import lv.rtu.maping.DataStreamMapping;

public class SetDataStreamMapping implements Command{
    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        String[] array = objectFile.getData().split("-");
        DataStreamMapping.addElementToMap(array[0], array[1]);
        return new ObjectFile("Added to mapping");
    }
}
