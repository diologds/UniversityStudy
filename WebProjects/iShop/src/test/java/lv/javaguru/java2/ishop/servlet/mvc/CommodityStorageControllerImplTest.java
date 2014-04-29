package lv.javaguru.java2.ishop.servlet.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.CommodityStorageDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.CommodityStorage;
import lv.javaguru.java2.ishop.domain.StorageType;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static lv.javaguru.java2.ishop.database.builders.CommodityBuilder.createCommodity;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by Rita on 14.31.3.
 */
public class CommodityStorageControllerImplTest extends InjectMocksTest {

    @Mock private CommodityDAO commodityDAO;
    @Mock private CommodityStorageDAO commodityStorageDAO;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;

    @InjectMocks
    private CommodityStorageEditControllerImpl commodityStorageEditController=new CommodityStorageEditControllerImpl();


    @Test
    public void testReturnModel() throws DBException {
        ModelAndView model = commodityStorageEditController.getModel(request, response);

        assertNotNull(model);
        assertNotNull(model.getModel());
        verify(request, times(1)).setAttribute("msg", " ");
    }

    @Test
    public void testAddStorage_Success() throws DBException {
        // Prepare test data
        Long commodityId = 1L;
        StorageType storageType = StorageType.local;
        when(request.getParameter("adding")).thenReturn("true");
        when(request.getParameter("idCommodity")).thenReturn("" + commodityId);
        when(request.getParameter("storageType")).thenReturn(storageType.toString());
        when(request.getParameter("quantity")).thenReturn("1");

        Commodity commodity = new Commodity();
        when(commodityDAO.getById(commodityId)).thenReturn(commodity);

        when(commodityStorageDAO.getByType(storageType)).thenReturn(Collections.<CommodityStorage>emptyList());

        // Invoke code under test
        commodityStorageEditController.getModel(request, response);

        // Check results
        verify(request, times(1)).setAttribute("msg", "Storage item is successfully saved into DB! Review Storage item list.");

        CommodityStorage storage = new CommodityStorage(commodity, storageType, 1L);
        verify(commodityStorageDAO, times(1)).create(storage);
    }

    @Test
    public void testAddStorage_FAIL() throws DBException {
        // Prepare test data
        Long commodityId = 1L;
        StorageType storageType = StorageType.local;
        when(request.getParameter("adding")).thenReturn("true");
        when(request.getParameter("idCommodity")).thenReturn("" + commodityId);
        when(request.getParameter("storageType")).thenReturn(storageType.toString());
        when(request.getParameter("quantity")).thenReturn("1");

        Commodity commodity = createCommodity()
                .withId(commodityId)
                .withName("name").build();
        when(commodityDAO.getById(commodityId)).thenReturn(commodity);

        List<CommodityStorage> commodityStorages = new ArrayList<CommodityStorage>();
        commodityStorages.add(new CommodityStorage(commodity, storageType, 1L));
        when(commodityStorageDAO.getByIdCommodity(commodityId)).thenReturn(commodityStorages);

        // Invoke code under test
        commodityStorageEditController.getModel(request, response);

        // Check results
        verify(request, times(2)).setAttribute(eq("msg"), any(String.class));
        InOrder inOrder = inOrder(request);
        inOrder.verify(request).setAttribute(eq("msg"), eq(" "));
        inOrder.verify(request).setAttribute(eq("msg"), eq("Entered storage item already exists!"));
        verify(commodityStorageDAO, times(0)).create(any(CommodityStorage.class));
    }

    @Test
    public void testUpdateStorage() throws DBException{
        Long storageId = 1L;
        Long commodityId = 1L;
        StorageType storageType = StorageType.local;
        when(request.getParameter("editing")).thenReturn("true");
        when(request.getParameter("idStorage")).thenReturn("" + storageId);
        when(request.getParameter("idCommodity")).thenReturn("" + commodityId);
        when(request.getParameter("storageType")).thenReturn(storageType.toString());
        when(request.getParameter("quantity")).thenReturn("10");

        Commodity commodity = createCommodity().withName("name").build();
        when(commodityDAO.getById(commodityId)).thenReturn(commodity);

        CommodityStorage storage = new CommodityStorage(commodity, StorageType.remote, 1L);
        when(commodityStorageDAO.getById(storageId)).thenReturn(storage);

        storage.setCommodity(commodity);
        storage.setType(storageType);
        storage.setQuantity(10L);

        commodityStorageEditController.getModel(request, response);

        verify(request, times(1)).setAttribute("msg", "Storage item is successfully updated in DB! Review Storage item list.");
        verify(commodityStorageDAO, times(1)).update(storage);
    }

    @Test
    public void testDeleteStorage() throws DBException{
        when(request.getParameter("delete")).thenReturn("1");

        commodityStorageEditController.getModel(request, response);

        verify(request, times(1)).setAttribute("msg", "Storage item is successfully deleted from DB! Review Storage item list.");
        verify(commodityStorageDAO, times(1)).delete(1L);

    }

}
