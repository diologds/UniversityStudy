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
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CommodityViewDAOImplTest extends SpringContextTest {

    @Autowired
    @Qualifier("CommodityViewDAO_ORM")
    private CommodityViewDAO commodityViewDAO;
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
    private ImageConverter imageLoader;
    @Autowired
    private ClearDatabaseDAO clearDatabaseDAO;

    private CommodityView commodityView;

    private void createData() throws Exception  {
        Category category = new Category("Tablet");
        categoryDAO.create(category);

        Producer producer = new Producer("Lenovo", "Just Good");
        producerDAO.create(producer);

        Producer producerFromDB = producerDAO.getByName("Lenovo");
        Category categoryFromDB = categoryDAO.getByName("Tablet");
        Commodity commodity = new Commodity(producerFromDB, categoryFromDB, "Ipad4",new BigDecimal(600),
                "Nice","Ipad","AAA","http://www.apple.com/");

        commodityDAO.create(commodity);

        Commodity receivedCommodity=commodityDAO.getByName("Ipad4");
        commodityView = new CommodityView(receivedCommodity,imageLoader.getBytes("htc.jpg" , ImageType.jpg)
                , ImageType.bmp, false);
    }


    @Before
    public void setUp() throws Exception {
        clearDatabaseDAO.clear();
        createData();
    }

   @Test
    @Transactional
    public void testCreate() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByIdCommodity(commodityView.getCommodity().getId());
        commodityView.setId(c.get(0).getId());
        assertEquals(c.get(0),commodityView);
    }

    @Test
    @Transactional
    public void testGetAll() throws DBException {
        commodityViewDAO.create(commodityView);
        List<CommodityView> commodityViews = commodityViewDAO.getAll();
        assertNotNull(commodityViews);
        assertEquals(1, commodityViews.size());
        assertTrue(commodityViews.contains(commodityView));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByIdCommodity(commodityView.getCommodity().getId());
        for (CommodityView test : c)
            commodityViewDAO.delete(test.getId());
        assertThat("Expected empty List ",
                commodityViewDAO.getByIdCommodity(commodityView.getCommodity().getId()).isEmpty(), is(true));

    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        commodityViewDAO.create(commodityView);
        commodityView.setCommodityPhotoType(ImageType.gif);
        commodityViewDAO.update(commodityView);
    }

    @Test
    @Transactional
    public void testGetByImageType() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> commodityViews = commodityViewDAO.getByImageType(commodityView.getCommodityPhotoType());
        assertNotNull(commodityViews);
        assertEquals(1, commodityViews.size());
        assertTrue(commodityViews.contains(commodityView));
    }

    @Test
    @Transactional
    public  void testGetImageList() throws DBException {
        commodityViewDAO.create(commodityView);
        List<Image> commodityImages = commodityViewDAO.getImagesByIdCommodity(commodityView.getCommodity().getId());
        assertNotNull(commodityImages);
        assertEquals(1, commodityImages.size());
        assertEquals(commodityImages.get(0).getType(), commodityView.getCommodityPhotoType());
    }

}
