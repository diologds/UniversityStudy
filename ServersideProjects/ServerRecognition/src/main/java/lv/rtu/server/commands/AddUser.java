package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;

public class AddUser implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        dao.insert(objectFile.getUser());
        return new ObjectFile("User Added");
    }
}
