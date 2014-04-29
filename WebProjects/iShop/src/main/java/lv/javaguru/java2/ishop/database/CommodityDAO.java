
package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.Commodity;

import java.util.List;

/**
 * Created by Ann on 08/02/14.
 */
public interface CommodityDAO
{
    void create(Commodity commodity) throws DBException;

    Commodity getById(Long id) throws DBException;

    Commodity getByName(String name) throws DBException;

    List<Commodity> getAll() throws DBException;

    List<Commodity> getByCategory(Long idCategory) throws DBException;

    void delete(Long id) throws DBException;

    void update(Commodity commodity) throws DBException;

    List<Commodity> searchByName(String commodityName) throws DBException;

    //List<Commodity> getProducerCategoryNames() throws DBException;

}
