package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.database.CustomerDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ann on 13/02/14.
 */
public class CustomerDAOImplTest extends SpringContextTest {
    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("CustomerDAO_JDBC")
    private CustomerDAO customerDAO;

    private Customer[] customers = new Customer[3];
    private Customer[] customersRetrieved = new Customer[3];
    private Customer someCustomer = null;

    private void createCustomers()
    {
        customers[0]= new Customer("j.bond@gmail.com","London","James","Bond","Paris","777",
                                   "TopSecret","007",true,false,false);

        customers[1]= new Customer("s.holmes@gmail.com","London","Sherlock","Holmes","London, Baker St, 221","112",
                                   "SuperSmart","001",false,true,false);

        customers[2] = new Customer("carlson@fictionhero.com",null,"Unknown","Carlson",null,"000",
                                    "VerySimple","111",true,false,true);

        someCustomer = new Customer("some@inbox.lv","Riga, Lačplēša 37","Jānis","Berziņš",null,"+37129670940",
                                    "Password","14022014-12802", true,false,false);

    }
    @Before
    public void setUp() throws Exception
    {
        dbCleaner.clear();
        createCustomers();
        for (Customer c: customers)
            customerDAO.create(c);
    }

    @Test
    public void testCreate() throws Exception
    {
        Customer c = customerDAO.getByPersonalCode("001");

        assertEquals(customers[1].getEmail(), c.getEmail());
        assertEquals(customers[1].getAddress(),c.getAddress());
        assertEquals(customers[1].getName(),c.getName());
        assertEquals(customers[1].getSurname(),c.getSurname());
        assertEquals(customers[1].getDeliveryAddress(),c.getDeliveryAddress());
        assertEquals(customers[1].getPhone(),c.getPhone());
        assertEquals(customers[1].getPassword(),c.getPassword());
        assertEquals(customers[1].getPersonalCode(),c.getPersonalCode());
        assertEquals(customers[1].getInfoToMail(),c.getInfoToMail());

    }

    @Test
    public void testGetById() throws Exception
    {
        Long id = customerDAO.getByPersonalCode("111").getId();
        Customer c = customerDAO.getById(id);
        assertEquals(customers[2].getEmail(), c.getEmail());
        assertEquals(customers[2].getAddress(),c.getAddress());
        assertEquals(customers[2].getName(),c.getName());
        assertEquals(customers[2].getSurname(),c.getSurname());
        assertEquals(customers[2].getDeliveryAddress(),c.getDeliveryAddress());
        assertEquals(customers[2].getPhone(),c.getPhone());
        assertEquals(customers[2].getPassword(),c.getPassword());
        assertEquals(customers[2].getPersonalCode(),c.getPersonalCode());
        assertEquals(customers[2].getInfoToMail(),c.getInfoToMail());

    }

    @Test
    public void testGetByPersonalCode() throws Exception
    {
        assertEquals("007", customerDAO.getByPersonalCode("007").getPersonalCode());
        assertEquals("001", customerDAO.getByPersonalCode("001").getPersonalCode());
        assertEquals("111", customerDAO.getByPersonalCode("111").getPersonalCode());

    }

    @Test
    public void testDelete() throws Exception
    {
        Long id = customerDAO.getByPersonalCode("007").getId();
        customerDAO.delete(id);
        assertEquals(null, customerDAO.getById(id));

    }

    @Test
    public void testUpdate() throws Exception
    {
        Customer c = customerDAO.getByPersonalCode("007");
        Long id = c.getId();
        someCustomer.setId(id);
        customerDAO.update(someCustomer);
        assertEquals(someCustomer, customerDAO.getById(id));

    }
}
