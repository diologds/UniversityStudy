package lv.javaguru.java2.ishop.database.orm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.database.jdbc.ClearDatabaseDAO;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Producer;
import lv.javaguru.java2.ishop.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import static lv.javaguru.java2.ishop.database.builders.CategoryBuilder.createCategory;
import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static lv.javaguru.java2.ishop.database.builders.ProducerBuilder.createProducer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class CategoryDAOImplTest extends SpringContextTest{

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
    private ClearDatabaseDAO dbCleaner;

    private Category category;
    private Producer producer;
    private List<Commodity> commodities = new ArrayList<Commodity>();

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();
    }


    private void prepareTestDataSet()
    {
        category = createCategory()
                .withName("Laptop")
                .build();
        producer = createProducer()
                .withName("D")
                .withDescription("DD")
                .build();
        commodities.add( createCommodity()
                .withName("Ipad4")
                .withPrice(new BigDecimal("600.00").setScale(2, RoundingMode.HALF_EVEN))
                .withDescription("Nice")
                .withBrand("IPad")
                .withRef("AAA")
                .withURL("http://www.apple.com/index.html")
                .build());

    }
    private void persistCategory() throws DBException
    {
        producerDAO.create(producer);
        commodities.get(0).setProducer(producer);
        commodities.get(0).setCategory(category);
        category.setCommodities(commodities);

        categoryDAO.create(category);

        System.out.println("persistCategory");
    }

    @Test
    @Transactional
    public void testCreateCategory() throws DBException {

        categoryDAO.create(category);
        assertNotNull(category.getId());

    }
    @Test
    @Transactional
    public void testCascadeCreate() throws DBException {
        persistCategory();

        assertNotNull(category.getId());
        assertNotNull(commodities.get(0).getProducer().getId());

        System.out.println(category.getCommodities().get(0));

        assertNotNull(category.getCommodities().get(0).getId());
        Commodity c = commodityDAO.getByName(commodities.get(0).getName());
        assertNotNull(c.getId());

        System.out.println(category.getCommodities().get(0));
        assertEquals(category.getCommodities().get(0), c);

    }

    @Test
    @Transactional
    public void testGetById() throws DBException {
        categoryDAO.create(category);
        Category cat = categoryDAO.getById(category.getId());
        assertNotNull(cat);
        assertTrue(category == cat);
    }

    @Test
    @Transactional
    public void testGetByName() throws DBException {
        categoryDAO.create(category);
        Category cat = categoryDAO.getByName(category.getName());
        assertNotNull(cat);
        assertTrue(category == cat);
    }
    @Test
    @Transactional
    public void testGetCommodities() throws DBException {
        persistCategory();
        List<Commodity> commodityList = category.getCommodities();
        assertEquals(1,commodityList.size());

    }

    @Test
    @Transactional
    public void testGetAll() throws DBException {
        Category category1 = new Category("WWW_1");
        categoryDAO.create(category1);
        Category category2 = new Category("WWW_2");
        categoryDAO.create(category2);

        List<Category> categories = categoryDAO.getAll();
        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
    }

    @Test
    @Transactional
    public void testDelete() throws DBException {
        categoryDAO.create(category);
        categoryDAO.delete(category.getId());
        Category cat = categoryDAO.getById(category.getId());
        assertNull(cat);
    }
    @Test
    @Transactional
    public void testCascadeDelete() throws DBException
    {
        persistCategory();

        categoryDAO.delete(category.getId());

        Category cat = categoryDAO.getById(category.getId());
        assertNull(cat);

        Commodity c = commodityDAO.getById(commodities.get(0).getId());
        assertNull(c);

    }

    @Test
    @Transactional
    public void testUpdate() throws DBException {
        categoryDAO.create(category);
        category.setName("WWW_QQQ");
        categoryDAO.update(category);
        assertEquals("WWW_QQQ", category.getName());
    }
    @Test
    @Transactional
    public void testCascadeUpdate() throws DBException {

       persistCategory();

        category.getCommodities().get(0).setName("DA");
        category.getCommodities().get(0).setDescription("DAAA");
        category.getCommodities().get(0).setPrice(new BigDecimal(100));
        categoryDAO.update(category);
        Commodity c = commodityDAO.getById(commodities.get(0).getId());
        assertTrue(c.equals(commodities.get(0)));
    }

}
