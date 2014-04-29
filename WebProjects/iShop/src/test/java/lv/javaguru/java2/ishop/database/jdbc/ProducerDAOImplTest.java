
package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.domain.Producer;
import lv.javaguru.java2.ishop.database.ProducerDAO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Ann on 06/02/14.
 */
public class ProducerDAOImplTest extends SpringContextTest {

    @Autowired
    private  ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;

    private Producer[] producers = new Producer[3];
    private Producer[] producersRetrieved = new Producer[3];
    private Producer someProducer = null;

    private void createProducers()
    {
        producers[0] = new Producer("Lenovo","Just Good");
        producers[1] = new Producer("Apple","Premium");
        producers[2] = new Producer("Acer","Budget");
        someProducer = new Producer("Apple Co","Cool");

    }


    @Before
    public void testSetup()throws Exception
    {
       dbCleaner.clear();
        createProducers();
        for (Producer p: producers)
            producerDAO.create(p);
    }

    @Test
    public void testCreate() throws Exception
    {
        producersRetrieved[0] = producerDAO.getByName("Lenovo");
        producersRetrieved[1] = producerDAO.getByName("Apple");
        producersRetrieved[2] = producerDAO.getByName("Acer");

        for(int i=0; i<producers.length; i++)
        {
            assertEquals(producers[i].getName(), producersRetrieved[i].getName());
            assertEquals(producers[i].getDescription(),producersRetrieved[i].getDescription());
        }

    }
    @Test
    public void testGetById() throws Exception
    {
        Long id1 = producerDAO.getByName("Lenovo").getId();
        Long id2 = producerDAO.getByName("Apple").getId();
        Long id3 = producerDAO.getByName("Acer").getId();

        assertEquals("Lenovo", producerDAO.getById(id1).getName());
        assertEquals("Just Good", producerDAO.getById(id1).getDescription());
        assertEquals("Apple", producerDAO.getById(id2).getName());
        assertEquals("Premium", producerDAO.getById(id2).getDescription());
        assertEquals("Acer", producerDAO.getById(id3).getName());
        assertEquals("Budget", producerDAO.getById(id3).getDescription());

    }

    @Test
    public void testGetByName() throws Exception
    {

        assertEquals("Lenovo", producerDAO.getByName("Lenovo").getName());
        assertEquals("Just Good", producerDAO.getByName("Lenovo").getDescription());
        assertEquals("Acer", producerDAO.getByName("Acer").getName());
        assertEquals("Budget", producerDAO.getByName("Acer").getDescription());
        assertEquals("Apple", producerDAO.getByName("Apple").getName());
        assertEquals("Premium", producerDAO.getByName("Apple").getDescription());

    }

    @Test
    public void testGetAll() throws Exception
    {
        List<Producer> producersFromDb = producerDAO.getAll();
        assertEquals(producers.length, producersFromDb.size());
    }

    @Test
    public void testDelete() throws Exception {


        long id1 = producerDAO.getByName("Apple").getId();

        producerDAO.delete(id1);
        assertNull(producerDAO.getById(id1));
    }

    @Test
    public void testUpdate() throws Exception {


        Producer producer = producerDAO.getByName("Apple");
        producer.setName(someProducer.getName());
        producer.setDescription(someProducer.getDescription());

        producerDAO.update(producer);

        assertEquals(producer, producerDAO.getByName(someProducer.getName()));

    }

}
