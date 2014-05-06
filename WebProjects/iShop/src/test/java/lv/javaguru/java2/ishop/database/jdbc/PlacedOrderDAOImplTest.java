package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.database.CustomerDAO;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.domain.OrderDeliveryType;
import lv.javaguru.java2.ishop.domain.OrderStatus;
import lv.javaguru.java2.ishop.domain.PlacedOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nikolaylapshin on 13/02/14. Test case
 */
public class PlacedOrderDAOImplTest extends SpringContextTest {

    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("PlacedOrderDAO_JDBC")
    private PlacedOrderDAO placedOrderDAO;
    @Autowired
    @Qualifier("CustomerDAO_JDBC")
    private CustomerDAO customerDAO;

    private Customer customer;
    private PlacedOrder order;

    private  void createData() throws Exception {
        Customer c = new Customer(
                "nikolay@mail.lv", "Riga", "Nikolay", "Lapshin", "Riga, Peldu 15",
                "12345678", "password", "123456-12345", true, false, false

        );

        customerDAO.create(c);
        customer = customerDAO.getByPersonalCode("123456-12345");
        order = new PlacedOrder(customer.getId(), new BigDecimal("23.50"), OrderStatus.ready, OrderDeliveryType.tocustomeraddress);
    }

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        createData();
    }

    @After
    public void tearDown() throws Exception {
        //dbCleaner.clear();
    }

    @Test
    public void testCreate() throws Exception {
        placedOrderDAO.create(order);
        PlacedOrder fromDb = placedOrderDAO.getById(order.getId());
        assertTrue(order.equals(fromDb));
    }

    @Test(expected = DBException.class)
    public void testCreateConstraints() throws Exception {
        order.setCustomerId(-4l);
        placedOrderDAO.create(order);
    }

    @Test
    public void testGetById() throws Exception {
        placedOrderDAO.create(order);
        PlacedOrder fromDb = placedOrderDAO.getById(order.getId());
        assertNotNull(fromDb);
    }

    @Test
    public void testGetByStatus() throws Exception {
        placedOrderDAO.create(order);
        List<PlacedOrder> ordersList = placedOrderDAO.getByStatus(order.getStatus());
        assertFalse(ordersList.isEmpty());
        for (PlacedOrder o : ordersList) {
            assertEquals(order.getStatus(), o.getStatus());
        }
    }

    @Test
    public void testGetByDeliveryType() throws Exception {
        placedOrderDAO.create(order);
        List<PlacedOrder> ordersList = placedOrderDAO.getByDeliveryType(order.getDeliveryType());
        assertFalse(ordersList.isEmpty());
        for (PlacedOrder o : ordersList) {
            assertEquals(order.getDeliveryType(), o.getDeliveryType());
        }
    }

    @Test
    public void testDelete() throws Exception {
        placedOrderDAO.create(order);
        placedOrderDAO.delete(order.getId());
        PlacedOrder fromDb = placedOrderDAO.getById(order.getId());
        assertNull(fromDb);
    }

    @Test
    public void testUpdate() throws Exception {
        placedOrderDAO.create(order);
        BigDecimal newTotal = order.getTotal().add(new BigDecimal("45.30"));
        order.setTotal(newTotal);
        placedOrderDAO.update(order);
        PlacedOrder afterUpdate = placedOrderDAO.getById(order.getId());
        assertEquals(afterUpdate.getTotal(), newTotal);
    }
}
