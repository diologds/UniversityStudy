package lv.javaguru.java2.ishop.database.orm;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.domain.Producer;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
@Component(value = "ProducerDAO_ORM")
public class ProducerDAOImpl extends ORMDAOImpl implements ProducerDAO {

    @Override
    public void create(Producer producer) throws DBException {
        getCurrentSession().saveOrUpdate(producer);
    }

    @Override
    public Producer getById(Long id) throws DBException {
        Session session = getCurrentSession();
        return (Producer) session.get(Producer.class, id);
    }

    @Override
    public Producer getByName(String name) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Producer where name = :name ");
        query.setParameter("name", name);
        return (Producer) query.uniqueResult();
    }

    @Override
    public List<Producer> getAll() throws DBException {
        Session session = getCurrentSession();
        // use Hibernate Criteria API to construct query
        Criteria criteria = session.createCriteria(Producer.class);
        return criteria.list();
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = getCurrentSession();
        Producer producer = (Producer) session.get(Producer.class, id);
        session.delete(producer);
    }

    @Override
    public void update(Producer producer) throws DBException {
        Session session = getCurrentSession();
        session.saveOrUpdate(producer);
    }

}
