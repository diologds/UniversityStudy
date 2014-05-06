package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.DBException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lilya on 23.03.14.
 */

@Component
public class LikeControllerImpl implements LikeController {

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        return null;
    }
}
