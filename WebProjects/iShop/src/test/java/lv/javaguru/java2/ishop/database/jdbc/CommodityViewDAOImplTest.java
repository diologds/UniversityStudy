package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.domain.*;
import lv.javaguru.java2.ishop.database.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CommodityViewDAOImplTest  extends SpringContextTest
{

    @Autowired private  ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("CommodityDAO_JDBC")
    private  CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;
    @Qualifier("CommodityViewDAO_JDBC")
    @Autowired private CommodityViewDAO commodityViewDAO;
    @Autowired private ImageConverter imageLoader;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private  CategoryDAO categoryDAO;

    private static final int COMMODITY_NUM =5;

    private Commodity commodity;
    private CommodityView commodityView;
    private Category[] categories = new Category[3];
    private Producer[] producers = new Producer[4];
    private Commodity[] commodities = new Commodity[COMMODITY_NUM];

    private CommodityView someCommodityView = null;
    private String[] urlStrings = new String[COMMODITY_NUM];

    private void createCategories() {
        categories[0] = new Category("tablet");
        categories[1] = new Category("laptop");
        categories[2] = new Category("smartphone");
    }

    private void createProducers() {
        producers[0] = new Producer("Lenovo", "Just Good");
        producers[1] = new Producer("Apple", "Premium");
        producers[2] = new Producer("MSI", "Budget");
        producers[3] = new Producer("HTC", "Good");
    }

    private void createCommodities() throws Exception {
        urlStrings[0] = " http://www.apple.com/";
        urlStrings[1] = " http://www.apple.com/";
        urlStrings[2] = " http://www.msi.com/";
        urlStrings[3] = " http://www.lenovo.com/";
        urlStrings[4] = " http://www.htc.com/";

        commodity = new Commodity("Ipad4", new BigDecimal(600), "Nice", "Ipad", "AAA", urlStrings[0]);

    }

    private void createCommodityView() throws Exception {
        commodityView = new CommodityView(imageLoader.getBytes("htc.jpg" , ImageType.jpg) , ImageType.bmp, false);


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

        commodityView.setIdCommodity(commodity.getId());


    }

    private void prepareTestDataSet() throws Exception {
        createCategories();
        createProducers();
        createCommodities();
        createCommodityView();
    }

    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        prepareTestDataSet();
        fillDbTables();
    }

    @Test
    public void testCreate() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByIdCommodity(commodityView.getIdCommodity());
        commodityView.setId(c.get(0).getId());
        assertEquals(c.get(0),commodityView);
    }

    @Test
    public void testGetByImageType() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByImageType(ImageType.bmp);
        commodityView.setId(c.get(0).getId());
        assertEquals(c.get(0),commodityView);
    }

    @Test
    public void testDelete() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByIdCommodity(commodity.getId());
        for (CommodityView test : c)
            commodityViewDAO.delete(test.getId());
        assertThat("Expected empty List ", commodityViewDAO.getByIdCommodity(commodity.getId()).isEmpty(), is(true));

    }

    @Test
    public void testUpdate() throws Exception {
        commodityViewDAO.create(commodityView);
        List<CommodityView> c = commodityViewDAO.getByIdCommodity(commodity.getId());

        CommodityView view = c.get(0);
        long id = view.getId();
        long idCommodity = view.getIdCommodity();

        someCommodityView = new CommodityView();
        someCommodityView.setId(id);
        someCommodityView.setIdCommodity(idCommodity);
        someCommodityView.setCommodityPhoto(view.getCommodityPhoto());
        someCommodityView.setCommodityPhotoType(ImageType.png);
        someCommodityView.setGalleryPhoto(true);

        commodityViewDAO.update(someCommodityView);

        CommodityView test = commodityViewDAO.getById(id);

        assertEquals(someCommodityView, test);

    }
}
