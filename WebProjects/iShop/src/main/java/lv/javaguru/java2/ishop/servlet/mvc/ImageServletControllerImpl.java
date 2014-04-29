package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.CommodityViewDAO;
import lv.javaguru.java2.ishop.domain.Image;
import lv.javaguru.java2.ishop.domain.ImageConverter;
import lv.javaguru.java2.ishop.domain.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ann on 28/03/14.
 */
@Component
public class ImageServletControllerImpl implements ImageServletController
{
    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    @Qualifier("CommodityViewDAO_ORM")
    private CommodityViewDAO commodityViewDAO;

    private final String dirPath = "." + File.separator + ".." + File.separator + "temp" + File.separator + "images";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = Isolation.REPEATABLE_READ)
    public Model getModel(HttpServletRequest req,HttpServletResponse resp) throws DBException
    {
        File outputDir = new File(dirPath);
        if (!outputDir.exists())
            outputDir.mkdir();

        Image image = new Image();

        String imageName = req.getParameter("imageName");
        String filePath = dirPath + File.separator + imageName;
        File outputFile = new File(filePath);
        String[] nameParts = imageName.split("\\.");
        ImageType imageType = ImageType.getEnum(nameParts[1]);
        image.setImagePath(filePath);
        image.setType(imageType);
        if (outputFile.exists())
        {

            return  new Model("ImageServlet",image);

        }

        /*if local (nonDB) file is referred*/
        if (this.getClass().getClassLoader().getResource(imageName)!=null)
        {      try
        {
            imageConverter.writeImage(imageConverter.getBytes(imageName,imageType),imageType,filePath);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
            return  new Model("ImageServlet",image);


        }
        Long imageId = Long.parseLong(nameParts[0]);
        byte[] imageInByte = commodityViewDAO.getById(imageId).getCommodityPhoto();
        try
        {
            imageConverter.writeImage(imageInByte,imageType,filePath);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new Model("ImageServlet",image);
    }

}
