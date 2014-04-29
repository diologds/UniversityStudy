package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.CustomerBuilder.createCustomer;
import static lv.javaguru.java2.ishop.database.builders.OrderedCommodityBuilder.createOrderedCommodity;
import static lv.javaguru.java2.ishop.database.builders.PlacedOrderBuilder.createPlacedOrder;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Ann on 24/03/14.
 */
public class OrderedCommodityDAOImplTest extends SpringContextTest {
    @Autowired
    private ClearDatabaseDAO dbCleaner;

    @Autowired
    @Qualifier("OrderedCommodityDAO_ORM")
    private OrderedCommodityDAO orderedCommodityDAO;

    @Autowired
    @Qualifier("CommodityDAO_ORM")
    private CommodityDAO commodityDAO;

    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;

    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;

    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;

    @Autowired
    @Qualifier("PlacedOrderDAO_ORM")
    private PlacedOrderDAO placedOrderDAO;

    private OrderedCommodity orderedCommodity;
    private OrderedCommodity someOrderedCommodity;
    private Customer customer;
    private PlacedOrder placedOrder;
    private Category category;
    private Producer producer;
    private Commodity commodity;

    private void createTestData()
    {
        category = createCategory()
                .withName("tablet")
                .build();
        producer =  createProducer()
                .withName("Apple")
                .withDescription("Premium")
                .build();
        commodity = createCommodity()
                .withName("Ipad5")
                .withPrice(new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN))
                .withDescription("Nice")
                .withBrand("IPad")
                .withRef("AAA")
                .withURL("http://www.apple.com/index.html")
                .build();
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
        placedOrder = createPlacedOrder()
                .withStatus(OrderStatus.inprogress)
                .withDeliveryType(OrderDeliveryType.inoffice)
                .withTotal(new BigDecimal("1200.00"))
                .build();
        orderedCommodity = createOrderedCommodity()
                .withPrice(new BigDecimal("600.00"))
                .withQuantity(2L)
                .build();
        someOrderedCommodity = createOrderedCommodity()
                .withPrice(new BigDecimal("600.00"))
                .withQuantity(20L)
                .build();

    }
    private void persistOrderedCommodity(OrderedCommodity orderedCommodity) throws Exception
    {
        categoryDAO.create(category);
        producerDAO.create(producer);
        commodity.setProducer(producer);
        commodity.setCategory(category);

        commodityDAO.create(commodity);
        orderedCommodity.setCommodity(commodity);

        customerDAO.create(customer);
        placedOrder.setCustomer(customer);
        placedOrderDAO.create(placedOrder);
        orderedCommodity.setPlacedOrder(placedOrder);

        orderedCommodityDAO.create(orderedCommodity);
    }
    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        createTestData();
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        assertNotNull(orderedCommodity.getOrderedCommodityID());

    }

    @Test
    @Transactional
    public void testGetByID() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        OrderedCommodity o = orderedCommodityDAO.getByID(orderedCommodity.getOrderedCommodityID());
        assertNotNull(o);
        assertEquals(orderedCommodity,o);
    }

    @Test
    @Transactional
    public void testGetOrderID() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        List<OrderedCommodity> orderedCommodities = orderedCommodityDAO.getOrderID(orderedCommodity
                .getPlacedOrder().getId());
        assertEquals(1, orderedCommodities.size());
        assertEquals(orderedCommodity,orderedCommodities.get(0));
    }

    @Test
    @Transactional
    public void testGetCommodityID() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        List<OrderedCommodity> orderedCommodities = orderedCommodityDAO.getCommodityID(orderedCommodity
                .getCommodity().getId());
        assertEquals(1, orderedCommodities.size());
        assertEquals(orderedCommodity,orderedCommodities.get(0));

    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        persistOrderedCommodity(someOrderedCommodity);
        List<OrderedCommodity> orderedCommodities = orderedCommodityDAO.getAll();
        assertNotNull(orderedCommodities);
        assertEquals(2, orderedCommodities.size());
        assertTrue(orderedCommodities.contains(orderedCommodity));
        assertTrue(orderedCommodities.contains(someOrderedCommodity));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        orderedCommodityDAO.delete(orderedCommodity.getOrderedCommodityID());
        OrderedCommodity o = orderedCommodityDAO.getByID(orderedCommodity.getOrderedCommodityID());
        assertNull(o);

    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        persistOrderedCommodity(orderedCommodity);
        orderedCommodity.setOrderedCommodityQuantity(someOrderedCommodity.getOrderedCommodityQuantity());
        orderedCommodity.setOrderedCommodityPrice(someOrderedCommodity.getOrderedCommodityPrice());
        orderedCommodityDAO.update(orderedCommodity);
        assertEquals(someOrderedCommodity.getOrderedCommodityPrice(), orderedCommodity.getOrderedCommodityPrice());
        assertEquals(someOrderedCommodity.getOrderedCommodityQuantity(), orderedCommodity.getOrderedCommodityQuantity());

    }
}
