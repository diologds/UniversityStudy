package lv.javaguru.java2.ishop.database.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Assert;
import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.servlet.mvc.CategoryController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class SpringIoCTest {

    private ApplicationContext context;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
    }

    @Test
    public void testTwoImplementationsOfOneInterface() {
        CategoryDAO categoryDAO1 = context.getBean("CategoryDAO_ORM", CategoryDAO.class);
        CategoryDAO categoryDAO2 = context.getBean("CategoryDAO_ORM", CategoryDAO.class);
        assertNotNull(categoryDAO1);
        assertNotNull(categoryDAO2);
        assertTrue(categoryDAO1 == categoryDAO2);

        CategoryDAO categoryDAO = context.getBean("CategoryDAO_JDBC", CategoryDAO.class);
        assertNotNull(categoryDAO);
    }

    @Test
    public void testGetBeanByClass() {
        CategoryController categoryController = context.getBean(CategoryController.class);
        assertNotNull(categoryController);
        assertNotNull(categoryController.getCategoryDAO());
    }

}
