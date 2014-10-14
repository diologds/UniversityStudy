package lv.rtu.server.commands;

import com.google.inject.Inject;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.ObjectFile;

public class DeleteUser implements Command {

    @Inject
    UserTableImplementationDAO dao;

    @Override
    public ObjectFile executeCommand(ObjectFile objectFile) {
        if(objectFile.getUser().getId() != null){
            dao.delete(objectFile.getUser().getId());
        } else if (objectFile.getUser().getUserName() != null && objectFile.getUser().getUserSurname() != null){
            dao.deleteWithName(objectFile.getUser().getUserName(),objectFile.getUser().getUserSurname());
        } else {
            return new ObjectFile("Incorrect data provided");
        }
        return new ObjectFile("User deleted");
    }
}
