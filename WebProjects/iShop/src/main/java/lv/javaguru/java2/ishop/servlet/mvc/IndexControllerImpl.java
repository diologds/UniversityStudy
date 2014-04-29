package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.domain.Commodity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import org.springframework.web.servlet.ModelAndView;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Ann on 15/03/14.
 */
@Controller
@RequestMapping("index")
public class IndexControllerImpl
{
    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private CategoryDAO categoryDAO;

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    @Autowired
    IshopDispatcher dispatcher;

   private static Logger logger = Logger.getLogger(IndexControllerImpl.class.getName());

    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
                        throws IOException, ServletException
    {
        logger.log(Level.INFO, "In index!");
        Stack<String> taskHandlers = new Stack<String>();
        //add handlers which follow after CartController.performTask
        req.setAttribute("taskHandlers",taskHandlers);
        String servletName = req.getServletPath();
        String pageHandler = req.getContextPath() + servletName;
        req.setAttribute("pageHandler", pageHandler);
        dispatcher.forwardToTaskHandler(req,resp,"cart");
    }
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp)
            throws DBException
    {
        IndexData data = new IndexData();
        List<Category> categories = null;
        List<Commodity> commodities = null;

        categories = categoryDAO.getAll();
        commodities = commodityDAO.getAll();
        data.setCategories(categories);
        data.setCommodities(commodities);
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("model", data);
        return model;
    }

}
