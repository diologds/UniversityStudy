package lv.javaguru.java2.ishop.servlet.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Cart;
import lv.javaguru.java2.ishop.domain.CartItem;
import lv.javaguru.java2.ishop.domain.Commodity;
import org.mockito.Spy;

import java.io.IOException;
import java.util.Stack;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;



/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class CartControllerImplTest extends InjectMocksTest {

    @Mock private CommodityDAO commodityDAO;

    @Mock private HttpSession httpSession;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private IshopDispatcher dispatcher;
    @Mock private Cart cart;
    @Spy private Stack<String> taskHandlers = new Stack<String>();

    @InjectMocks private CartControllerImpl cartController = new CartControllerImpl();

    @Before
    public void init()
            throws IOException
     {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("cart")).thenReturn(cart);
        when(httpSession.getAttribute("taskHandlers")).thenReturn(taskHandlers);

    }

    @Test
    public void testPerformTask()
            throws DBException, IOException, ServletException
    {

        doThrow(new RuntimeException()).when(dispatcher).redirectToPageHandler(response, "");
        cartController.performTask(request, response);
        verify(taskHandlers, times(0)).pop();
        verify(dispatcher, times(1)).redirectToPageHandler(response,null);
    }

    @Test
    public void testAddCommodity()
            throws DBException, IOException, ServletException
    {
        when(request.getParameter("addCommodity")).thenReturn("1");

        Commodity commodity = new Commodity();
        when(commodityDAO.getById(1L)).thenReturn(commodity);

        cartController.performTask(request, response);

        verify(cart, times(1)).addItem(any(CartItem.class));
        verify(commodityDAO, times(1)).getById(1L);
    }

    @Test
    public void testRemoveCommodity()
            throws DBException, IOException, ServletException
    {
        when(request.getParameter("remCommodity")).thenReturn("1");

        cartController.performTask(request, response);

        verify(cart, times(1)).deleteItem(1L);
        verifyZeroInteractions(commodityDAO);
    }

}
