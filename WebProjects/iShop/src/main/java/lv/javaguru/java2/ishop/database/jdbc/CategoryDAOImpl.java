package lv.javaguru.java2.ishop.database.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.database.DBException;


/**
 * Created by Ann on 09/02/14.
 */
@Component("CategoryDAO_JDBC")
public class CategoryDAOImpl extends DAOImpl implements CategoryDAO {

    @Override
    public void create(Category category) throws DBException {
        if (category == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into CATEGORY values (default, ?)");
            preparedStatement.setString(1,category.getName());
            preparedStatement.executeUpdate();
        } catch (Throwable e){
            System.out.println("Exception while execute CategoryDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Category getById(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from CATEGORY where IDCategory = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Category category = null;
            if (resultSet.next())
            {

                    category = new Category();
                    category.setName(resultSet.getString("CategoryName"));
                    category.setId(id);

            }
            return category;
        } catch (Throwable e){
            System.out.println("Exception while execute CategoryDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Category getByName(String name) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from CATEGORY where CategoryName = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Category category = null;
            if (resultSet.next())
            {

                    category = new Category();
                    category.setName(resultSet.getString("CategoryName"));
                    category.setId(resultSet.getLong("IDCategory"));
                }

            return category;
        } catch (Throwable e){
            System.out.println("Exception while execute CategoryDAOImpl.getByName()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from CATEGORY where IDCategory = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e){
            System.out.println("Exception while execute CategoryDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(Category category) throws DBException {
        if (category == null) {
            return;
        }
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update CATEGORY set CategoryName = ?"+"where IDCategory = ?");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setLong(2, category.getId());
            preparedStatement.executeUpdate();

        } catch (Throwable e){
            System.out.println("Exception while execute CategoryDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }
    @Override
    public List<Category> getAll() throws DBException {
        List<Category> categories = new ArrayList<Category>();
        Category cat = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from  CATEGORY");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cat = new Category();
                cat.setName(resultSet.getString("CategoryName"));
                cat.setId(resultSet.getLong("IDCategory"));
                categories.add(cat);
            }
            return  categories;
        } catch (Throwable e) {
            System.out.println("Exception while execute CategoryDAOImpl.getAll()");
            e.printStackTrace();
            throw new DBException(e);
        }
        finally
        {
            closeConnection(connection);
        }
    }

}
