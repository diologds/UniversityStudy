package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.domain.*;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import lv.javaguru.java2.ishop.servlet.mvc.data_model.DisplayCommodityDescriptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;


/**
 * Created by Ann on 15/03/14.
 */
@Controller
@RequestMapping("displayCommodityDescription")
public class DisplayCommodityDescriptionControllerImpl
{
    @Autowired
    @Qualifier("CategoryDAO_ORM")
    private  CategoryDAO categoryDAO;


    @Autowired
    @Qualifier("CommodityDAO_ORM")
    private CommodityDAO commodityDAO;

    @Autowired
    @Qualifier("CommodityViewDAO_ORM")
    private CommodityViewDAO commodityViewDAO;

    @Autowired
    @Qualifier("CommodityStorageDAO_ORM")
    private CommodityStorageDAO commodityStorageDAO;

    @Autowired
    @Qualifier("ProducerDAO_ORM")
    private ProducerDAO producerDAO;

    @Autowired
    @Qualifier("CommentDAO_ORM")
    private CommentDAO commentDAO;

    @Autowired
    @Qualifier("LikeDAO_ORM")
    private LikeDAO likeDAO;

    @Autowired
    IshopDispatcher dispatcher;

    private static Logger logger = Logger.getLogger(DisplayCommodityDescriptionController.class.getName());

    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException, ServletException
    {
        String taskHandler = getTaskHandler(req);
        Stack<String> taskHandlers = new Stack<String>();
        taskHandlers.add(taskHandler);
        req.setAttribute("taskHandlers",taskHandlers);
        String servletName = req.getServletPath();
        String pageHandler = req.getContextPath() + servletName;
        String url = req.getRequestURI() +
                "?"+"commodity="+req.getParameter("commodity") +
                "&"+ "type=" +(String)req.getParameter("type");
        req.setAttribute("pageHandler", url);
        dispatcher.forwardToTaskHandler(req,resp,"/cart");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        DisplayCommodityDescriptionData data = new DisplayCommodityDescriptionData();
        List<Category> categories = null;
        Commodity commodity = null;
        Producer producer = null;
        List<String> imageNames = null;
        List<Comment> comments = null;
       // List<Like> likes = null;

        Long quantity = new Long(0); 

        Long idCommodity = Long.parseLong(req.getParameter("commodity"));

        categories = categoryDAO.getAll();
        comments = commentDAO.getByCommodity(idCommodity);
        commodity = commodityDAO.getById(idCommodity);
       // producerName = producerDAO.getById(commodity.getIdProducer()).getName();
        producer = commodity.getProducer();
        List<Image> images = commodityViewDAO.getImagesByIdCommodity(idCommodity);
        imageNames = getImageNames(images);
        if (imageNames.isEmpty())
        {
            imageNames.add("default.jpg");

        }
        //List<CommodityStorage> storage = commodityStorageDAO.getByIdCommodity(idCommodity);
        quantity = (long)5;//calculateQuantity(storage);
        data.setCategories(categories);
        data.setCommodity(commodity);
        data.setProducer(producer);
        data.setImageNames(imageNames);
        data.setQuantity(quantity);
        data.setComments(comments);
        //data.setLikes(likes);

        //cartController.getModel(req, resp);
        //likeController.getModel(req, resp);
        ModelAndView model = new ModelAndView();
        model.setViewName("displayCommodityDescription");
        model.addObject("model", data);
        return model;

    }


    private String getTaskHandler(HttpServletRequest req) {
        if (req.getParameter("like")!=null) return "like";
        if (req.getParameter("wishList")!=null) return "wishList";
        if (req.getParameter("type").equals("comments")) return "comment";

        return null;
    }

    private Long calculateQuantity(List<CommodityStorage> storage)
    {
        Long quantity = new Long(0);
        for(CommodityStorage s: storage)
            quantity += s.getQuantity();
        return quantity;
    }
    private List<String> getImageNames(List<Image> images)
    {
        List<String> names = new ArrayList<String>();

        for (Image i: images)
        {
            String fileName = i.getImagePath();

            ImageType imageFormat = i.getType();

            String name = fileName + "." + imageFormat.toString();
            names.add(name);

        }
        return names;
    }

}
