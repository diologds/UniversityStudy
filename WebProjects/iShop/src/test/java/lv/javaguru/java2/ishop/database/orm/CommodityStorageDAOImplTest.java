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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by Rita on 14.12.3.
 */

public class CommodityStorageDAOImplTest extends SpringContextTest{
    @Autowired
    @Qualifier("CommodityStorageDAO_ORM")
    private CommodityStorageDAO storageDAO;
    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;
    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;

    @Autowired
    private ClearDatabaseDAO dbCleaner;


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

        Commodity commodity =new Commodity(producerFromDB, categoryFromDB, "Ipad4",new BigDecimal(600),
                "Nice","Ipad","AAA",urlStrings);
        commodityDAO.create(commodity);

        //CommodityStorageJoinCommodity table
        Commodity commodityFromDB=commodityDAO.getByName("Ipad4");
        storage=new CommodityStorage(commodityFromDB, StorageType.local, 57L);
    }


    @Before
    public void setUp() throws Exception {
        dbCleaner.clear();
        createData();
    }


    @Test
    @Transactional
    public void testCreate() throws DBException {
        storageDAO.create(storage);
        CommodityStorage storageFromDB = storageDAO.getById(storage.getId());
        assertTrue(storage.equals(storageFromDB));
        assertNotNull(storageFromDB.getCommodity());

    }

    @Test
    @Transactional
    public void testGetByType() throws Exception {
        storageDAO.create(storage);
        List<CommodityStorage> storageList = storageDAO.getByType(storage.getType());
        assertFalse(storageList.isEmpty());
        for (CommodityStorage s: storageList){
            assertEquals(storage.getType(), s.getType());
        }
    }


    @Test
    @Transactional
    public void testGetByIDCommodity() throws DBException {
        storageDAO.create(storage);
        List<CommodityStorage> storageList = storageDAO.getByIdCommodity(storage.getCommodity().getId());

        assertFalse(storageList.isEmpty());
        for (CommodityStorage s: storageList){
            assertTrue(storage.getCommodity().getId() == s.getCommodity().getId());
        }
    }


    @Test
    @Transactional
    public void testGetByID() throws DBException {
        storageDAO.create(storage);
        CommodityStorage storageFromDB = storageDAO.getById(storage.getId());
        assertNotNull(storageFromDB);
        assertTrue(storage.getType().equals(storageFromDB.getType()));
        assertTrue(storage==storageFromDB);

    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        storageDAO.create(storage);
        storageDAO.delete(storage.getId());
        CommodityStorage storageFromDB = storageDAO.getById(storage.getId());
        assertNull(storageFromDB);

    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<CommodityStorage> storageList = storageDAO.getAll();
        for (CommodityStorage s: storageList){
            assertTrue(s.equals(storage));
        }
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        storageDAO.create(storage);
        Long newQuantity = storage.getQuantity()+93L;
        storage.setQuantity(newQuantity);
        storageDAO.update(storage);
        CommodityStorage storageUpdated=storageDAO.getById(storage.getId());
        assertEquals(storageUpdated.getQuantity(), newQuantity);

    }
}
