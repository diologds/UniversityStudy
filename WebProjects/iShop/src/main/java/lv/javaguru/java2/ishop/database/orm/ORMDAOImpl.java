package lv.javaguru.java2.ishop.database.orm;

import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class ORMDAOImpl {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
