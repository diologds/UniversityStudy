package lv.javaguru.java2.ishop.servlet.mvc.business_model.action_handlers;

import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Producer;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.checkers.CommodityChecker;
import lv.javaguru.java2.ishop.servlet.mvc.business_model.validators.CommodityValidator;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ann on 04/04/14.
 */
@Component
public class CommodityActionHandler implements ActionHandler
{
    @Autowired
    CommodityChecker checker;

    @Autowired
    CommodityValidator validator;

    @Qualifier("ProducerDAO_ORM")
    @Autowired
    private ProducerDAO producerDAO;

    @Qualifier("CategoryDAO_ORM")
    @Autowired
    private CategoryDAO categoryDAO;

    @Qualifier("CommodityDAO_ORM")
    @Autowired
    private CommodityDAO commodityDAO;

    private static Logger logger = Logger.getLogger(CommodityActionHandler.class.getName());

    @Override
    public boolean delete(HttpServletRequest req, StringBuilder dbMessage)
            throws DBException
    {
        if (!checker.checkDeleteConditions(req, dbMessage))
        {

            return false;
        }
        Long paramIDCommodity = Long.parseLong(req.getParameter("deleting"));
        try
        {
            commodityDAO.delete(paramIDCommodity);
        }
        catch (DBException e)
        {
            logger.log(Level.SEVERE, "Exception while deleting commodity", e);
            dbMessage.append("DB error: Exception while deleting commodity!");
            return false;
        }
        dbMessage.append("DB message: Commodity was successfully deleted from DB! Review Commodities list.");
        return  true;
    }
    @Override
    public boolean add(HttpServletRequest req,
                        StringBuilder dbMessage,
                        Map<String,Object> inputData,
                        Map<String,String> inputErrors)
            throws DBException {

        if(!validator.validate(req,inputData,inputErrors))
        {
            return false;
        }
        if (!checker.checkInsertConditions(req, dbMessage))
        {
            logger.log(Level.INFO, "Commodity with selected name exists!");
            return false;
        }
        Producer paramProducer=producerDAO.getById(Long.parseLong(req.getParameter("idProducer")));
        Category paramCategory=categoryDAO.getById(Long.parseLong(req.getParameter("idCategory")));
        String paramName=req.getParameter("name");
        BigDecimalValidator bigDecimalValidator = new BigDecimalValidator();
        BigDecimal paramPrice = bigDecimalValidator.validate(req.getParameter("price"));
        String paramDescription=req.getParameter("description");
        String paramBrand=req.getParameter("brand");
        String paramRef=req.getParameter("ref");
        String paramUrl=req.getParameter("url");

        Commodity commodity = new Commodity(paramProducer, paramCategory, paramName, paramPrice,
                paramDescription, paramBrand, paramRef,paramUrl);
        try {
            commodityDAO.create(commodity);

        }
        catch (DBException e)
        {
            logger.log(Level.SEVERE, "Exception while inserting commodity", e);
            dbMessage.append("DB error: Exception while inserting commodity!");
            return  false;
        }
        dbMessage.append("DB message: Commodity was successfully added to DB! Review Commodities list.");
        return true;

    }
    @Override
    public boolean update(HttpServletRequest req,
                           StringBuilder dbMessage,
                           Map<String,Object> inputData,
                           Map<String,String> inputErrors)
                   throws DBException{

        if(!validator.validate(req,inputData,inputErrors))
        {
            logger.log(Level.INFO, "checkInput = false");
            return false;
        }
        if (!checker.checkUpdateConditions(req, dbMessage))
        {
            logger.log(Level.INFO, "checkUpdate = false");
            return false;
        }
        Long paramIDCommodity=Long.parseLong(req.getParameter("updating"));
        Producer paramProducer=producerDAO.getById(Long.parseLong(req.getParameter("idProducer")));
        Category paramCategory=categoryDAO.getById(Long.parseLong(req.getParameter("idCategory")));
        String paramName=req.getParameter("name");

        BigDecimalValidator bigDecimalValidator = new BigDecimalValidator();
        BigDecimal paramPrice = bigDecimalValidator.validate(req.getParameter("price"));

        String paramDescription=req.getParameter("description");
        String paramBrand=req.getParameter("brand");
        String paramRef=req.getParameter("ref");
        String paramUrl=req.getParameter("url");

        Commodity commodity = commodityDAO.getById(paramIDCommodity);

        logger.log(Level.INFO, "updateCommodity: commodity got bi id");
        commodity.setProducer(paramProducer);
        commodity.setCategory(paramCategory);
        commodity.setName(paramName);
        commodity.setPrice(paramPrice);
        commodity.setDescription(paramDescription);
        commodity.setBrand(paramBrand);
        commodity.setRef(paramRef);
        commodity.setUrl(paramUrl);
        logger.log(Level.INFO, "updateCommodity: commodity set");
        try
        {
            commodityDAO.update(commodity);
            logger.log(Level.INFO, "updateCommodity: commodity updated");

        }
        catch (DBException e)
        {
            logger.log(Level.SEVERE, "Exception while updating commodity", e);
            dbMessage.append("DB error: Exception while updating commodity!");
            return  false;
        }
        dbMessage.append("DB message: Commodity was successfully updated in DB! Review Commodities list.");
        return  true;

    }
    @Override
    public void refresh(HttpServletRequest req,
                    StringBuilder dbMessage,
                    Map<String,Object> inputData,
                    Map<String,String> inputErrors) throws DBException
    {
        validator.validate(req, inputData, inputErrors);
        logger.log(Level.INFO,"inputData:"+inputData.toString());
        logger.log(Level.INFO,"inputErrors:"+inputErrors.toString());
    }

}
