package lv.javaguru.java2.ishop.database.jdbc;

import lv.javaguru.java2.ishop.database.CommodityViewDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.Image;
import lv.javaguru.java2.ishop.domain.ImageType;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(value = "CommodityViewDAO_JDBC")
public class CommodityViewDAOImpl extends DAOImpl implements CommodityViewDAO {

    @Override
    public void create(CommodityView commodityView) throws DBException{
        if (commodityView == null) {
            return;
        }
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement("insert into CommodityView (IDCommodityView," +
                    " IDCommodity, CommodityPhoto, CommodityPhotoType,GalleryPhoto)  " +
                    "values (default, ?, ?, ? , ?)");
            preparedStatement.setLong(1, commodityView.getIdCommodity());
            preparedStatement.setBytes(2, commodityView.getCommodityPhoto());
            preparedStatement.setString(3, commodityView.getCommodityPhotoType().toString());
            preparedStatement.setBoolean(4, commodityView.isGalleryPhoto());
            preparedStatement.executeUpdate();
        } catch (Throwable e){
            System.out.println("Exception while execute CommodityViewDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
    }

    @Override
    public CommodityView getById(Long id)  throws DBException{
        CommodityView commodityView = null;
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("select * from  CommodityView where IDCommodityView = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                commodityView = new CommodityView();
                commodityView.setId(resultSet.getLong("IDCommodityView"));
                commodityView.setIdCommodity(resultSet.getLong("IDCommodity"));
                commodityView.setCommodityPhoto(resultSet.getBytes("CommodityPhoto"));
                commodityView.setCommodityPhotoType(ImageType.getEnum(resultSet.getString("CommodityPhotoType")));
                commodityView.setGalleryPhoto(resultSet.getBoolean("GalleryPhoto"));

            }

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
        return commodityView;
    }

    @Override
    public List<CommodityView> getByImageType(ImageType type) throws DBException{
        CommodityView commodityView = null;
        List<CommodityView> list = new ArrayList<CommodityView>();
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("select * from  CommodityView where CommodityPhotoType = ?");
            preparedStatement.setString(1, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                commodityView = new CommodityView();
                commodityView.setId(resultSet.getLong("IDCommodityView"));
                commodityView.setIdCommodity(resultSet.getLong("IDCommodity"));
                commodityView.setCommodityPhoto(resultSet.getBytes("CommodityPhoto"));
                commodityView.setCommodityPhotoType(ImageType.getEnum(resultSet.getString("CommodityPhotoType")));
                commodityView.setGalleryPhoto(resultSet.getBoolean("GalleryPhoto"));
                list.add(commodityView);
            }

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.getByType()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
        return list;
    }

    @Override
    public List<CommodityView> getByIdCommodity(Long idCommodity) throws DBException{
        CommodityView commodityView = null;
        List<CommodityView> list = new ArrayList<CommodityView>();
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("select * from  CommodityView where IDCommodity = ?");
            preparedStatement.setLong(1, idCommodity);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                commodityView = new CommodityView();
                commodityView.setId(resultSet.getLong("IDCommodityView"));
                commodityView.setIdCommodity(resultSet.getLong("IDCommodity"));
                commodityView.setCommodityPhoto(resultSet.getBytes("CommodityPhoto"));
                commodityView.setCommodityPhotoType(ImageType.getEnum(resultSet.getString("CommodityPhotoType")));
                commodityView.setGalleryPhoto(resultSet.getBoolean("GalleryPhoto"));
                list.add(commodityView);
            }

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.getByIdCommodity()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
        return list;
    }
    @Override
    public List<Image> getImagesByIdCommodity(Long idCommodity) throws DBException
    {
        List<Image> list = new ArrayList<Image>();
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("select IDCommodityView, CommodityPhotoType from  CommodityView where IDCommodity = ?");
            preparedStatement.setLong(1, idCommodity);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
               Long id = resultSet.getLong("IDCommodityView");
               String imagePath = id.toString();
               ImageType type = ImageType.getEnum(resultSet.getString("CommodityPhotoType"));

               list.add(new Image(imagePath,type));


            }

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.getImagesByIdCommodity()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
        return list;
    }

    @Override
    public List<CommodityView> getAll()  throws DBException {
        CommodityView commodityView = null;
        List<CommodityView> list = new ArrayList<CommodityView>();
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement  preparedStatement = connect
                    .prepareStatement("select * from  CommodityView ");
            ResultSet  resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                commodityView = new CommodityView();
                commodityView.setId(resultSet.getLong("IDCommodityView"));
                commodityView.setIdCommodity(resultSet.getLong("IDCommodity"));
                commodityView.setCommodityPhoto(resultSet.getBytes("CommodityPhoto"));
                commodityView.setCommodityPhotoType(ImageType.getEnum(resultSet.getString("CommodityPhotoType")));
                commodityView.setGalleryPhoto(resultSet.getBoolean("GalleryPhoto"));
                list.add(commodityView);
            }

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.getByIdCommodity()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
        return list;
    }

    @Override
    public void delete(Long id) throws DBException{
        Connection connect = null;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("delete from CommodityView where IDCommodityView = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
    }

    @Override
    public void update(CommodityView commodityView) throws DBException{
        Connection connect = null;
        if (commodityView == null)
            return;
        try {
            connect = getConnection();
            PreparedStatement preparedStatement = connect
                    .prepareStatement("update CommodityView set IDCommodity = ?, CommodityPhoto = ?, CommodityPhotoType = ?, GalleryPhoto = ? " +
                            "where IDCommodityView = ?");
            preparedStatement.setLong(1, commodityView.getIdCommodity());
            preparedStatement.setBytes(2, commodityView.getCommodityPhoto());
            preparedStatement.setString(3, commodityView.getCommodityPhotoType().toString());
            preparedStatement.setBoolean(4, commodityView.isGalleryPhoto());
            preparedStatement.setLong(5, commodityView.getId());
            preparedStatement.executeUpdate();

        } catch (Throwable e)
        {
            System.out.println("Exception while execute CommodityViewDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
    }



}
