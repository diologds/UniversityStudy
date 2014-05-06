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
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.CustomerBuilder.createCustomer;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;
import static lv.javaguru.java2.ishop.database.builders.CommodityStorageBuilder.createCommodityStorage;
import static lv.javaguru.java2.ishop.database.builders.CommodityViewBuilder.createCommodityView;
import static lv.javaguru.java2.ishop.database.builders.OrderedCommodityBuilder.createOrderedCommodity;
import static lv.javaguru.java2.ishop.database.builders.WishedCommodityBuilder.createWishedCommodity;
import static lv.javaguru.java2.ishop.database.builders.PlacedOrderBuilder.createPlacedOrder;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

/**
 * Created by Rita on 14.14.3.
 */

public class CommodityDAOImplTest extends SpringContextTest{
    @Autowired
    private ClearDatabaseDAO dbCleaner;

    @Qualifier("CommodityDAO_ORM")
    @Autowired private CommodityDAO commodityDAO;

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

    @Autowired
    @Qualifier("CommodityStorageDAO_ORM")
    private CommodityStorageDAO commodityStorageDAO;

    @Autowired
    @Qualifier("CommodityViewDAO_ORM")
    private CommodityViewDAO commodityViewDAO;

    @Autowired
    @Qualifier("OrderedCommodityDAO_ORM")
    private OrderedCommodityDAO orderedCommodityDAO;

    @Autowired
    @Qualifier("WishedCommodityDAO_ORM")
    private WishedCommodityDAO wishedCommodityDAO;


    private List<Commodity> commodities = new ArrayList<Commodity>();
    private List<CommodityView> views = new ArrayList<CommodityView>();
    private List<CommodityStorage> storages = new ArrayList<CommodityStorage>();
    private List<OrderedCommodity> orderedCommodities = new ArrayList<OrderedCommodity>();
    private List<WishedCommodity> wishedCommodities = new ArrayList<WishedCommodity>();

    private Category category;
    private Producer producer;
    private Customer customer;
    private PlacedOrder placedOrder;

    private  void createCommodities() throws Exception
    {
        commodities.add( createCommodity()

                .withName("Ipad4")
                .withPrice(new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN))
                .withDescription("Nice")
                .withBrand("IPad")
                .withRef("AAA1")
                .withURL("http://www.apple.com/index.html")
                .build());

