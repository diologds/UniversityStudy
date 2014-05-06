package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Producer;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.action_handlers.CommodityActionHandler;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.CommodityAddData;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.CommodityUpdateData;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ann on 28/03/14.
 */
@Controller
@RequestMapping("commodityTask")
public class CommodityTaskControllerImpl
{
    private final String pageHandlerName = "commodityEdit";

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    @Qualifier("ProducerDAO_ORM")
    @Autowired
    private ProducerDAO producerDAO;

    @Qualifier("CategoryDAO_ORM")
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private IshopDispatcher dispatcher;

    @Autowired
    private CommodityActionHandler actionHandler;

    private static Logger logger = Logger.getLogger(CommodityEditControllerImpl.class.getName());

    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DBException, ServletException
    {
        if (req.getParameter("add")!=null)
        {
                logger.log(Level.INFO, "add");
                performAdd(req, resp);
        }
        else if (req.getParameter("update")!=null )
                performUpdate(req, resp);
        else if (req.getParameter("adding")!=null)
        {
            if (req.getParameter("refresh")!=null)
                 refreshAddForm(req,resp);
            else
                performAdding(req, resp);
        }
        else if (req.getParameter("updating")!=null)
        {
            if (req.getParameter("refresh")!=null)
                refreshUpdateForm(req,resp);
            else
                performUpdating(req, resp);
        }
        else if (req.getParameter("cancelAdding")!=null)
            performCancelAdding(req, resp);

        else if (req.getParameter("cancelUpdating")!=null)
            performCancelUpdating(req, resp);

        else if (req.getParameter("undoAdding")!=null)
              performUndoAdding(req, resp);

        else if (req.getParameter("undoUpdating")!=null)
             performUndoUpdating(req, resp);
    }
    private void refreshAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DBException
    {
        StringBuilder dbMessage = new StringBuilder("");
        Map<String,Object> inputData = new HashMap<String, Object>();
        Map<String,String> inputErrors = new HashMap<String, String>();
        actionHandler.refresh(req,dbMessage,inputData,inputErrors);
        req.setAttribute("dbMessage", dbMessage.toString());
        req.setAttribute("inputData", inputData);
        req.setAttribute("inputErrors", inputErrors);
        String url = req.getRequestURL() + "?adding=adding&refresh=refresh";
        dispatcher.redirectTo(resp, url);

    }
    private void refreshUpdateForm(HttpServletRequest req, HttpServletResponse resp)
            throws IOException,DBException
    {
        StringBuilder dbMessage = new StringBuilder("");
        Map<String,Object> inputData = new HashMap<String, Object>();
        Map<String,String> inputErrors = new HashMap<String, String>();
        actionHandler.refresh(req,dbMessage,inputData,inputErrors);
        req.setAttribute("dbMessage", dbMessage.toString());
        req.setAttribute("inputData", inputData);
        req.setAttribute("inputErrors", inputErrors);
        String url = req.getRequestURL() + "?updating="+req.getParameter("updating")+"&refresh=refresh";
        dispatcher.redirectTo(resp, url);
    }

    private void performAdd(HttpServletRequest req, HttpServletResponse resp)
            throws IOException,ServletException
    {
        String url = req.getRequestURL() + "?add=add";
        dispatcher.redirectTo(resp, url);
        //dispatcher.forwardToTaskHandler(req,resp,"/commodityTask/getModel");
    }

