//package ishop.jdbc.database_tools;
package lv.javaguru.java2.ishop.database.jdbc;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Properties;

public class DatabaseTools {

    private String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName;
    private String userName;
    private String password;
    private PreparedStatement preparedStatement = null;
    private String filePath = ".\\iShop\\resources\\ishop.properties";
    private String testFilePath = ".\\iShop\\resources\\ishop.properties";
    //private String filePath = ".\\out\\production\\ishop\\ishop.properties";
    //private String testFilePath = ".\\out\\production\\ishop\\ishop.properties";

    public DatabaseTools setJDBCProperties(boolean isTestRun) {
        FileInputStream in;
        Properties properties = new Properties();
        try {
            in = (isTestRun) ? new FileInputStream(testFilePath) : new FileInputStream(filePath);
            properties.load(in);
            url = properties.getProperty("dbUrl");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            dbName = properties.getProperty("dbSchema");
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File input failed");
        }
        return this;
    }

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, userName, password);
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
   
    public void clear() {
        Connection connection;
        try {
            Class.forName(driverName);

            //'true' instead of 'false' to work with test DB
            //setJDBCProperties(isTestRun);
            connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(false);
            connection.prepareStatement("SET SQL_SAFE_UPDATES=0;").execute();
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            connection.commit();
            try {


                for (String tableName : getDatabaseMetaData(connection)) {
                    preparedStatement = connection
                            .prepareStatement("DELETE FROM " + dbName + "." + tableName + ";");
                    System.out.println(preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                connection.commit();
                stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                connection.commit();
            } catch (SQLException s) {
                System.out.println("Deleted All Rows In  Table Error. ");
                s.printStackTrace();
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}