package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.database.jdbc.CustomerDAOImpl;
import lv.javaguru.java2.ishop.database.jdbc.PlacedOrderDAOImpl;
import lv.javaguru.java2.ishop.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by nikolaylapshin on 23/02/14.
 */
@Component
public class PlacedOrderController implements Controller {

    @Autowired
    @Qualifier("PlacedOrderDAO_JDBC")
    private PlacedOrderDAO placedOrderDAO;
    @Qualifier("CustomerDAO_JDBC")
    @Autowired
    private CustomerDAO customerDAO;


    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {

        if (req.getParameter("btnAdd") != null) {
            this.proceedOrder(req);
        }
        List<PlacedOrder> orders = new ArrayList<PlacedOrder>();
        List<Customer> customers = new ArrayList<Customer>();
        HashMap<String, Object> map = new HashMap<String, Object>();

        orders = placedOrderDAO.getList();
        customers = customerDAO.getAll();


        map.put("orders", orders);
        map.put("customers", customers);

        Model model = new Model("placedOrder.jsp", map);
        return model;
    }

    protected void proceedOrder(HttpServletRequest request) throws DBException
    {

        PlacedOrder order = new PlacedOrder();
        order.setCustomerId(Long.parseLong(request.getParameter("customer-id")));
        order.setTotal(new BigDecimal(Double.parseDouble(request.getParameter("total"))));
        order.setStatus(OrderStatus.getEnum(request.getParameter("status")));
        order.setDeliveryType(OrderDeliveryType.getEnum(request.getParameter("delivery-type")));

        placedOrderDAO.create(order);

    }
}
