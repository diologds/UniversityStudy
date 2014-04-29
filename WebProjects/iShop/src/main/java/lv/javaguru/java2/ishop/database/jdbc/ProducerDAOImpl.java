package lv.javaguru.java2.ishop.database.jdbc;

/**
 * Created by Ann on 05/02/14.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.domain.Producer;


@Component(value = "ProducerDAO_JDBC")
public class ProducerDAOImpl extends DAOImpl implements ProducerDAO {

    @Override
    public void create(Producer producer) throws DBException {
        if (producer == null) {
            return;
        }

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into  PRODUCER values (default, ?, ?)");
            preparedStatement.setString(1,producer.getName());
            preparedStatement.setString(2,producer.getDescription());
            preparedStatement.executeUpdate();
        } catch (Throwable e){
            System.out.println("Exception while execute ProducerDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Producer getById(Long id) throws DBException {
        Producer p = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from  PRODUCER where IDProducer = ?");
            preparedStatement.setLong(1, id); 
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                    p = new Producer();
                    p.setName(resultSet.getString("ProducerName"));
                    p.setDescription(resultSet.getString("ProducerDescription"));
                    p.setId(id);

            }
            return p;
        }  catch (Throwable e){
            System.out.println("Exception while execute ProducerDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Producer getByName(String name) throws DBException {
        Producer p = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from  PRODUCER where ProducerName = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                    p = new Producer();
                    p.setName(resultSet.getString("ProducerName"));
                    p.setDescription(resultSet.getString("ProducerDescription"));
                    p.setId(resultSet.getLong("IDProducer"));

            }
            return  p;
        }  catch (Throwable e){
            System.out.println("Exception while execute ProducerDAOImpl.getByName()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Producer> getAll() throws DBException {
        List<Producer> producers = new ArrayList<Producer>();
        Producer p = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from  PRODUCER");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                p = new Producer();
                p.setName(resultSet.getString("ProducerName"));
                p.setDescription(resultSet.getString("ProducerDescription"));
                p.setId(resultSet.getLong("IDProducer"));
                producers.add(p);
            }
            return  producers;
        }  catch (Throwable e){
            System.out.println("Exception while execute ProducerDAOImpl.getAll()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void delete(Long id) throws DBException {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from PRODUCER where IDProducer = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

    }  catch (Throwable e){
        System.out.println("Exception while execute ProducerDAOImpl.delete()");
        e.printStackTrace();
        throw new DBException(e);
    } finally {
        closeConnection(connection);
    }
    }

    @Override
    public void update(Producer producer) throws DBException {
        if (producer == null) {
            return;
        }

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update PRODUCER set ProducerName = ?, ProducerDescription = ? "+
                    "where IDProducer = ?");
            preparedStatement.setString(1, producer.getName());
            preparedStatement.setString(2,producer.getDescription());
            preparedStatement.setLong(3, producer.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e){
            System.out.println("Exception while execute ProducerDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

}


