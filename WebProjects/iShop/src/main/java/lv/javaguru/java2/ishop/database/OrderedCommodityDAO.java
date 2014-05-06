package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.OrderedCommodity;

import java.util.List;

public interface OrderedCommodityDAO {

    void create(OrderedCommodity orderedCommodity);

    OrderedCommodity getByID(Long orderedCommodityID) throws DBException;

    List<OrderedCommodity> getOrderID(Long placedOrderID) throws DBException;

    List<OrderedCommodity> getAll() throws DBException;

    List<OrderedCommodity> getCommodityID(Long commodityID) throws DBException;

    void delete(Long orderedCommodityID) throws DBException;

    void update(OrderedCommodity orderedCommodity) throws DBException;
}

