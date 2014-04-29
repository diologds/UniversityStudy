package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.domain.OrderDeliveryType;
import lv.javaguru.java2.ishop.domain.OrderStatus;
import lv.javaguru.java2.ishop.domain.PlacedOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by nikolaylapshin on 13/03/14.
 */
public class PlacedOrderDAOImplTest extends SpringContextTest {
    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("PlacedOrderDAO_ORM")
    private PlacedOrderDAO placedOrderDAO;
    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;

    private Customer customer;
    private PlacedOrder order;


    private  void createData() throws Exception {
        Customer c = new Customer(
                "nikolay@mail.lv", "Riga", "Nikolay", "Lapshin", "Riga, Peldu 15",
                "12345678", "password", "123456-12345", true, false, false

        );
        order = new PlacedOrder(new BigDecimal("23.50"), OrderStatus.inprogress, OrderDeliveryType.tocustomeraddress);

        customerDAO.create(c);
        customer = customerDAO.getByPersonalCode("123456-12345");
        order.setCustomer(customer);
        List<PlacedOrder> o = new ArrayList<PlacedOrder>();
        o.add(order);
        customer.setOrders(o);
    }

    @Before
    @Transactional
    public void setUp() throws Exception {
        dbCleaner.clear();
        createData();
    }

    @After
    public void tearDown() throws Exception {
        //dbCleaner.clear();
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {

        placedOrderDAO.create(order);
        PlacedOrder fromDb = placedOrderDAO.getById(order.getId());
        assertTrue(order.equals(fromDb));
    }

    //Как проверить констрайнты???
  /*  @Test(expected = DBException.class)
    @Transactional
    public void testCreateConstraints() throws Exception {
        order.setCustomerId(-4l);
        placedOrderDAO.create(order);
    }*/

    @Test
    @Transactional
    public void testGetById() throws Exception {
        placedOrderDAO.create(order);
        PlacedOrder fromDb = placedOrderDAO.getById(order.getId());
        assertNotNull(fromDb);
    }

    @Test
    @Transactional
    public void testGetByStatus() throws Exception {
        placedOrderDAO.create(order);
        List<PlacedOrder> ordersList = placedOrderDAO.getByStatus(order.getStatus());
       System.out.println(ordersList); //System.exit(0);
        assertFalse(ordersList.isEmpty());
        for (PlacedOrder o : ordersList) {
            assertEquals(order.getStatus(), o.getStatus());
        }
    }

    @Test
    @Transactional
    public void testGetByDeliveryType() throws Exception {
        placedOrderDAO.create(order);
        List<PlacedOrder> ordersList = placedOrderDAO.getByDeliveryType(order.getDeliveryType());
        assertFalse(ordersList.isEmpty());
        for (PlacedOrder o : ordersList) {
            assertEquals(order.getDeliveryType(), o.getDeliveryType());
        }
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        placedOrderDAO.create(order);
        customer.setOrders(null);
        placedOrderDAO.delete(order.getId());
        PlacedOrder  fromDb = placedOrderDAO.getById(order.getId());
        assertNull(fromDb);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        placedOrderDAO.create(order);
        BigDecimal newTotal = order.getTotal().add(new BigDecimal("45.30"));
        order.setTotal(newTotal);
        placedOrderDAO.update(order);
        PlacedOrder afterUpdate = placedOrderDAO.getById(order.getId());
        assertEquals(afterUpdate.getTotal(), newTotal);
    }
}
