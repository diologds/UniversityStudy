package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.database.ProducerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.domain.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Lilya on 05.03.14.
 */
@Component
public class EditProducerController implements Controller{
    @Autowired
    @Qualifier("ProducerDAO_JDBC")
    private ProducerDAO producerDAO;

    private List<Producer> producers =  null;

    private Long editId;

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        req.setAttribute("msg", " ");

        if (req.getParameter("save")!=null){
            saveChanges(req);
        }
        if (req.getParameter("edit")!=null){
            edit(req);
        }
        if (req.getParameter("add")!=null){
            addProducer(req);
        }
        if (req.getParameter("delete")!=null){
            deleteProducer(req);
        }
        producers = producerDAO.getAll();
        return new Model("editProducer.jsp", producers);
    }

    private void edit(HttpServletRequest req) {
        editId = Long.parseLong(req.getParameter("edit"));
    }

    private void saveChanges(HttpServletRequest req) {
        Producer producer = new Producer(editId, req.getParameter("editName"), req.getParameter("editDescription"));
        try {
            producerDAO.update(producer);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void deleteProducer(HttpServletRequest req) {
        Long paramIDProducer=Long.parseLong(req.getParameter("delete"));
            try {
                producerDAO.delete(paramIDProducer);
                } catch (DBException e) {
                    e.printStackTrace();
                }
    }

    protected void addProducer(HttpServletRequest req){
        String paramProducerName = req.getParameter("addName");
        String paramProducerDescription = req.getParameter("addDescription");
        if (!overlap(paramProducerName)){
            Producer producer = new Producer(paramProducerName, paramProducerDescription);
            try {
                producerDAO.create(producer);
                producers.add(producer);
                req.setAttribute("msg","New Producer is successfully saved into BD!");
            } catch (DBException e) {
                e.printStackTrace();
            }
        }else {
            req.setAttribute("msg","Entered Producer already exists!");
        }
    }

    private boolean overlap(String prodName){
        for (Producer producer: producers ){
            if (prodName.equals(producer.getName())) return true;
        }
        return false;
    }
}
