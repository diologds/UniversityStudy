package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.OrderedCommodityDAO;
import lv.javaguru.java2.ishop.domain.OrderedCommodity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ann on 24/03/14.
 */
@Component(value = "OrderedCommodityDAO_ORM")
public class OrderedCommodityDAOImpl extends  ORMDAOImpl implements OrderedCommodityDAO
{
    @Override
    public void create(OrderedCommodity orderedCommodity)
    {
        getCurrentSession().saveOrUpdate(orderedCommodity);
    }
    @Override
    public OrderedCommodity getByID(Long orderedCommodityID) throws DBException
    {
        Session session = getCurrentSession();
        return (OrderedCommodity)session.get(OrderedCommodity.class,orderedCommodityID);
    }
    @Override
    public List<OrderedCommodity> getOrderID(Long placeOrderID) throws DBException
    {
        Session session = getCurrentSession();
        Query query = session.createQuery("from OrderedCommodity where IDPlacedOrder = :IDPlacedOrder ");
        query.setParameter("IDPlacedOrder", placeOrderID);
        return query.list();
    }
    @Override
    public List<OrderedCommodity> getCommodityID(Long commodityID) throws DBException
    {
        Session session = getCurrentSession();
        Query query = session.createQuery("from OrderedCommodity where IDCommodity = :IDCommodity ");
        query.setParameter("IDCommodity", commodityID);
        return query.list();
    }
    @Override
    public List<OrderedCommodity> getAll() throws DBException
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(OrderedCommodity.class);
        return criteria.list();
    }

    @Override
    public void delete(Long orderedCommodityID) throws DBException
    {
        Session session = getCurrentSession();
        OrderedCommodity orderedCommodity = (OrderedCommodity)session.get(OrderedCommodity.class, orderedCommodityID);
        session.delete(orderedCommodity);
    }
    @Override
    public void update(OrderedCommodity orderedCommodity) throws DBException
    {
        getCurrentSession().saveOrUpdate(orderedCommodity);
    }
}
