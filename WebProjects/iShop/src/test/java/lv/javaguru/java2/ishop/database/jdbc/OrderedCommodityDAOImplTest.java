package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.domain.*;
import lv.javaguru.java2.ishop.database.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class OrderedCommodityDAOImplTest  extends SpringContextTest
{
    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("CommodityDAO_JDBC")
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;
    @Autowired
    @Qualifier("PlacedOrderDAO_JDBC")
    private PlacedOrderDAO placedOrderDAO;
    @Autowired
    @Qualifier("CustomerDAO_JDBC")
    private CustomerDAO customerDAO;
    @Autowired
    @Qualifier("OrderedCommodityDAO_JDBC")
    private OrderedCommodityDAO  orderedCommodityDAO;

    private Customer customer;
    private PlacedOrder placeOrder;
    private Commodity commodity;
    private OrderedCommodity orderedCommodity;

    private Category[] categories = new Category[2];
    private Producer[] producers = new Producer[3];
    private String[] urlStrings = new String[3];

    private void createCustomer() {
        customer = new Customer("nikolay@mail.lv", "Riga", "Nikolay", "Lapshin", "Riga, Peldu 15", "12345678"
                , "password", "123456-12345", true,false,false);
    }

    private void createPlaceOrder() {
        placeOrder = new PlacedOrder(BigDecimal.valueOf(23.5d), OrderStatus.ready, OrderDeliveryType.tocustomeraddress);
    }

    private void createCategories() {
        categories[0] = new Category("tablet");
        categories[1] = new Category("laptop");
    }

    private void createProducers() {
        producers[0] = new Producer("Lenovo", "Just Good");
        producers[1] = new Producer("Apple", "Premium");
        producers[2] = new Producer("MSI", "Budget");
    }

    private void createCommodities() throws Exception {
        urlStrings[0] = " http://www.apple.com/index.html";
        urlStrings[1] = " http://www.apple.com/index.html";
        urlStrings[2] = " http://www.msi.com/index.html";

        commodity = new Commodity("Ipad4", new BigDecimal(600), "Nice", "Ipad", "AAA", urlStrings[0]);

    }

    private void createOrderCommodity() throws Exception {
        orderedCommodity = new OrderedCommodity(BigDecimal.valueOf(200.00).setScale(2, RoundingMode.HALF_EVEN), 2l);
    }

    private void fillDbTables() throws Exception {
        for (Category cat : categories)
            categoryDAO.create(cat);

        for (Producer p : producers)
            producerDAO.create(p);

        Long idProducer = producerDAO.getByName("Apple").getId();
        Long idCategory = categoryDAO.getByName("Tablet").getId();

        commodity.setIdProducer(idProducer);
        commodity.setIdCategory(idCategory);
        commodityDAO.create(commodity);
        commodity = commodityDAO.getByName("Ipad4");

        customerDAO.create(customer);
        customer = customerDAO.getByPersonalCode(customer.getPersonalCode());

        placeOrder.setCustomerId(customer.getId());
        placedOrderDAO.create(placeOrder);
        placeOrder = placedOrderDAO.getByCustomerId(placeOrder.getCustomerId()).get(0);

        orderedCommodity.setPlaceOrderID(placeOrder.getId());
        orderedCommodity.setCommodityID(commodity.getId());
    }

    private  void prepareTestDataSet() throws Exception {
        createCategories();
        createProducers();
        createCommodities();
        createCustomer();
        createPlaceOrder();
        createOrderCommodity();
    }

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();
        fillDbTables();
    }


    @Test
    public void testCreate() throws Exception {
        orderedCommodityDAO.create(orderedCommodity);
        List<OrderedCommodity> orderedCommodityList = orderedCommodityDAO.getOrderID(orderedCommodity.getPlaceOrderID());
        orderedCommodity.setOrderedCommodityID(orderedCommodityList.get(0).getOrderedCommodityID());
        assertTrue(orderedCommodityList.contains(orderedCommodity));
    }

    @Test
    public void testGetByCommodityID() throws Exception {
        orderedCommodityDAO.create(orderedCommodity);
        List<OrderedCommodity> orderedCommodityList = orderedCommodityDAO.getCommodityID(orderedCommodity.getCommodityID());
        orderedCommodity.setOrderedCommodityID(orderedCommodityList.get(0).getOrderedCommodityID());
        assertTrue(orderedCommodityList.contains(orderedCommodity));
    }

    @Test
    public void testDelete() throws Exception {
        orderedCommodityDAO.create(orderedCommodity);
        List<OrderedCommodity> orderedCommodityList = orderedCommodityDAO.getOrderID(placeOrder.getId());
        for (OrderedCommodity test : orderedCommodityList)
            orderedCommodityDAO.delete(test.getOrderedCommodityID());
        assertThat("Expected empty List ", orderedCommodityDAO.getOrderID(placeOrder.getId()).isEmpty(), is(true));

    }

    @Test
    public void testUpdate() throws Exception {
        orderedCommodityDAO.create(orderedCommodity);
        orderedCommodity.setOrderedCommodityQuantity(10000l);
        orderedCommodityDAO.update(orderedCommodity);
        OrderedCommodity test = orderedCommodityDAO.getByID(orderedCommodity.getOrderedCommodityID());
        assertEquals(orderedCommodity, test);
    }
}
