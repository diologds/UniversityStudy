package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
@Component
public class CategoryController implements Controller {

    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;


    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        List<Category> categories = null;

         categories = categoryDAO.getAll();
         return new Model("category.jsp", categories);

    }

}
