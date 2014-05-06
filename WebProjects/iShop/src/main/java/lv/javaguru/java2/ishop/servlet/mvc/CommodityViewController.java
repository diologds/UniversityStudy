package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.jdbc.CommodityViewDAOImpl;
import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommodityViewController implements Controller {

    @Autowired
    @Qualifier("CommodityViewDAO_JDBC")
    private CommodityViewDAOImpl commodityViewDAO;

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) {

        List<CommodityView> content = new ArrayList<CommodityView>();
        try {
            content = commodityViewDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageConverter converter = new ImageConverter();
        /* TODO remove when image servlet will be ready
        for(CommodityView view : content){
            view.setBase64(converter.encodeToString(view.getCommodityPhoto()));
        }*/
        return (new Model("commodityView.jsp", null));
    }
}