        commodities.add( createCommodity()

                .withName("Ipad5")
                .withPrice(new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN))
                .withDescription("Nice")
                .withBrand("IPad")
                .withRef("AAA2")
                .withURL("http://www.apple.com/index.html")
                .build());
    }
    private void createViews()
    {
        ImageConverter converter = new ImageConverter();

        views.add( createCommodityView()

                   .withPhoto(converter.getBytes("ip1.jpg", ImageType.jpg))
                   .withPhotoType(ImageType.jpg)
                   .withGalleryPhoto(false)
                   .build()
                   );
        views.add( createCommodityView()

                .withPhoto(converter.getBytes("ip2.jpg", ImageType.jpg))
                .withPhotoType(ImageType.jpg)
                .withGalleryPhoto(false)
                .build()
        );

    }
    private void createStorages()
    {
        storages.add( createCommodityStorage()
                        .withType(StorageType.local)
                        .withQuantity(55L)
                        .build()
                    );
        storages.add( createCommodityStorage()
                .withType(StorageType.remote)
                .withQuantity(100L)
                .build()
        );
    }
    private void createWishedCommodities()
    {
        wishedCommodities.add(createWishedCommodity()
                .build());
    }
    private void createOrderedCommodities()
    {
        orderedCommodities.add(createOrderedCommodity()
                .withPrice(new BigDecimal("600.00"))
                .withQuantity(2L)
                .build());

    }

    private void prepareTestDataSet() throws Exception {

        category = createCategory()
                .withName("tablet")
                .build();
        producer =  createProducer()
                .withName("Apple")
                .withDescription("Premium")
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

        createCommodities();
        createViews();
        createStorages();
        createOrderedCommodities();
        createWishedCommodities();
    }

    private void persistCommodities() throws DBException
    {
        categoryDAO.create(category);
        producerDAO.create(producer);
        customerDAO.create(customer);

        placedOrder.setCustomer(customer);
        placedOrderDAO.create(placedOrder);

        orderedCommodities.get(0).setPlacedOrder(placedOrder);
        wishedCommodities.get(0).setCustomer(customer);

        commodities.get(1).setCommodityViews(views);
        commodities.get(1).setCommodityStorages(storages);
        commodities.get(1).setOrderedCommodities(orderedCommodities);
        commodities.get(1).setWishedCommodities(wishedCommodities);

        for (CommodityStorage s: storages)
        {
            s.setCommodity(commodities.get(1));
        }
        for (CommodityView v: views)
        {
            v.setCommodity(commodities.get(1));
        }
        for (OrderedCommodity o: orderedCommodities)
        {
            o.setCommodity(commodities.get(1));
        }
        for (WishedCommodity w: wishedCommodities)
        {
            w.setCommodity(commodities.get(1));
        }

        for (Commodity c: commodities)
        {
            c.setCategory(category);
            c.setProducer(producer);
            commodityDAO.create(c);
        }

        System.out.println("persistCommodities");

    }


    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {

        persistCommodities();
        assertNotNull(customer.getId());

        assertNotNull(commodities.get(0).getId());
        assertNotNull(commodities.get(1).getId());

        Commodity c = commodityDAO.getById(commodities.get(0).getId());

        assertEquals(commodities.get(0).getName(), c.getName());
        assertEquals(commodities.get(0).getPrice(), c.getPrice());
        assertEquals(commodities.get(0).getDescription(), c.getDescription());
        assertEquals(commodities.get(0).getBrand(), c.getBrand());
        assertEquals(commodities.get(0).getRef(), c.getRef());
        assertEquals(commodities.get(0).getUrl(), c.getUrl());

        assertNotNull(c.getProducer());
        assertEquals(c.getProducer(), producer);
        assertNotNull(c.getCategory());
        assertEquals(c.getCategory(), category);
    }
    @Test
    @Transactional
    public void testCascadeCreate() throws Exception {
        persistCommodities();

        assertNotNull(commodities.get(1).getCommodityViews().get(0).getId());
        assertNotNull(commodities.get(1).getCommodityViews().get(1).getId());

        assertNotNull(commodities.get(1).getCommodityStorages().get(0).getId());
        assertNotNull(commodities.get(1).getCommodityStorages().get(1).getId());

        assertNotNull(commodities.get(1).getOrderedCommodities().get(0).getOrderedCommodityID());
        assertNotNull(commodities.get(1).getWishedCommodities().get(0).getId());

        CommodityView v = commodityViewDAO.getById(commodities.get(1).getCommodityViews().get(0).getId());
        CommodityStorage s = commodityStorageDAO.getById(commodities.get(1).getCommodityStorages().get(0).getId());
        WishedCommodity w = wishedCommodityDAO.getById(commodities.get(1).getWishedCommodities().get(0).getId());
        OrderedCommodity o = orderedCommodityDAO.getByID(commodities.get(1).getOrderedCommodities().get(0).getOrderedCommodityID());

        assertNotNull(v);
        assertNotNull(s);
        assertNotNull(w);
        assertNotNull(o);
        assertEquals(commodities.get(1).getCommodityViews().get(0),v);
        assertEquals(commodities.get(1).getCommodityStorages().get(0),s);
        assertEquals(commodities.get(1).getWishedCommodities().get(0),w);
        assertEquals(commodities.get(1).getOrderedCommodities().get(0),o);
    }
    

    @Test
    @Transactional
    public void testGetByName() throws Exception
    {
        persistCommodities();
        assertEquals("Ipad4", commodityDAO.getByName("Ipad4").getName());
        assertEquals("Ipad5", commodityDAO.getByName("Ipad5").getName());

    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        persistCommodities();
        List<Commodity> commoditiesFromDb = commodityDAO.getAll();
        assertEquals(commodities.size(), commoditiesFromDb.size());

    }
    @Test
    @Transactional
    public void testDelete() throws Exception {
        persistCommodities();

        long id = commodityDAO.getByName("Ipad5").getId();
        commodityDAO.delete(id);
        assertNull(commodityDAO.getById(id));

    }
    @Test
    @Transactional
    public void testCascadeDelete()throws Exception {
        persistCommodities();
        commodityDAO.delete(commodities.get(1).getId());
        Commodity c  = commodityDAO.getById(commodities.get(1).getId());
        assertNull(c);
        CommodityStorage s = commodityStorageDAO.getById(commodities.get(1).getCommodityStorages().get(0).getId() );
        assertNull(s);
        CommodityView v = commodityViewDAO.getById(commodities.get(1).getCommodityViews().get(0).getId());
        assertNull(v);
        OrderedCommodity o = orderedCommodityDAO.getByID(commodities.get(1).getOrderedCommodities().get(0).getOrderedCommodityID());
        assertNull(o);
        WishedCommodity w = wishedCommodityDAO.getById(commodities.get(1).getWishedCommodities().get(0).getId());
        assertNull(w);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {

        persistCommodities();

        Commodity c = commodityDAO.getByName("Ipad4");
        c.setName("Ipad2");
        commodityDAO.update(c);
        Commodity c1 = commodityDAO.getById(c.getId());
        assertEquals(c.getName(),c1.getName());
    }
    @Test
    @Transactional
    public void testCascadeUpdate()throws Exception {
        ImageConverter converter = new ImageConverter();

        persistCommodities();
        commodities.get(1).getCommodityStorages().get(0).setQuantity(56L);
        commodities.get(1).getCommodityStorages().get(0).setType(StorageType.remote);
        commodities.get(1).getCommodityViews().get(0).setCommodityPhoto(converter.getBytes("ip3.jpg", ImageType.jpg));
        commodities.get(1).getCommodityViews().get(0).setGalleryPhoto(true);
        commodities.get(1).getCommodityViews().get(0).setCommodityPhotoType(ImageType.bmp);
        commodities.get(1).getOrderedCommodities().get(0).setOrderedCommodityQuantity(10L);

        commodityDAO.update(commodities.get(1));

        CommodityView v = commodityViewDAO.getById(commodities.get(1).getCommodityViews().get(0).getId());
        CommodityStorage s = commodityStorageDAO.getById(commodities.get(1).getCommodityStorages().get(0).getId());
        OrderedCommodity o = orderedCommodityDAO.getByID(commodities.get(1).getOrderedCommodities().get(0).getOrderedCommodityID());

        assertEquals(commodities.get(1).getCommodityViews().get(0),v);
        assertEquals(commodities.get(1).getCommodityStorages().get(0),s);
        assertEquals(commodities.get(1).getOrderedCommodities().get(0),o);

    }

    @Test
    @Transactional
    public void testSearchByName() throws Exception {
        persistCommodities();

        List<Commodity> commoditiesFromDB = commodityDAO.searchByName("Ip");
        assertNotNull(commoditiesFromDB);
    }

    @Test
    @Transactional
    public void testGetByCategory() throws Exception {
        persistCommodities();

        Commodity commodity = commodityDAO.getByName(commodities.get(0).getName());
        List<Commodity> commoditiesFromDB = commodityDAO.getByCategory(commodities.get(0).getCategory().getId());
        assertFalse(commoditiesFromDB.isEmpty());
        for (Commodity c: commoditiesFromDB){
            assertEquals(commodities.get(0).getCategory(), c.getCategory());
        }
    }

}
