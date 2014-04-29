package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CommentDAO;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Comment;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lilya on 19.03.14.
 */
@Controller
@RequestMapping(value={"comment"})
public class CommentControllerImpl {
    @Qualifier("CommentDAO_ORM")
    @Autowired
    private CommentDAO commentDAO;

    @Qualifier("CustomerDAO_ORM")
    @Autowired
    private CustomerDAO customerDAO;

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    @Autowired
    private IshopDispatcher dispatcher;

    private static Logger logger = Logger.getLogger(CommentController.class.getName());


    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
            throws DBException, IOException, ServletException {

        try{
            String parameter = req.getParameter("addComment");
            if (parameter != null){
                addComment(req);
            }
        }catch(Exception e)
        {
            logger.log(Level.SEVERE, "Exception while executing commentController.getModel()");
        }
        List<Comment> comments = commentDAO.getAll();
        Collections.reverse(comments);

        String pageHandler = (String)req.getAttribute("pageHandler");
        dispatcher.redirectToPageHandler(resp, pageHandler);
    }

    private void addComment(HttpServletRequest req) {
        Long paramIDCommodity=Long.parseLong(req.getParameter("commodity"));
        String paramComment=req.getParameter("comment");
        Date date = new Date();
        boolean isLoggedIn;

        Customer customer = (Customer) req.getSession().getAttribute("user");
        Comment comment;
        try {
            Commodity commodity = commodityDAO.getById(paramIDCommodity);
            if (customer==null) {
                isLoggedIn = false;
                comment = new Comment(commodity, paramComment, date, isLoggedIn);
            } else {
                isLoggedIn = true;
                comment = new Comment(commodity, customer, paramComment, date, isLoggedIn);
            }
            commentDAO.create(comment);

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
