package lv.javaguru.java2.ishop.database.orm;

/**
 * Created by Ann on 24/03/14.
 */
import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DBFiller extends SpringContextTest{
    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;
    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;
    @Autowired
    @Qualifier("CommodityDAO_ORM")
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("CommodityViewDAO_ORM")
    private CommodityViewDAO commodityViewDAO;
    @Autowired
    @Qualifier("CommodityStorageDAO_ORM")
    private CommodityStorageDAO commodityStorageDAO;
    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;
    @Autowired
    @Qualifier("PlacedOrderDAO_ORM")
    private PlacedOrderDAO placedOrderDAO;
    @Autowired
    @Qualifier("OrderedCommodityDAO_ORM")
    private OrderedCommodityDAO orderedCommodityDAO;
    @Autowired
    private ImageConverter imageLoader;


    private List<String> urlStrings = new ArrayList<String>();
    private List<Category> categories = new ArrayList<Category>();
    private List<Producer> producers = new ArrayList<Producer>();
    private List<Commodity> commodities = new ArrayList<Commodity>();

    private List<CommodityStorage> ipadStorage = new ArrayList<CommodityStorage>();
    private List<CommodityStorage> macBookStorage = new ArrayList<CommodityStorage>();
    private List<CommodityStorage> msiStorage = new ArrayList<CommodityStorage>();
    private List<CommodityStorage> lenovoStorage = new ArrayList<CommodityStorage>();

    private List<CommodityView> iPadViews = new ArrayList<CommodityView>();
    private List<CommodityView> macBookViews = new ArrayList<CommodityView>();
    private List<CommodityView> msiViews = new ArrayList<CommodityView>();
    private List<CommodityView> lenovoViews = new ArrayList<CommodityView>();
    private List<CommodityView> htcViews = new ArrayList<CommodityView>();

    private List<Customer> customers = new ArrayList<Customer>();
    private List<PlacedOrder> orders = new ArrayList<PlacedOrder>();
    private List<OrderedCommodity> orderedCommodities = new ArrayList<OrderedCommodity>();

    @Before
    public void setUp() throws Exception
    {
        dbCleaner.clear();
        prepareDataSet();


    }
    @Test
    @Transactional
    public void Test()throws Exception
    {
        fillDbTables();
        System.out.println("DB filled successfully!");
    }
    private void prepareDataSet() throws Exception
    {
        createCategories();
        createProducers();
        createCommodities();
        createCommodityViews();
        createCommodityStorage();
        createCustomers();
        createPlacedOrders();
        createOrderedCommodities();
    }
    private void createCategories()
    {
        categories.add(new Category("tablet"));
        categories.add(new Category("laptop"));
        categories.add(new Category("smartphone"));
    }
    private void createProducers()
    {
        producers.add(new Producer("Lenovo", "Just Good"));
        producers.add(new Producer("Apple", "Premium"));
        producers.add(new Producer("MSI", "Budget"));
        producers.add(new Producer("HTC", "Good"));
    }
    private void  createCommodities() throws Exception
    {
        urlStrings.add("http://www.apple.com/");
        urlStrings.add("http://www.msi.com/");
        urlStrings.add("http://www.lenovo.com/");
        urlStrings.add("http://www.htc.com/");

        commodities.add(new Commodity("Ipad5", new BigDecimal("800.00").setScale(2, RoundingMode.HALF_EVEN),
                "Nice", "iPad", "AAA", urlStrings.get(0)));
        commodities.add(new Commodity("MacBook 2013", new BigDecimal("2000.00").setScale(2, RoundingMode.HALF_EVEN),
                "Superb", "MacBook", "AAB", urlStrings.get(0)));

        commodities.add(new Commodity("msi gs70", new BigDecimal("700.00").setScale(2, RoundingMode.HALF_EVEN),
                "Budget Choice", "MSI", "BAA", urlStrings.get(1)));

        commodities.add(new Commodity("lenovo y510p", new BigDecimal("1200.00").setScale(2, RoundingMode.HALF_EVEN),
                "Fast", "Lenovo", "CAA", urlStrings.get(2)));

        commodities.add(new Commodity("HTC One S", new BigDecimal("300.00").setScale(2, RoundingMode.HALF_EVEN),
                "Affordable", "HTC", "DAA", urlStrings.get(3)));


    }
    private void createCommodityViews() throws Exception
    {
        iPadViews.add(new CommodityView(imageLoader.getBytes("ip1.jpg", ImageType.jpg),ImageType.jpg, false));
        iPadViews.add(new CommodityView(imageLoader.getBytes("ip2.jpg", ImageType.jpg),ImageType.jpg, false));
        iPadViews.add(new CommodityView(imageLoader.getBytes("ip3.jpg", ImageType.jpg),ImageType.jpg, false));
        iPadViews.add(new CommodityView(imageLoader.getBytes("ip4.jpg", ImageType.jpg),ImageType.jpg, false));

        macBookViews.add(new CommodityView(imageLoader.getBytes("ma1.jpg", ImageType.jpg),ImageType.jpg, false));
        macBookViews.add(new CommodityView(imageLoader.getBytes("ma2.jpg", ImageType.jpg),ImageType.jpg, false));
        macBookViews.add(new CommodityView(imageLoader.getBytes("ma3.jpg", ImageType.jpg),ImageType.jpg, false));

        msiViews.add(new CommodityView(imageLoader.getBytes("ms1.jpg", ImageType.jpg),ImageType.jpg, false));
        msiViews.add(new CommodityView(imageLoader.getBytes("ms2.jpg", ImageType.jpg),ImageType.jpg, false));

        lenovoViews.add(new CommodityView(imageLoader.getBytes("le1.jpg", ImageType.jpg),ImageType.jpg, false));
        lenovoViews.add(new CommodityView(imageLoader.getBytes("le2.jpg", ImageType.jpg),ImageType.jpg, false));

        htcViews.add(new CommodityView(imageLoader.getBytes("default.jpg", ImageType.jpg),ImageType.jpg, false));
    }
    private  void  createCommodityStorage()
    {
        ipadStorage.add(new CommodityStorage(StorageType.local, 10L));
        ipadStorage.add(new CommodityStorage(StorageType.remote, 100L));
        macBookStorage.add(new CommodityStorage(StorageType.local, 20L));
        macBookStorage.add(new CommodityStorage(StorageType.remote, 200L));
        msiStorage.add(new CommodityStorage(StorageType.local, 5L));
        msiStorage.add(new CommodityStorage(StorageType.remote, 12L));
        lenovoStorage.add(new CommodityStorage(StorageType.local, 5L));
        lenovoStorage.add(new CommodityStorage(StorageType.remote, 12L));
    }
    private void createCustomers()
    {
        customers.add(new Customer("j.bond@gmail.com","London","James","Bond","Paris","777",
                "TopSecret","007",true,false,false));

        customers.add(new Customer("s.holmes@gmail.com","London","Sherlock","Holmes","London, Baker St, 221","112",
                "SuperSmart","001",false,true,false));

        customers.add(new Customer("carlson@fictionhero.com",null,"Unknown","Carlson",null,"000",
                "VerySimple","111",true,false,true));
    }
    private void createPlacedOrders()
    {
        orders.add(new PlacedOrder(new BigDecimal("9500.00"), OrderStatus.ready, OrderDeliveryType.tocustomeraddress));
        orders.add(new PlacedOrder(new BigDecimal("5000.00"), OrderStatus.neworder, OrderDeliveryType.inoffice));
        orders.add(new PlacedOrder(new BigDecimal("12000.20"), OrderStatus.inprogress, OrderDeliveryType.inoffice));

    }
    private void createOrderedCommodities()
    {
        orderedCommodities.add(new OrderedCommodity(new BigDecimal("2000.00"), 3L));
        orderedCommodities.add(new OrderedCommodity(new BigDecimal("700.00"), 5L));
        orderedCommodities.add(new OrderedCommodity(new BigDecimal("700.00"), 2L));
        orderedCommodities.add(new OrderedCommodity(new BigDecimal("1200.00"), 3L));
        orderedCommodities.add(new OrderedCommodity(new BigDecimal("1200.00"), 10L));
    }

    private void fillDbTables() throws Exception
    {
        fillCategory();
        fillProducer();
        fillCommodity();
        fillCommodityView();
        fillCommodityStorage();
        fillCustomer();
        fillPlacedOrder();
        fillOrderedCommodity();
    }
    private void fillCategory() throws Exception
    {
        for (Category cat : categories)
            categoryDAO.create(cat);
    }
    private void fillProducer() throws Exception
    {
        for (Producer p : producers)
            producerDAO.create(p);
    }
    private void fillCommodity() throws Exception
    {
        commodities.get(0).setProducer(producerDAO.getByName("Apple"));
        commodities.get(0).setCategory(categoryDAO.getByName("tablet"));

        commodities.get(1).setProducer(producerDAO.getByName("Apple"));
        commodities.get(1).setCategory(categoryDAO.getByName("laptop"));

        commodities.get(2).setProducer(producerDAO.getByName("MSI"));
        commodities.get(2).setCategory(categoryDAO.getByName("laptop"));

        commodities.get(3).setProducer(producerDAO.getByName("Lenovo"));
        commodities.get(3).setCategory(categoryDAO.getByName("laptop"));

        commodities.get(4).setProducer(producerDAO.getByName("HTC"));
        commodities.get(4).setCategory(categoryDAO.getByName("smartphone"));

        for (Commodity c : commodities)
            commodityDAO.create(c);
    }
    private void fillCommodityView() throws Exception
    {
        for (CommodityView v: iPadViews)
        {
            v.setCommodity(commodityDAO.getByName("Ipad5"));
            commodityViewDAO.create(v);
        }
        for (CommodityView v: macBookViews)
        {
            v.setCommodity(commodityDAO.getByName("MacBook 2013"));
            commodityViewDAO.create(v);
        }
        for (CommodityView v: msiViews)
        {
            v.setCommodity(commodityDAO.getByName("msi gs70"));
            commodityViewDAO.create(v);
        }
        for (CommodityView v: lenovoViews)
        {
            v.setCommodity(commodityDAO.getByName("lenovo y510p"));
            commodityViewDAO.create(v);
        }
    }
    private void fillCommodityStorage() throws Exception
    {
        ipadStorage.get(0).setCommodity(commodityDAO.getByName("Ipad5"));
        ipadStorage.get(1).setCommodity(commodityDAO.getByName("Ipad5"));
        macBookStorage.get(0).setCommodity(commodityDAO.getByName("MacBook 2013"));
        macBookStorage.get(1).setCommodity(commodityDAO.getByName("MacBook 2013"));
        msiStorage.get(0).setCommodity(commodityDAO.getByName("msi gs70"));
        msiStorage.get(1).setCommodity(commodityDAO.getByName("msi gs70"));
        lenovoStorage.get(0).setCommodity(commodityDAO.getByName("lenovo y510p"));
        lenovoStorage.get(1).setCommodity(commodityDAO.getByName("lenovo y510p"));

        for (CommodityStorage s : ipadStorage)
            commodityStorageDAO.create(s);
        for (CommodityStorage s : macBookStorage)
            commodityStorageDAO.create(s);
        for (CommodityStorage s : lenovoStorage)
            commodityStorageDAO.create(s);
    }
    private void fillCustomer() throws Exception
    {
        for (Customer c: customers)
            customerDAO.create(c);


    }
    private void fillPlacedOrder() throws Exception
    {
        orders.get(0).setCustomer(customerDAO.getByPersonalCode("007"));
        orders.get(1).setCustomer(customerDAO.getByPersonalCode("001"));
        orders.get(2).setCustomer(customerDAO.getByPersonalCode("111"));

        for (PlacedOrder o: orders)
            placedOrderDAO.create(o);
    }
    private void fillOrderedCommodity() throws Exception
    {
        List<PlacedOrder> orderList = placedOrderDAO.getList();

        orderedCommodities.get(0).setCommodity(commodityDAO.getByName("MacBook 2013"));
        orderedCommodities.get(0).setPlacedOrder(orderList.get(0));
        orderedCommodities.get(1).setCommodity(commodityDAO.getByName("msi gs70"));
        orderedCommodities.get(1).setPlacedOrder(orderList.get(0));
        orderedCommodities.get(2).setCommodity(commodityDAO.getByName("msi gs70"));
        orderedCommodities.get(2).setPlacedOrder(orderList.get(1));
        orderedCommodities.get(3).setCommodity(commodityDAO.getByName("lenovo y510p"));
        orderedCommodities.get(3).setPlacedOrder(orderList.get(1));
        orderedCommodities.get(4).setCommodity(commodityDAO.getByName("lenovo y510p"));
        orderedCommodities.get(4).setPlacedOrder(orderList.get(2));

        for (OrderedCommodity oc: orderedCommodities)
            orderedCommodityDAO.create(oc);
    }


}

