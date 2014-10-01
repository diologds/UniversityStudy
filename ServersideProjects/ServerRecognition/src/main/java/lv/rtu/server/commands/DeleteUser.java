package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;

public class DeleteUser implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        dao.delete(objectFile.getUser().getId());
        return new ObjectFile("User deleted");
    }
}
