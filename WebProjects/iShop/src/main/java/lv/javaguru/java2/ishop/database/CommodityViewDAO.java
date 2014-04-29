package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.Image;
import lv.javaguru.java2.ishop.domain.ImageType;

import java.util.List;

public interface CommodityViewDAO
{
    void create(CommodityView commodityView) throws DBException;

    CommodityView getById(Long id) throws DBException;

    List<CommodityView> getByIdCommodity(Long idCommodity) throws DBException;

    List<Image> getImagesByIdCommodity(Long idCommodity) throws DBException;

    List<CommodityView> getByImageType(ImageType type) throws DBException;

    List<CommodityView> getAll() throws DBException;

    void delete(Long id) throws DBException;

    void update(CommodityView commodityView) throws DBException;
}
