package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.DBException;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public interface ClearDatabaseDAO {

    void clear() throws DBException;

}
