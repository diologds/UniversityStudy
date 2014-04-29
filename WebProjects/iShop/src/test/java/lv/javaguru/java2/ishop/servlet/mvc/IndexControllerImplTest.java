package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.IshopDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Stack;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Ann on 06/04/14.
 */
public class IndexControllerImplTest extends InjectMocksTest
{
    @Mock private HttpSession httpSession;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private IshopDispatcher dispatcher;
    @Mock private CategoryDAO categoryDAO;
    @Mock private CommodityDAO commodityDAO;
    @Mock private ModelAndView model;

    @Spy private Stack<String> taskHandlers = null;

    @InjectMocks
    private IndexControllerImpl indexController = new IndexControllerImpl();

    @Before
    public void init()
            throws IOException
    {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("taskHandlers")).thenReturn(taskHandlers);
        taskHandlers = new Stack<String>();
    }
    @Test
    public void testPerformTask()
            throws IOException, ServletException
    {
        doThrow(new RuntimeException()).when(dispatcher).forwardToTaskHandler(request, response, "");
        indexController.performTask(request, response);
        verify(dispatcher, times(1)).forwardToTaskHandler(request,response, "cart");
    }
    @Test
    public void testGetModel()
            throws DBException,IOException
    {
        ModelAndView someModel = indexController.getModel(request, response);
        assertNotNull(someModel);
        verify(categoryDAO, times(1)).getAll();
        verify(commodityDAO, times(1)).getAll();
    }

}
