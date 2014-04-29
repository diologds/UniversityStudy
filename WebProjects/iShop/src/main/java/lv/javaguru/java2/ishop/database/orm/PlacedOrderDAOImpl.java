package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.PlacedOrderDAO;
import lv.javaguru.java2.ishop.domain.Category;
import lv.javaguru.java2.ishop.domain.OrderDeliveryType;
import lv.javaguru.java2.ishop.domain.OrderStatus;
import lv.javaguru.java2.ishop.domain.PlacedOrder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaylapshin on 08/02/14. JDBC implementatio
 */
@Component(value = "PlacedOrderDAO_ORM")
public class PlacedOrderDAOImpl extends ORMDAOImpl implements PlacedOrderDAO {
    @Override
    public void create(PlacedOrder order) throws DBException {
        getCurrentSession().saveOrUpdate(order);
    }

    @Override
    public PlacedOrder getById(Long id) throws DBException {
        Session session = getCurrentSession();
        return (PlacedOrder) session.get(PlacedOrder.class, id);
    }

    @Override
    public List<PlacedOrder> getByCustomerId(Long id) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from PlacedOrder where IDCustomer = :id ");
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public List<PlacedOrder> getByStatus(OrderStatus status) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from PlacedOrder where PlacedOrderStatus = :status ");
        query.setParameter("status", status.toString());
        return query.list();
    }

    @Override
    public List<PlacedOrder> getAll() throws DBException {
        Session session = getCurrentSession();
        // use Hibernate Criteria API to construct query
        Criteria criteria = session.createCriteria(PlacedOrder.class);
        return criteria.list();
    }

    @Override
    public List<PlacedOrder> getByDeliveryType(OrderDeliveryType type) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from PlacedOrder where PlacedOrderDeliveryType = :type ");
        query.setParameter("type", type.toString());
        return query.list();
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = getCurrentSession();
        PlacedOrder placedOrder = (PlacedOrder) session.get(PlacedOrder.class, id);
        session.delete(placedOrder);
    }

    @Override
    public void update(PlacedOrder order) throws DBException {
        Session session = getCurrentSession();
        session.saveOrUpdate(order);
    }

    @Override
    public List<PlacedOrder> getList() throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from PlacedOrder order by IDPlacedOrder");
        return query.list();
    }

}
