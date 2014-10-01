package lv.rtu.server.commands;

import lv.rtu.db.DataBaseFiller;
import lv.rtu.domain.ObjectFile;

public class FillDB implements Command {
    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        DataBaseFiller.fillDB();
        return new ObjectFile("Data base filed");
    }
}
