package lv.javaguru.java2.ishop.servlet.mvc.business_model.validators;

import lv.javaguru.java2.ishop.database.DBException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Ann on 03/04/14.
 */
public interface Validator
{

    boolean validate(HttpServletRequest req, Map<String, Object> inputData,
                     Map<String, String> inputErrors)
            throws DBException;
}
