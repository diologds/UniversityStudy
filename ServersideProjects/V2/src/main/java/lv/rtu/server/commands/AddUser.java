package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;
import lv.rtu.domain.User;

public class AddUser implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        if (objectFile.getUser().getId() == null) {
            return new ObjectFile("Incorrect user id");
        }

        User user = dao.select(objectFile.getUser().getId());

        if (user != null) {
            return new ObjectFile("User already exists");
        }
        dao.insert(objectFile.getUser());
        return new ObjectFile("User Added");
    }
}
