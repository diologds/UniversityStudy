package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CommodityViewDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.OrderedCommodityDAO;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("clientOrder")
public class PlaceOrderControllerImpl{

    @Qualifier("PlacedOrderDAO_ORM")
    @Autowired
    private PlacedOrderDAO placedOrderDAO;
    @Qualifier("OrderedCommodityDAO_ORM")
    @Autowired
    private OrderedCommodityDAO orderedCommodityDAO;
    @Qualifier("CommodityViewDAO_ORM")
    @Autowired
    private CommodityViewDAO commodityViewDAO;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException {

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        String message = null;

        if (req.getParameter("checkout") != null) {
            if (!req.getParameter("deliveryType").equals("null")) {
                addOrder(req);
                cart.removeAll();
                message = "Your order received";
            } else {
                message = "Please select delivery type ";
            }
        } else {
            if (req.getParameter("inc") != null || req.getParameter("dec") != null || req.getParameter("remove") != null)
                cartModification(req, cart);

            if (cart != null) {
                List<String> imageNames;
                for (CartItem item : cart.getItems()) {
                    List<Image> images = commodityViewDAO.getImagesByIdCommodity(item.getCommodity().getId());
                    imageNames = getImageNames(images);
                    if (imageNames.isEmpty()) {
                        imageNames.add("default.jpg");
                    }
                    item.setImageFile(imageNames.get(0));
                }
            }
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("clientOrder");
        model.addObject("model", message);
        return model;

    }

    protected void cartModification(HttpServletRequest req, Cart cart) {

        Long id;

        if (req.getParameter("inc") == null) {
            if (req.getParameter("dec") == null) {
                if (req.getParameter("remove") == null) {
                    return;
                } else {
                    id = Long.parseLong(req.getParameter("remove"));
                    for (CartItem item : cart.getItems()) {
                        if (item.getCommodity().getId().equals(id)) {
                            cart.deleteItem(item);
                            break;
                        }
                    }
                }
            } else {
                id = Long.parseLong(req.getParameter("dec"));
                for (CartItem item : cart.getItems()) {
                    if (item.getCommodity().getId().equals(id)) {
                        item.dec();
                        break;
                    }
                }
            }
        } else {
            id = Long.parseLong(req.getParameter("inc"));
            for (CartItem item : cart.getItems()) {
                if (item.getCommodity().getId().equals(id)) {
                    item.inc();
                    break;
                }
            }
        }
    }

    private void addOrder(HttpServletRequest req) throws DBException {

        Customer customer = (Customer) req.getSession().getAttribute("user");

        if (customer != null) {
            PlacedOrder order = new PlacedOrder();
            order.setCustomer(customer);
            Cart cart = (Cart) req.getSession().getAttribute("cart");
            BigDecimal amount = new BigDecimal((double) cart.getItems().size());

            order.setTotal(amount);
            order.setStatus(OrderStatus.neworder);
            order.setDeliveryType(OrderDeliveryType.getEnum(req.getParameter("deliveryType")));

            placedOrderDAO.create(order);

            List<CartItem> list = cart.getItems();
            for (CartItem item : list) {
                OrderedCommodity orderedCommodity = new OrderedCommodity(order, item.getCommodity()
                        , item.getCommodity().getPrice().multiply(BigDecimal.valueOf((double) item.getAmount()))
                        , (long) item.getAmount());
                orderedCommodityDAO.create(orderedCommodity);
            }
        }
    }

    private List<String> getImageNames(List<Image> images) {
        List<String> names = new ArrayList<String>();
        for (Image i : images) {
            String fileName = i.getImagePath();
            ImageType imageFormat = i.getType();
            String name = fileName + "." + imageFormat.toString();
            names.add(name);
        }
        return names;
    }

}