    private void performUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        String url = req.getRequestURL() + "?update="+req.getParameter("update");
        dispatcher.redirectTo(resp, url);
    }

    private void performAdding(HttpServletRequest req, HttpServletResponse resp)
           throws DBException, IOException, ServletException
      
    {
        boolean failedDbOperation = false;
        StringBuilder dbMessage = new StringBuilder("");
        Map<String,Object> inputData = new HashMap<String, Object>();
        Map<String,String> inputErrors = new HashMap<String, String>();

        failedDbOperation = !actionHandler.add(req,dbMessage,inputData,inputErrors);
        req.setAttribute("dbMessage", dbMessage.toString());
        if(failedDbOperation)
        {
            req.setAttribute("inputData", inputData);
            req.setAttribute("inputErrors", inputErrors);
            String url = req.getRequestURL() + "?adding=adding&refresh=refresh";
            dispatcher.redirectTo(resp, url);
            //dispatcher.forwardToTaskHandler(req,resp,"/commodityTask/getModel");
        }
        else
        {
             final String pageHandler = req.getContextPath()+ pageHandlerName;
             dispatcher.redirectTo(resp, pageHandler);
        }
    }

    private void performUpdating(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException
    {

        boolean failedDbOperation = false;
        StringBuilder dbMessage = new StringBuilder("");
        Map<String,Object> inputData = new HashMap<String, Object>();
        Map<String,String> inputErrors = new HashMap<String, String>();

        failedDbOperation = !actionHandler.update(req, dbMessage, inputData, inputErrors);
        req.setAttribute("dbMessage", dbMessage.toString());
        if(failedDbOperation)
        {
            req.setAttribute("inputData", inputData);
            req.setAttribute("inputErrors", inputErrors);
            String url = req.getRequestURL() + "?updating="+req.getParameter("updating")+"&refresh=refresh";
            dispatcher.redirectTo(resp, url);
        }
        else
        {
            final String pageHandler = req.getContextPath()+ pageHandlerName;
            dispatcher.redirectTo(resp, pageHandler);
        }
    }
    private void performCancelAdding(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException
    {
        final String pageHandler = req.getContextPath()+ pageHandlerName;
        dispatcher.redirectTo(resp, pageHandler);
    }

    private void performCancelUpdating(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException
    {
        final String pageHandler = req.getContextPath()+ pageHandlerName;
        dispatcher.redirectTo(resp, pageHandler);
    }

    private void performUndoAdding(HttpServletRequest req, HttpServletResponse resp)
            throws DBException,IOException
    {

       String url = req.getRequestURL() + "?add=add";
       dispatcher.redirectTo(resp, url);

    }
    private void performUndoUpdating(HttpServletRequest req, HttpServletResponse resp)
            throws DBException,IOException
    {
        String url = req.getRequestURL() + "?update="+req.getParameter("undoUpdating");
        dispatcher.redirectTo(resp, url);

    }
    @RequestMapping(method = RequestMethod.GET)
    public void getModel(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException
    {
        String dbMessage = (String)req.getAttribute("dbMessage");
        Map<String,Object> inputData = (Map<String,Object>)req.getAttribute("inputData");
        logger.log(Level.INFO,"inputData: "+ inputData.toString());
        Map<String,String> inputErrors = (Map<String,String>)req.getAttribute("inputErrors");
        logger.log(Level.INFO,"inputErrors: "+ inputErrors.toString());
       // dispatcher.redirectTo(resp, req.getRequestURL().toString());
        List<Category> categories = categoryDAO.getAll();
        logger.log(Level.INFO,"getModel categories: "+ categories.toString());
        List<Producer> producers = producerDAO.getAll();


        if (req.getParameter("updating")!=null)
        {
                logger.log(Level.INFO,"updating");
                Long paramIDCommodity=Long.parseLong(req.getParameter("updating"));

                CommodityUpdateData data1 = new CommodityUpdateData();
                Commodity c = commodityDAO.getById(paramIDCommodity);
                data1.setCommodity(c);
                data1.setCategories(categories);
                data1.setProducers(producers);
                data1.setDbMessage(dbMessage);
                data1.setInputData(inputData);
                data1.setInputErrors(inputErrors);
                data1.setNewForm(false);
                ModelAndView model = new ModelAndView();
                model.setViewName("commodityUpdate");
                model.addObject("model",data1);
                //return  model;
         }
         else if (req.getParameter("adding")!=null)
         {
             logger.log(Level.INFO,"adding");

                CommodityAddData data2 = new CommodityAddData();
                data2.setCategories(categories);
                data2.setProducers(producers);
                data2.setDbMessage(dbMessage);
                data2.setInputData(inputData);
                data2.setInputErrors(inputErrors);
                data2.setNewForm(false);
                ModelAndView model = new ModelAndView();
                model.setViewName("commodityAdd");
                model.addObject("model",data2);
                //dispatcher.redirectTo(resp, req.getRequestURL().toString());
                //return  model;
         }
        else if(req.getParameter("add")!=null)
        {
            logger.log(Level.INFO,"add");
            CommodityAddData data3 = new CommodityAddData();
            data3.setCategories(categories);
            data3.setProducers(producers);
            data3.setNewForm(true);
            ModelAndView model = new ModelAndView();
            model.setViewName("commodityAdd");
            model.addObject("model", data3);
            //String s = req.getContextPath()+"/commodityTask/stub";
            //logger.log(Level.INFO,"s="+s);
            //dispatcher.redirectTo(resp,s);
            //return  model;

        }
        else if (req.getParameter("update")!=null)
        {
            logger.log(Level.INFO,"update");
            Long paramIDCommodity=Long.parseLong(req.getParameter("update"));

            CommodityUpdateData data4 = new CommodityUpdateData();
            Commodity c = commodityDAO.getById(paramIDCommodity);
            data4.setCommodity(c);
            data4.setCategories(categories);
            data4.setProducers(producers);
            data4.setNewForm(true);
            ModelAndView model = new ModelAndView();
            model.setViewName("commodityUpdate");
            model.addObject("model",data4);
            //return  model;
        }
        //return  null;
    }
    @RequestMapping(value = "/stub", method = RequestMethod.GET)
    public void stub(HttpServletRequest req, HttpServletResponse resp)
    {
        logger.log(Level.INFO,"stub");
    }

}