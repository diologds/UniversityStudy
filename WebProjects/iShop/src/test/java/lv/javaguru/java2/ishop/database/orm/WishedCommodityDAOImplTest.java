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
import java.util.ArrayList;
import java.util.List;


import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CustomerBuilder.createCustomer;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;
import static lv.javaguru.java2.ishop.database.builders.WishedCommodityBuilder.createWishedCommodity;

import static org.junit.Assert.*;

public class WishedCommodityDAOImplTest extends SpringContextTest{
    @Autowired
    private ClearDatabaseDAO dbCleaner;

    @Autowired
    @Qualifier("WishedCommodityDAO_ORM")
    private WishedCommodityDAO wishedCommodityDAO;

    @Autowired
    @Qualifier("CommodityDAO_ORM")
    private CommodityDAO commodityDAO;

    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;

    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;

    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;

    private List<Commodity> commodities = new ArrayList<Commodity>();
    private List<Customer> customers = new ArrayList<Customer>();

    private WishedCommodity wishedCommodity;
    private Commodity commodity;
    private Customer customer;
    private Category category;
    private Producer producer;

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        createData();
    }

    private void createData() throws Exception {

        category = createCategory()
                .withName("tablet")
                .build();
        producer =  createProducer()
                .withName("Apple")
                .withDescription("Premium")
                .build();
       commodity = (createCommodity()
                .withName("Ipad Air")
                .withPrice(new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN))
                .withDescription("Nice")
                .withBrand("IPad")
                .withRef("AAA")
                .withURL("http://www.apple.com/index.html")
                .build());
        customer = (createCustomer()
                .withName("John")
                .withSurname("Madison")
                .withPersonalCode("131283-22400")
                .withEmail("martin34@gmail.com")
                .withAddress("Pasta iela 18-3, Tukums, LV-1002")
                .withDeliveryAddress("Pasta iela 18-3, Tukums, LV-1002")
                .withPhone("38583837")
                .withInfoToMail(false)
                .withPassword("qwerty")
                .build()
        );

        wishedCommodity = createWishedCommodity().build();

    }
    private void persistWishedCommodity() throws Exception
    {
        categoryDAO.create(category);
        producerDAO.create(producer);
        commodity.setProducer(producer);
        commodity.setCategory(category);

        commodityDAO.create(commodity);

        customerDAO.create(customer);
        wishedCommodity.setCustomer(customer);
        wishedCommodity.setCommodity(commodity);
        wishedCommodityDAO.create(wishedCommodity);
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        persistWishedCommodity();
        assertNotNull(wishedCommodity.getId());
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        persistWishedCommodity();
        WishedCommodity wishedCommodity = wishedCommodityDAO.getAll().get(0);
        Long id = wishedCommodity.getId();
        assertNotNull(wishedCommodityDAO.getById(id));
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        persistWishedCommodity();
//        assertNotNull(wishedCommodityDAO.getAll());
    }

    @Test
    @Transactional
    public void testGetByCustomer() throws Exception {
        persistWishedCommodity();
    }

    @Test
    @Transactional
    public void testGetByCommodity() throws Exception {
        persistWishedCommodity();
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        persistWishedCommodity();
        List<WishedCommodity> wishedCommodities = wishedCommodityDAO.getAll();
        int sizeBeforeDelete = wishedCommodities.size();
        WishedCommodity wishedCommodity = wishedCommodities.get(0);
        Long id = wishedCommodity.getId();
        wishedCommodityDAO.delete(id);

        wishedCommodities = wishedCommodityDAO.getAll();
        int sizeAfterDelete = wishedCommodities.size();

        assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        persistWishedCommodity();
        WishedCommodity wishedCommodity = wishedCommodityDAO.getAll().get(0);
        Long id = wishedCommodity.getId();
        String newAddress = "Stacijas iela 8, Riga";
        wishedCommodity.getCustomer().setAddress(newAddress);

        wishedCommodityDAO.update(wishedCommodity);
        wishedCommodity = wishedCommodityDAO.getById(id);
        String actualAddress = wishedCommodity.getCustomer().getAddress();

        assertEquals(newAddress, actualAddress);
    }
}
