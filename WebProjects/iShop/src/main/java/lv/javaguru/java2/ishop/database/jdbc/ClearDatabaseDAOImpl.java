package lv.javaguru.java2.ishop.database.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lv.javaguru.java2.ishop.database.DBException;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class ClearDatabaseDAOImpl extends DAOImpl implements ClearDatabaseDAO {

    @Override
    public void clear() throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            connection.prepareStatement("SET SQL_SAFE_UPDATES=0;").execute();
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            connection.commit();
            try {
                for (String tableName : getDatabaseMetaData(connection)) {
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("DELETE FROM " + tableName);
                    System.out.println(preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                connection.commit();
                stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Deleted All Rows In  Table Error. ");
                e.printStackTrace();
                throw new DBException(e);
            }
            connection.close();
        } catch (Throwable e) {
            System.out.println("Exception while deleting data from DB.");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    private List<String> getDatabaseMetaData(Connection connection) {
        List<String> tableList = new ArrayList<String>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            while (rs.next())
                tableList.add(rs.getString("TABLE_NAME"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

}
