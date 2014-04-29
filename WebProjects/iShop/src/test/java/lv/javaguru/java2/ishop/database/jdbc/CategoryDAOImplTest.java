package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.SpringContextTest;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.database.CategoryDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Ann on 12/02/14.
 */
public class CategoryDAOImplTest extends SpringContextTest {

    @Autowired
    private ClearDatabaseDAO clearDatabaseDAO;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;


    private static Category[] categories = new Category[3];
    private static Category[] categoriesRetrieved = new Category[3];


    private static void createCategories() {
        categories[0]= new Category("tablet");
        categories[1] = new Category("smartphone");
        categories[2] = new Category("RAM");
    }

    @Before
    public void setUp() throws Exception {
        clearDatabaseDAO.clear();
        createCategories();
        for(Category cat: categories) {
            categoryDAO.create(cat);
        }
    }

    @Test
    public void testCreate() throws Exception {
        categoriesRetrieved[0] = categoryDAO.getByName("tablet");
        categoriesRetrieved[1] = categoryDAO.getByName("smartphone");
        categoriesRetrieved[2] = categoryDAO.getByName("RAM");

        for(int i=0; i<categories.length; i++) {
            assertEquals(categories[i].getName(), categoriesRetrieved[i].getName());
        }
    }

    @Test
    public void testGetById() throws Exception {
        Long id1 = categoryDAO.getByName("tablet").getId();
        Long id2 = categoryDAO.getByName("smartphone").getId();
        Long id3 = categoryDAO.getByName("RAM").getId();

        assertEquals(categoryDAO.getById(id1).getName(), "tablet");
        assertEquals(categoryDAO.getById(id2).getName(),"smartphone");
        assertEquals(categoryDAO.getById(id3).getName(), "RAM");
    }

    @Test
    public void testGetByName() throws Exception {
        assertEquals(categoryDAO.getByName("tablet").getName(),"tablet");
        assertEquals(categoryDAO.getByName("smartphone").getName(),"smartphone");
        assertEquals(categoryDAO.getByName("RAM").getName(),"RAM");
    }

    @Test
    public void testDelete() throws Exception {
        Long id1 = categoryDAO.getByName("tablet").getId();
        categoryDAO.delete(id1);
        assertEquals(null, categoryDAO.getById(id1));

    }

    @Test
    public void testUpdate() throws Exception {
        Category cat = categoryDAO.getByName("RAM");
        cat.setName("SSD");
        categoryDAO.update(cat);
        assertEquals(cat, categoryDAO.getByName("SSD"));
    }

}
