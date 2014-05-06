package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Lilya on 04.03.14.
 */

@Component
public class EditCategoryController implements Controller {

    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;

    private List<Category> categories = null;

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        req.setAttribute("msg", " ");
        if (req.getParameter("submit")!=null){
            addCategory(req);
        }
        if (req.getParameter("delete")!=null){
            deleteCategory(req);
        }
        categories = categoryDAO.getAll();
        return new Model("editCategory.jsp", categories);
    }

    private void deleteCategory(HttpServletRequest req) {
        String[] deleteIds = req.getParameterValues("deleteIds");
        Long idLong;
        for (String id : deleteIds){
            idLong = Long.parseLong(id);
            try {
                categoryDAO.delete(idLong);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    protected void addCategory(HttpServletRequest req){
        String paramCategoryName = req.getParameter("categoryName");
        if (!overlap(paramCategoryName)){
            Category category = new Category(paramCategoryName);
            try {
                categoryDAO.create(category);
                categories.add(category);
                req.setAttribute("msg","New Category is successfully saved into BD!");
            } catch (DBException e) {
                e.printStackTrace();
            }
        }else {
            req.setAttribute("msg","Entered Category already exists!");
        }
     }

    private boolean overlap(String catName){
        for (Category cat: categories ){
            if (catName.equals(cat.getName())) return true;
        }
        return false;
    }

}
