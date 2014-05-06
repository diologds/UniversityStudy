package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.Like;

import java.util.List;

/**
 * Created by Lilya on 23.03.14.
 */
public interface LikeDAO {

    void create(Like like) throws DBException;

    List<Like> getByCommodity(Long idCommodity) throws DBException;

    List<Like> getByCustomer(Long idCustomer) throws DBException;

    void delete(Long id) throws DBException;

    void update(Like like) throws DBException;

    List<Like> getAll() throws DBException;

}
