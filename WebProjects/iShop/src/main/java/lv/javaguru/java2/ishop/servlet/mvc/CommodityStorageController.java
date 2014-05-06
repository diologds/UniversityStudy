package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.CommodityStorageDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.CommodityStorage;
import lv.javaguru.java2.ishop.domain.StorageType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Rita on 14.21.2.
 */
@Component
public class CommodityStorageController implements Controller {

    @Qualifier("CommodityDAO_JDBC")
    @Autowired
    private CommodityDAO commodityDAO;

    @Autowired
    @Qualifier("CommodityStorageDAO_JDBC")
    private CommodityStorageDAO commodityStorageDAO;

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        if (req.getParameter("submit")!=null){
            addStorage(req);
        }
        List<Commodity> commodities = commodityDAO.getAll();
        return new Model("commodityStorage.jsp", commodities);
    }

    private void addStorage(HttpServletRequest req){

        Long paramIDCommodity;
        StorageType paramStorageType;
        Long paramQuantity;

        paramIDCommodity = Long.parseLong(req.getParameter("idCommodity"));
        paramStorageType = StorageType.getEnum(req.getParameter("storageType"));
        paramQuantity = Long.parseLong(req.getParameter("quantity"));


        // saving form data into DB
        CommodityStorage storage = new CommodityStorage(paramIDCommodity, paramStorageType, paramQuantity);
        try {
            commodityStorageDAO.create(storage);
        } catch (DBException e) {
            e.printStackTrace();
        }

    }

}
