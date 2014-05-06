package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.CommodityViewDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.Image;
import lv.javaguru.java2.ishop.domain.ImageType;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component (value = "CommodityViewDAO_ORM")
public class CommodityViewDAOImpl extends ORMDAOImpl implements CommodityViewDAO {
    @Override
    public void create(CommodityView commodityView) throws DBException {
        getCurrentSession().saveOrUpdate(commodityView);
    }

    @Override
    public CommodityView getById(Long id) throws DBException {
        Session session = getCurrentSession();
        return (CommodityView) session.get(CommodityView.class, id);
    }

    @Override
    public List<CommodityView> getByIdCommodity(Long idCommodity) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from CommodityView where IDCommodity = :IDCommodity ");
        query.setParameter("IDCommodity", idCommodity);
        return query.list();
    }

    @Override
    public List<Image> getImagesByIdCommodity(Long idCommodity) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from CommodityView where IDCommodity = :IDCommodity ");
        query.setParameter("IDCommodity", idCommodity);
        List<CommodityView> recivedList = query.list();

        List<Image> list = new ArrayList<Image>();
        for (CommodityView view: recivedList) {
            Long id = view.getId();
            String imagePath = id.toString();
            list.add(new Image(imagePath,view.getCommodityPhotoType()));
        }
        return list;
    }

    @Override
    public List<CommodityView> getByImageType(ImageType imageType) throws DBException {
        Session session = getCurrentSession();
        Query query = session.createQuery("from CommodityView where CommodityPhotoType = :CommodityPhotoType ");
        System.out.println("#######" + imageType.toString());
        query.setParameter("CommodityPhotoType", imageType.toString());
        return query.list();
    }

    @Override
    public List<CommodityView> getAll() throws DBException {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(CommodityView.class);
        return criteria.list();
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = getCurrentSession();
        CommodityView storage = (CommodityView) session.get(CommodityView.class, id);
        session.delete(storage);
    }

    @Override
    public void update(CommodityView commodityView) throws DBException {
        Session session = getCurrentSession();
        session.update(commodityView);
    }
}
