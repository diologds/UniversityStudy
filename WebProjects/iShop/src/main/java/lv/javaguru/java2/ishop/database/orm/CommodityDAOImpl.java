package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.CommodityDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Commodity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Rita on 14.13.3.
 */
@Component(value = "CommodityDAO_ORM")
public class CommodityDAOImpl extends ORMDAOImpl implements CommodityDAO {
    @Override
    public void create(Commodity commodity) throws DBException {
        getCurrentSession().saveOrUpdate(commodity);
    }

    @Override
    public Commodity getById(Long id) throws DBException {
        Session session = getCurrentSession();
        return (Commodity)session.get(Commodity.class, id);
    }

    @Override
    public Commodity getByName(String name) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Commodity where name= :name");
        query.setParameter("name", name);
        return (Commodity)query.uniqueResult();
    }

    @Override
    public List<Commodity> getAll() throws DBException {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Commodity.class);
        return criteria.list();
    }

    @Override
    public List<Commodity> getByCategory(Long idCategory) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Commodity where idCategory = :idCategory ");
        query.setParameter("idCategory", idCategory);
        return query.list();
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = getCurrentSession();
        Commodity commodity = (Commodity)session.get(Commodity.class, id);
        session.delete(commodity);
    }

    @Override
    public void update(Commodity commodity) throws DBException {
        Session session = getCurrentSession();
        session.saveOrUpdate(commodity);
    }

    @Override
    public List<Commodity> searchByName(String commodityName) throws DBException {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Commodity.class);
        criteria.add(Restrictions.like("name", "%" + commodityName + "%"));
        return criteria.list();
    }

 }
