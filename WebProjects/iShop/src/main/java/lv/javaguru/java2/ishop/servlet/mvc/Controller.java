package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public interface Controller {

    Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException;

}
