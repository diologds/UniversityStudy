package lv.rtu.server.commands;

import lv.rtu.domain.ObjectFile;

public interface Command {
    ObjectFile executeCommand(ObjectFile objectFile);
}
