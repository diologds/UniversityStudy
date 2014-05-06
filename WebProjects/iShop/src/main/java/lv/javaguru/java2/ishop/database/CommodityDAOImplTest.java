package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Producer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Ann on 08/02/14.
 */
public class CommodityDAOImplTest  extends SpringContextTest {

    @Autowired private ClearDatabaseDAO dbCleaner;
    @Qualifier("CommodityDAO_JDBC")
    @Autowired private  CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private  CategoryDAO categoryDAO;

    private Commodity[] commodities = new Commodity[2];
    private Category[] categories = new Category[2];
    private Producer[] producers = new Producer[3];

    private Commodity[] commoditiesRetrieved = new Commodity[2];
    private Commodity someCommodity = null;

    private static String[] urlStrings = new String[3];


    private  void createCategories() {
        categories[0] = new Category("tablet");
        categories[1] = new Category("laptop");

    }

    private  void createProducers() {
        producers[0] = new Producer("Lenovo", "Just Good");
        producers[1] = new Producer("Apple", "Premium");
        producers[2] = new Producer("MSI", "Budget");

    }

    private  void createCommodities() throws Exception {
        urlStrings[0] = " http://www.apple.com/index.html";
        urlStrings[1] = " http://www.apple.com/index.html";
        urlStrings[2] = " http://www.msi.com/index.html";

        commodities[0] = new Commodity("Ipad4", new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN),
                "Nice", "Ipad", "AAA", urlStrings[0]);
        commodities[1] = new Commodity("Ipad5", new BigDecimal("800.00").setScale(2, RoundingMode.HALF_EVEN),
                "Superb", "Ipad", "AAB", urlStrings[1]);

        someCommodity = new Commodity("Ipad3", new BigDecimal("400.00").setScale(2, RoundingMode.HALF_EVEN),
                "Old model", "Ipad", "AAC", urlStrings[0]);


    }

    private void prepareTestDataSet() throws Exception {
        createCategories();
        createProducers();
        createCommodities();

    }

    private  void fillDbTables() throws Exception {
        for (Category cat : categories)
            categoryDAO.create(cat);

        for (Producer p : producers)
            producerDAO.create(p);

        Long idProducer = producerDAO.getByName("Apple").getId();
        Long idCategory = categoryDAO.getByName("Tablet").getId();


        for (Commodity c : commodities) {
            c.setIdProducer(idProducer);
            c.setIdCategory(idCategory);
        }

        for (Commodity c : commodities)
            commodityDAO.create(c);

    }

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();
        fillDbTables();

    }

    @Test
    public void testCreate() throws Exception {
        Commodity c = commodityDAO.getByName(commodities[0].getName());

        assertEquals(commodities[0].getName(), c.getName());
        assertEquals(commodities[0].getPrice(), c.getPrice());
        assertEquals(commodities[0].getDescription(), c.getDescription());
        assertEquals(commodities[0].getBrand(), c.getBrand());
        assertEquals(commodities[0].getRef(), c.getRef());
        assertEquals(commodities[0].getUrl(), c.getUrl());

    }

    @Test
    public void testGetByName() throws Exception {

        assertEquals("Ipad4", commodityDAO.getByName("Ipad4").getName());
        assertEquals("Ipad5", commodityDAO.getByName("Ipad5").getName());

    }

    @Test
    public void testGetAll() throws Exception {
        List<Commodity> commoditiesFromDb = commodityDAO.getAll();
        assertEquals(commodities.length, commoditiesFromDb.size());

    }

    @Test
    public void testDelete() throws Exception {
        long id = commodityDAO.getByName("Ipad5").getId();
        commodityDAO.delete(id);
        assertNull(commodityDAO.getById(id));

    }

    @Test
    public void testUpdate() throws Exception {
        Commodity c = commodityDAO.getByName("Ipad4");
        long id = c.getId();
        Long idProducer = c.getIdProducer();
        Long idCategory = c.getIdCategory();

        someCommodity.setId(id);
        someCommodity.setIdProducer(idProducer);
        someCommodity.setIdCategory(idCategory);

        commodityDAO.update(someCommodity);
        c = commodityDAO.getById(id);

        assertEquals(someCommodity, c);

    }
}
