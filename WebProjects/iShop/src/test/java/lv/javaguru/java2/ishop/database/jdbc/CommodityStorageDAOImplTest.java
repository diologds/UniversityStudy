package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.CommodityStorageDAO;
import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rita on 14.12.2.
 */
public class CommodityStorageDAOImplTest extends SpringContextTest {

    @Autowired
    private ClearDatabaseDAO dbCleaner;
    @Autowired
    @Qualifier("CommodityStorageDAO_JDBC")
    private  CommodityStorageDAO storageDAO;
    @Qualifier("CommodityDAO_JDBC")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;


    private CommodityStorage storage;

    private  void createData() throws Exception{
        //Producer table
        Producer producer = new Producer("Lenovo", "Just Good");

        producerDAO.create(producer);

        //Category table
        Category category = new Category("Tablet");

        categoryDAO.create(category);

        //Commodity table
        Producer producerFromDB = producerDAO.getByName("Lenovo");
        Category categoryFromDB = categoryDAO.getByName("Tablet");
        String urlStrings = " http://www.apple.com/index.html";

        Commodity commodity =new Commodity(producerFromDB.getId(), categoryFromDB.getId(), "Ipad4",new BigDecimal(600),
                "Nice","Ipad","AAA",urlStrings);

        commodityDAO.create(commodity);

        //CommodityStorageJoinCommodity table
        Commodity commodityFromDB=commodityDAO.getByName("Ipad4");

        storage=new CommodityStorage(commodityFromDB.getId(), StorageType.local, 57L);

    }



    @Before
    public void setUp() throws  Exception{
        dbCleaner.clear();
        createData();
    }

    @After
    public void tearDown() throws Exception {
        dbCleaner.clear();
    }

    @Test
    public void testCreate() throws Exception {
        storageDAO.create(storage);
        CommodityStorage storageFromDB = storageDAO.getById(storage.getId());
        assertTrue(storage.equals(storageFromDB));
    }

    @Test
    public void testGetByType() throws Exception {
        storageDAO.create(storage);
        List<CommodityStorage> storageList = storageDAO.getByType(storage.getType());
        assertFalse(storageList.isEmpty());
        for (CommodityStorage s: storageList){
            assertEquals(storage.getType(), s.getType());
        }
    }

    @Test
    public void testGetAll() throws Exception {
        List<CommodityStorage> storageList = storageDAO.getAll();
        for (CommodityStorage s: storageList){
        assertTrue(s.equals(storage));
        }
    }

    @Test
    public void testDelete() throws Exception {
        storageDAO.create(storage);
        storageDAO.delete(storage.getId());
        CommodityStorage storageFromDB = storageDAO.getById(storage.getId());
        assertNull(storageFromDB);

    }

    @Test
    public void testUpdate() throws Exception {
        storageDAO.create(storage);
        Long newQuantity = storage.getQuantity()+93L;
        storage.setQuantity(newQuantity);
        storageDAO.update(storage);
        CommodityStorage storageUpdated=storageDAO.getById(storage.getId());
        assertEquals(storageUpdated.getQuantity(), newQuantity);

    }
}
