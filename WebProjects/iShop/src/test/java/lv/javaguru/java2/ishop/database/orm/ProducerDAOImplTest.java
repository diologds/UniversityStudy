package lv.javaguru.java2.ishop.database.orm;

import java.util.List;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.domain.Producer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class ProducerDAOImplTest extends SpringContextTest {

    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;

    @Autowired
    private ClearDatabaseDAO dbCleaner;


    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
    }

    @Test
    @Transactional
    public void testCreate() throws DBException {
        Producer producer = new Producer("Samsung", "Description");
        producerDAO.create(producer);
        assertNotNull(producer.getId());
    }

    @Test
    @Transactional
    public void testGetById() throws DBException {
        Producer producer = new Producer("Samsung", "Description");
        producerDAO.create(producer);
        Producer producerFromDB = producerDAO.getById(producer.getId());
        assertNotNull(producerFromDB);
        assertTrue(producer == producerFromDB);
    }

    @Test
    @Transactional
    public void testGetByName() throws DBException {
        Producer producer = new Producer("Samsung", "Description");
        producerDAO.create(producer);
        Producer producerFromDB = producerDAO.getByName(producer.getName());
        assertNotNull(producerFromDB);
        assertTrue(producer == producerFromDB);
    }

    @Test
    @Transactional
    public void testGetAll() throws DBException {
        Producer producer1 = new Producer("Samsung1", "Description");
        producerDAO.create(producer1);
        Producer producer2 = new Producer("Samsung2", "Description");
        producerDAO.create(producer2);

        List<Producer> producers = producerDAO.getAll();
        assertNotNull(producers);
        assertEquals(2, producers.size());
        assertTrue(producers.contains(producer1));
        assertTrue(producers.contains(producer2));
    }

    @Test
    @Transactional
    public void testDelete() throws DBException {
        Producer producer = new Producer("Samsung", "Description");
        producerDAO.create(producer);

        producerDAO.delete(producer.getId());

        Producer producerFromDB = producerDAO.getByName(producer.getName());
        assertNull(producerFromDB);
    }

    @Test
    @Transactional
    public void testUpdate() throws DBException {
        Producer producer = new Producer("Samsung", "Description");
        producerDAO.create(producer);

        producer.setName("WWW_QQQ");
        producerDAO.update(producer);
    }

}
