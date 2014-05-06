package lv.javaguru.java2.ishop.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.DisplayCategoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ann on 15/03/14.
 */
@Component
public class DisplayCategoryControllerImpl implements DisplayCategoryController
{
    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    //@Autowired
    //CartController cartController;
    private static Logger logger = Logger.getLogger(DisplayCategoryController.class.getName());

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = Isolation.REPEATABLE_READ)
    public Model getModel(HttpServletRequest req,HttpServletResponse resp) throws DBException
    {
        DisplayCategoryData data = new DisplayCategoryData();
        List<Category> categories = null;
        List<Commodity> commodities = null;
        Long idCategory = Long.parseLong(req.getParameter("category"));

        Category currCategory = categoryDAO.getById(idCategory);
        categories = categoryDAO.getAll();
        commodities = currCategory.getCommodities();
        //To fetch collection from DB
        commodities = commodities.subList(0,commodities.size());
        data.setCategories(categories);
        data.setCommodities(commodities);
        data.setCurrCategory(currCategory);

        //cartController.getModel(req, resp);

        return new Model("displayCategory.jsp",data);

    }
}