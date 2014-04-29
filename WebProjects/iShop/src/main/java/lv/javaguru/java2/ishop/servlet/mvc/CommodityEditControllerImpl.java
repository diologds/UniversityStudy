package lv.javaguru.java2.ishop.servlet.mvc;


import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.action_handlers.CommodityActionHandler;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.CommodityEditData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Rita on 14.5.3.
 */
@Controller
@RequestMapping("commodityEdit")
public class CommodityEditControllerImpl
{

    private static Logger logger = Logger.getLogger(CommodityEditControllerImpl.class.getName());

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    private CommodityActionHandler dbActionHandler;
    @Autowired
    private IshopDispatcher dispatcher;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        String dbMessage = "";
        Boolean failedDbOperation = false;

        if (req.getAttribute("failedDbOperation")!=null)
            failedDbOperation = (Boolean)req.getAttribute("failedDbOperation");

        if (req.getAttribute("dbMessage")!=null)
            dbMessage= (String)req.getAttribute("dbMessage");

        CommodityEditData data = new CommodityEditData();

        data.setFailedDbOperation(failedDbOperation);
        data.setDbMessage(dbMessage);
        List<Commodity> commodities = commodityDAO.getAll();
        data.setCommodities(commodities);
        ModelAndView model = new ModelAndView();
        model.setViewName("commodityEdit");
        model.addObject("model", data);
        return model;

    }
    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException, ServletException
    {
        StringBuilder dbMessage = new StringBuilder("");
        boolean failedDelete = false;

        if (req.getParameter("deleting")!=null)
        {
            logger.log(Level.INFO, "performDeleting ="+req.getParameter("deleting"));
            failedDelete = !(dbActionHandler.delete(req,dbMessage));
            req.setAttribute("failedDbOperation", failedDelete);
            req.setAttribute("dbMessage", dbMessage.toString());
            dispatcher.redirectToThisHandler(req, resp);
        }
        else
            dispatcher.redirectToThisHandler(req,resp);
    }

}
