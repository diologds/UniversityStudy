package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.CommodityStorageDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.CommodityStorage;
import lv.javaguru.java2.ishop.domain.StorageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Rita on 14.21.2.
 */
@Controller
@RequestMapping("commodityStorageEdit")
public class CommodityStorageEditControllerImpl {
    private static Logger logger = Logger.getLogger(CommodityStorageEditControllerImpl.class.getName());

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;
    @Autowired
    @Qualifier("CommodityStorageDAO_ORM")
    private CommodityStorageDAO commodityStorageDAO;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        req.setAttribute("msg", " ");

        if (req.getParameter("adding")!=null){
            addStorage(req);
        }
        if (req.getParameter("editing")!=null){
            updateStorage(req);
        }
        if (req.getParameter("delete")!=null){
            deleteStorageItem(req);
        }
        if (req.getParameter("cancel")!=null){
            req.setAttribute("editing",null);
        }

        ModelAndView model = prepareModelAndView();

        return model;
    }

    private ModelAndView prepareModelAndView() throws DBException {
        Map<String, Object> map = new HashMap<String, Object>();

        List<CommodityStorage> storages = commodityStorageDAO.getAll();

        List<Commodity> commodities = commodityDAO.getAll();

        map.put("commodities", commodities);
        map.put("storages", storages);

        ModelAndView model = new ModelAndView();
        model.setViewName("commodityStorageEdit");
        model.addObject("model", map);
        return model;
    }

    private void addStorage(HttpServletRequest req) throws DBException {
        Long commodityId = Long.parseLong(req.getParameter("idCommodity"));
        StorageType paramStorageType = StorageType.getEnum(req.getParameter("storageType"));
        Long paramQuantity = Long.parseLong(req.getParameter("quantity"));

        Commodity paramCommodity = commodityDAO.getById(commodityId);
        if (verifyIdentity(paramCommodity, paramStorageType)){
            CommodityStorage storage = new CommodityStorage(paramCommodity, paramStorageType, paramQuantity);
            try {
                commodityStorageDAO.create(storage);
                req.setAttribute("msg", "Storage item is successfully saved into DB! Review Storage item list.");
            } catch (DBException e) {
                logger.log(Level.SEVERE, "Exception while commodity creation", e);
            }
        }else {
            req.setAttribute("msg","Entered storage item already exists!");
        }
    }

    private void updateStorage(HttpServletRequest req) throws DBException {
        Long paramIDStorage = Long.parseLong(req.getParameter("idStorage"));
        Commodity paramCommodity = commodityDAO.getById(Long.parseLong(req.getParameter("idCommodity")));
        StorageType paramStorageType = StorageType.getEnum(req.getParameter("storageType"));
        Long paramQuantity = Long.parseLong(req.getParameter("quantity"));

        CommodityStorage storage = commodityStorageDAO.getById(paramIDStorage);
        storage.setCommodity(paramCommodity);
        storage.setType(paramStorageType);
        storage.setQuantity(paramQuantity);
        try {
            commodityStorageDAO.update(storage);
            req.setAttribute("msg","Storage item is successfully updated in DB! Review Storage item list.");
        } catch (DBException e) {
            logger.log(Level.SEVERE, "Exception while update commodity", e);
        }
    }

    private void deleteStorageItem(HttpServletRequest req) {
        Long paramIDStorage=Long.parseLong(req.getParameter("delete"));
        try {
            commodityStorageDAO.delete(paramIDStorage);
            req.setAttribute("msg","Storage item is successfully deleted from DB! Review Storage item list.");
        } catch (DBException e) {
            logger.log(Level.SEVERE, "Exception while delete commodity", e);
        }
    }

    private boolean verifyIdentity(Commodity paramCommodity, StorageType paramStorageType) throws DBException {
        //List<CommodityStorage> storageListFromDB = commodityStorageDAO.getByType(paramStorageType);

        //List is able to include max 2 objects
        List<CommodityStorage> storageListFromDB = commodityStorageDAO.getByIdCommodity(paramCommodity.getId());
        for (CommodityStorage storage : storageListFromDB){
            //if (paramCommodity.getName().equals(storage.getCommodity().getName())) {
            if (paramStorageType.equals(storage.getType())){
                return false;
            }
        }

        return true;
    }
}


