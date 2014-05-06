package lv.javaguru.java2.ishop.servlet.mvc.business_model.action_handlers;

import lv.javaguru.java2.ishop.database.DBException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Ann on 04/04/14.
 */

public interface ActionHandler
{
    boolean update(HttpServletRequest req,
                   StringBuilder dbMessage,
                   Map<String, Object> inputData,
                   Map<String, String> inputErrors) throws DBException;

    boolean add(HttpServletRequest req,
                StringBuilder dbMessage,
                Map<String, Object> inputData,
                Map<String, String> inputErrors) throws DBException;

    void  refresh(HttpServletRequest req,
                  StringBuilder dbMessage,
                  Map<String, Object> inputData,
                  Map<String, String> inputErrors) throws DBException;

     boolean delete(HttpServletRequest req, StringBuilder dbMessage)
            throws DBException;
}
