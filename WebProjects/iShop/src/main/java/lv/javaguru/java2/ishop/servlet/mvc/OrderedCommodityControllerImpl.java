package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ordersView")
public class OrderedCommodityControllerImpl {

    @Qualifier("CustomerDAO_ORM")
    @Autowired
    private CustomerDAO customerDAO;
    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("PlacedOrderDAO_ORM")
    private PlacedOrderDAO placedOrderDAO;
    @Autowired
    @Qualifier("OrderedCommodityDAO_ORM")
    private OrderedCommodityDAO orderedCommodityDAO;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException {

        if (req.getParameter("submitChange") != null) {
            String message;
            if (!req.getParameter("orderChangeID").equals("null") && !req.getParameter("orderChangeValue").equals("null")) {
                changeOrder(req);
                message = "Order was successfully changed";
            } else {
                message = "You forgot to specify OrderID or OrderStatus";
            }
            req.setAttribute("message", message);
        }

        if (req.getParameter("submitDelete") != null) {
            String message;
            if (!req.getParameter("orderDeleteID").equals("null")) {
                message = "Order was successfully deleted";
                deleteOrder(req);
            } else {
                message = "You forgot to specify OrderID";
            }
            req.setAttribute("message", message);
        }

        FilterInfo dataFilter  = new FilterInfo();

        List<OrderedCommodity> orderedCommodities = orderedCommodityDAO.getAll();
        List<FilterData> dataList = new ArrayList<FilterData>();
        if (!orderedCommodities.isEmpty()) {
            for (OrderedCommodity orderedCommodity : orderedCommodities) {

                FilterData data = new FilterData();
                data.setOrderedCommodity(orderedCommodity);
                data.setCommodity(orderedCommodity.getCommodity());
                data.setPlacedOrder(orderedCommodity.getPlacedOrder());
                data.setCustomer(orderedCommodity.getPlacedOrder().getCustomer());

                dataList.add(data);

                dataFilter.getCommodities().add(data.getCommodity());
                dataFilter.getOrderStatuses().add(data.getPlacedOrder().getStatus().getValue());
                dataFilter.getDeliveryTypes().add(data.getPlacedOrder().getDeliveryType().getValue());
                dataFilter.getOrders().add(data.getPlacedOrder());
                dataFilter.getCustomers().add(data.getCustomer());
            }
        }
        dataFilter(req, dataList);

        ModelAndView model = new ModelAndView();
        model.setViewName("ordersView");
        model.addObject("model", dataFilter);
        return model;
    }


    private void changeOrder(HttpServletRequest req) throws DBException {
        Long orderId = Long.parseLong(req.getParameter("orderChangeID"));
        PlacedOrder order = placedOrderDAO.getById(orderId);
        order.setStatus(OrderStatus.getEnum(req.getParameter("orderChangeValue")));
        placedOrderDAO.update(order);
    }

    private void deleteOrder(HttpServletRequest req) throws DBException {

        Long orderId = Long.parseLong(req.getParameter("orderDeleteID"));
        for (OrderedCommodity orderedCommodity : orderedCommodityDAO.getOrderID(orderId)) {
            orderedCommodityDAO.delete(orderedCommodity.getOrderedCommodityID());
        }
        placedOrderDAO.delete(orderId);

    }

    private void dataFilter(HttpServletRequest req, List<FilterData> dataList) {

        Long name = ((req.getParameter("commodityName") != null) ? Long.parseLong(req.getParameter("commodityName")) : 0l);
        Long brand = ((req.getParameter("commodityBrand") != null) ? Long.parseLong(req.getParameter("commodityBrand")) : 0l);
        String status = ((req.getParameter("orderStatus") != null) ? req.getParameter("orderStatus") : "0");
        String delivery = ((req.getParameter("orderDeliveryType") != null) ? req.getParameter("orderDeliveryType") : "0");
        Long email = ((req.getParameter("customerEMail") != null) ? Long.parseLong(req.getParameter("customerEMail")) : 0l);

        List<FilterData> dataFoFilter = new ArrayList<FilterData>();

        for (FilterData data : dataList) {
            if (data.getCommodity().getId().equals(name) || name == 0)
                if (data.getCommodity().getId().equals(brand) || brand == 0)
                    if (data.getPlacedOrder().getStatus().getValue().equals(status) || status.equals("0"))
                        if (data.getPlacedOrder().getDeliveryType().getValue().equals(delivery) || delivery.equals("0"))
                            if (data.getCustomer().getId().equals(email) || email == 0)
                                dataFoFilter.add(data);
        }

        req.setAttribute("filterData", dataFoFilter);
    }
}
