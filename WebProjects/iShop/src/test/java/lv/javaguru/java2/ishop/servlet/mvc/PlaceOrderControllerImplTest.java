package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class PlaceOrderControllerImplTest extends InjectMocksTest {
    @Mock
    private PlacedOrderDAO placedOrderDAO;
    @Mock
    private OrderedCommodityDAO orderedCommodityDAO;
    @Mock
    private CommodityViewDAO commodityViewDAO;
    @Mock
    private CommodityDAO commodityDAO;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Cart cart;
    @Mock
    private CartItem item;
    @Mock
    private Commodity commodityModification;

    @InjectMocks
    private PlaceOrderControllerImpl PlaceOrderController = new PlaceOrderControllerImpl();

    @Before
    public void init() throws DBException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("cart")).thenReturn(cart);
        List<CartItem> cartItems = new ArrayList<CartItem>() {{
            add(item);
        }};
        when(cart.getItems()).thenReturn(cartItems);
        when(item.getCommodity()).thenReturn(commodityModification);
        when(commodityModification.getId()).thenReturn(0l);
        when(commodityModification.getPrice()).thenReturn(BigDecimal.ONE);
        Customer customer = new Customer();
        when(httpSession.getAttribute("user")).thenReturn(customer);
    }

    @Test
    public void testReturnModel() throws DBException {
        ModelAndView model = PlaceOrderController.getModel(request, response);
        assertNotNull(model);
        assertNotNull(model.getModel());
    }

    @Test
    public void testCheckOut() throws DBException {
        when(request.getParameter("checkout")).thenReturn("true");
        when(request.getParameter("deliveryType")).thenReturn("toCustomerAddress");
        PlaceOrderController.getModel(request, response);
        verify(placedOrderDAO, times(1)).create(any(PlacedOrder.class));
    }

    @Test
    public void testDelete() throws DBException {
        when(request.getParameter("remove")).thenReturn("0");
        PlaceOrderController.getModel(request, response);
        verify(cart, times(1)).deleteItem(any(CartItem.class));
    }

    @Test
    public void testDec() throws DBException {
        when(request.getParameter("dec")).thenReturn("0");
        PlaceOrderController.getModel(request, response);
        verify(item, times(1)).dec();
    }

    @Test
    public void testAdd() throws DBException {
        when(request.getParameter("inc")).thenReturn("0");
        PlaceOrderController.getModel(request, response);
        verify(item, times(1)).inc();
    }

}
