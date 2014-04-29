package lv.javaguru.java2.ishop.servlet.mvc;

/**
 * Created by Ann on 15/03/14.
 */
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import java.io.IOException;
import lv.javaguru.java2.ishop.domain.Cart;
import lv.javaguru.java2.ishop.domain.CartItem;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value={"cart"})
public class CartControllerImpl
{
    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    @Autowired
    private IshopDispatcher dispatcher;

    private static Logger logger = Logger.getLogger(CartControllerImpl.class.getName());

    @RequestMapping(method = RequestMethod.POST)
    public void performTask(HttpServletRequest req, HttpServletResponse resp)
            throws DBException,  IOException, ServletException
    {

        try {

            // Add to cart
            String parameter1 = req.getParameter("addCommodity");
            if (parameter1 != null)
            {
                Long commodityID = Long.parseLong(parameter1);
                Cart cart = (Cart) req.getSession().getAttribute("cart");
                if (cart==null)
                {
                    cart = new Cart();

                }
                cart.addItem(new CartItem(commodityDAO.getById(commodityID), 1));
                req.getSession().setAttribute("cart",cart);
            }

            // Remove from cart
            String parameter2 = req.getParameter("remCommodity");
            if (parameter2 != null)
            {
                Long commodityID = Long.parseLong(parameter2);
                Cart cart = (Cart) req.getSession().getAttribute("cart");
                cart.deleteItem(commodityID);
                req.getSession().setAttribute("cart",cart);
            }
        }catch (Exception e)
        {
            logger.log(Level.SEVERE, "Exception while executing cartController.getModel()");
        }
        Stack<String> taskHandlers = (Stack<String>)req.getAttribute("taskHandlers");
        String nextHandler = null;
        if ((taskHandlers!=null)&&(!taskHandlers.isEmpty()))
            nextHandler = taskHandlers.pop();
        req.setAttribute("taskHandlers",taskHandlers);
        if (nextHandler != null)
            dispatcher.forwardToTaskHandler(req,resp,nextHandler);
        else
        {
            String pageHandler = (String)req.getAttribute("pageHandler");
            dispatcher.redirectToPageHandler(resp,pageHandler);
        }
    }
}
