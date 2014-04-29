package lv.javaguru.java2.ishop.database.orm;

import lv.javaguru.java2.ishop.database.*;
import lv.javaguru.java2.ishop.domain.Comment;
import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by Lilya on 19.03.14.
 */

@Component
public class CommentDAOImplTest extends SpringContextTest{
    @Autowired
    @Qualifier("CommentDAO_ORM")
    private CommentDAO commentDAO;

    @Autowired
    @Qualifier("CommodityDAO_ORM")
    private CommodityDAO commodityDAO;

    @Autowired
    @Qualifier("CustomerDAO_ORM")
    private CustomerDAO customerDAO;

    private Comment comment = new Comment();
    private Commodity commodity = new Commodity();
    private Customer customer = new Customer();
    private boolean isLoggedIn = true;


    private  void createCustomerAndCommodity() throws DBException {
        customer = customerDAO.getByPersonalCode("040397-10203");
        commodity = commodityDAO.getByName("18-55IS");
    }

    private  void createComment(){
        String comm = "My test comment on product";
        comment = new Comment(commodity, customer, comm, new Date(), isLoggedIn);
    }

    private  void fillDbTables() throws Exception {
        commentDAO.create(comment);
    }

    @Before
    public void setUp() throws Exception {
        createCustomerAndCommodity();
        createComment();
        fillDbTables();
    }

    @Test
    @Transactional
    public void testCreate() throws DBException {
        Comment c = commentDAO.getById(comment.getId());

        assertEquals(comment.getName(), c.getName());
        assertEquals(comment.getDate(), c.getDate());
        assertEquals(comment.getComment(), c.getComment());

        assertNotNull(c.getCommodity());
        assertEquals(c.getCommodity(), commodity);
        assertNotNull(c.getCustomer());
        assertEquals(c.getCustomer(), customer);
    }

    @Test
    @Transactional
    public void testGetById() throws DBException {
        Comment comment = new Comment(commodityDAO.getByName("32'' TV"), customerDAO.getByPersonalCode("040397-10203"), "comment", new Date(), isLoggedIn);
        commentDAO.create(comment);
        Comment commentFromDB = commentDAO.getById(comment.getId());
        assertNotNull(commentFromDB);
        Assert.assertTrue(comment == commentFromDB);
    }

    @Test
    @Transactional
    public void testGetByCommodity() throws Exception {
        Comment comment = new Comment(commodityDAO.getByName("18-55IS"), customerDAO.getByPersonalCode("040397-10203"), "comment", new Date(), isLoggedIn);
        commentDAO.create(comment);
        List<Comment> commentsFromDB = commentDAO.getByCommodity(comment.getCommodity().getId());
        assertFalse(commentsFromDB.isEmpty());
        for (Comment c: commentsFromDB){
            assertEquals(c.getCommodity().getId(), comment.getCommodity().getId());
        }
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        commentDAO.create(comment);
        String newComment = "New comment";
        comment.setComment(newComment);
        commentDAO.update(comment);
        Comment comment1=commentDAO.getById(comment.getId());
        assertEquals(comment1.getComment(), newComment);

    }
}
