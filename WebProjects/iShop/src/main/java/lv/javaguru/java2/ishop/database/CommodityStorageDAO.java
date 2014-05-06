package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.CommodityStorage;
import lv.javaguru.java2.ishop.domain.StorageType;

import java.util.List;

/**
 * Created by Rita on 14.12.2.
 */
public interface CommodityStorageDAO {

    void create(CommodityStorage storage) throws DBException;

    CommodityStorage getById(Long id) throws DBException;

    List<CommodityStorage> getAll() throws DBException;

    List<CommodityStorage> getByType(StorageType type) throws DBException;

    List<CommodityStorage> getByIdCommodity(Long commodityId) throws DBException;

    void delete(Long id) throws DBException;

    void update(CommodityStorage storage) throws DBException;
}
