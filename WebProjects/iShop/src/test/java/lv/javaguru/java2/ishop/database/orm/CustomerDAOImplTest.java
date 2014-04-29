package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.domain.OrderDeliveryType;
import lv.javaguru.java2.ishop.domain.OrderStatus;
import lv.javaguru.java2.ishop.domain.PlacedOrder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static lv.javaguru.java2.ishop.database.builders.CustomerBuilder.createCustomer;
import static lv.javaguru.java2.ishop.database.builders.PlacedOrderBuilder.createPlacedOrder;

/**
 * Created by Ann on 14/03/14.
 */
public class CustomerDAOImplTest extends SpringContextTest {
    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;

    @Autowired
    @Qualifier("PlacedOrderDAO_ORM")
    private PlacedOrderDAO placedOrderDAO;

    @Autowired
    private ClearDatabaseDAO dbCleaner;

    private Customer customer;
    private Customer someCustomer;
    private List<PlacedOrder> orders = new ArrayList<PlacedOrder>();

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();

    }

    private void prepareTestDataSet()
    {
        customer = createCustomer()
                    .withName("James")
                    .withSurname("Bond")
                    .withEmail("j.bond@gmail.com")
                    .withAddress("London")
                    .withDeliveryAddress("Paris")
                    .withPhone("777")
                    .withPersonalCode("007")
                    .withPassword("TopSecret")
                    .withInfoToMail(true)
                    .withAdmin(false)
                    .withAccountLocked(false)
                  .build();
        someCustomer = createCustomer()
                    .withName("Sherlock" )
                    .withSurname("Holmes" )
                    .withEmail("s.holmes@gmail.com" )
                    .withAddress("London")
                    .withDeliveryAddress("London, Baker St, 221")
                    .withPhone( "112")
                    .withPersonalCode("001")
                    .withPassword("SuperSmart")
                    .withInfoToMail(false)
                    .withAdmin(true)
                    .withAccountLocked(false)
                .build();

        orders.add(createPlacedOrder()
                    .withTotal(new BigDecimal("300.00"))
                    .withStatus(OrderStatus.neworder)
                    .withDeliveryType(OrderDeliveryType.inoffice)
                   .build()
                 );
        orders.add(createPlacedOrder()
                .withTotal(new BigDecimal("420.00"))
                .withStatus(OrderStatus.neworder)
                .withDeliveryType(OrderDeliveryType.inoffice)
                .build()
        );
    }
    private void persistCustomer(Customer customer)throws DBException
    {

        for (PlacedOrder o: orders)
          o.setCustomer(customer);
        customer.setOrders(orders);
        customerDAO.create(customer);

    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        persistCustomer(customer);
        assertNotNull(customer.getId());

    }
    @Test
    @Transactional
    public void testCascadeCreate() throws Exception {
        persistCustomer(customer);
        assertNotNull(customer.getId());
        assertNotNull(customer.getOrders().get(0).getId());
        assertNotNull(customer.getOrders().get(1).getId());

    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        persistCustomer(customer);
        Customer customerFromDB = customerDAO.getById(customer.getId());
        assertNotNull(customerFromDB);
        assertTrue(customer==customerFromDB);

    }

    @Test
    @Transactional
    public void testGetByPersonalCode() throws Exception {
        persistCustomer(customer);
        Customer customerFromDB = customerDAO.getByPersonalCode(customer.getPersonalCode());
        assertNotNull(customerFromDB);
        assertTrue(customer==customerFromDB);

    }

    @Test
    @Transactional
    public void testGetByEmail() throws Exception {
        persistCustomer(customer);
        Customer customerFromDB = customerDAO.getByEmail(customer.getEmail());
        assertNotNull(customerFromDB);
        assertTrue(customer==customerFromDB);
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        persistCustomer(customer);
        persistCustomer(someCustomer);
        List<Customer> customers = customerDAO.getAll();
        assertNotNull(customers);
        assertEquals(2,customers.size());
        assertTrue(customers.contains(customer));
        assertTrue(customers.contains(someCustomer));

    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        persistCustomer(customer);
        customerDAO.delete(customer.getId());
        Customer customerFromDB = customerDAO.getById(customer.getId());
        assertNull(customerFromDB);

    }
    @Test
    @Transactional
    public void testCascadeDelete() throws Exception {
        persistCustomer(customer);
        customerDAO.delete(customer.getId());
        Customer customerFromDB = customerDAO.getById(customer.getId());
        assertNull(customerFromDB);
        List<PlacedOrder> orders = placedOrderDAO.getByCustomerId(customer.getId());
        assertTrue(orders.isEmpty());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        persistCustomer(customer);
        customer.setName("007 Agent");
        customerDAO.update(customer);
        Customer customerFromDB = customerDAO.getById(customer.getId());
        assertTrue(customer == customerFromDB);

    }
    @Test
    @Transactional
    public void testCascadeUpdate() throws Exception {
        persistCustomer(customer);
        customer.getOrders().get(0).setStatus(OrderStatus.inprogress);
        customer.getOrders().get(0).setDeliveryType(OrderDeliveryType.tocustomeraddress);
        customerDAO.update(customer);
        List<PlacedOrder> orders = placedOrderDAO.getByCustomerId(customer.getId());
        assertEquals(customer.getOrders().get(0), orders.get(0));

    }
}
