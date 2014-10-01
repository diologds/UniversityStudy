package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;

public class UpdateUser implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        dao.update(objectFile.getUser());
        return new ObjectFile("User information updated");
    }
}
