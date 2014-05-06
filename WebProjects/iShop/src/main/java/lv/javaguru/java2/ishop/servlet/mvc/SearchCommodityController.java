package lv.javaguru.java2.ishop.servlet.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
@Component
public class SearchCommodityController implements Controller {

    @Qualifier("CommodityDAO_JDBC")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("CategoryDAO_JDBC")
    private CategoryDAO categoryDAO;


    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String commodityName = req.getParameter("search");


            List<Commodity> commodities = commodityDAO.searchByName(commodityName);
            List<Category> categories = categoryDAO.getAll();

            IndexData dataModel = new IndexData();
            dataModel.setCommodities(commodities);
            dataModel.setCategories(categories);

            return new Model("searchCommodity.jsp", dataModel);
    }

}
