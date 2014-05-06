package lv.javaguru.java2.ishop.servlet.mvc;

import lv.javaguru.java2.ishop.domain.Customer;
import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.servlet.forms.LoginForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Ann on 15/03/14.
 */
@Controller
@RequestMapping("signIn")
public class LoginControllerImpl  {

    private static Logger logger = Logger.getLogger(LoginControllerImpl.class.getName());

    @Qualifier("CustomerDAO_ORM")
    @Autowired
    private CustomerDAO customerDAO;


/*
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.REPEATABLE_READ)
*/

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        LoginForm loginForm = new LoginForm();
        HttpSession session = req.getSession();

        // boolean isPost = "POST".equals(req.getMethod());
        if (req.getParameter("logout") != null) {
            session.removeAttribute("user");
        }

        Customer user = (Customer) session.getAttribute("user");
        if (user != null) {
            loginForm.user = user;
        } else if (req.getParameter("btnLogin") != null) {
            loginForm.login = req.getParameter("login");
            loginForm.password = req.getParameter("password");


            Integer attempts = (Integer) session.getAttribute("attempts");
            String lastLogin = (String) session.getAttribute("lastLogin");
            if (attempts == null || !loginForm.login.equals(lastLogin)) {
                attempts = 0;
            }
            lastLogin = loginForm.login;

            if (loginForm.validate()) {
                attempts++;
                Customer customer = null;
                try
                {
                  customer = customerDAO.getByEmail(loginForm.login);
                }
                catch  (DBException e)
                {
                  logger.log(Level.SEVERE, "Exception while getting customer by email");
                }
                if (customer == null || !customer.getPassword().equals(loginForm.password)) {
                    loginForm.addError("form", "Wrong username or password");
                    session.setAttribute("attempts", attempts);
                    session.setAttribute("lastLogin", lastLogin);
                    if (attempts >= 3 && customer != null && !customer.isAccountLocked()) {
                        customer.setAccountLocked(true);
                        try
                        {
                        customerDAO.update(customer);
                        }
                        catch(DBException e)
                        {
                            logger.log(Level.SEVERE, "Exception while updating customer.accountLocked");
                        }
                    }
                }

                if (customer != null) {
                    if (customer.isAccountLocked()) {
                        loginForm.addError("form", "Account is locked");
                    } else if (!loginForm.hasErrors()) {
                        session.removeAttribute("attempts");
                        session.removeAttribute("lastLogin");
                        //login
                        session.setAttribute("user", customer);
                        loginForm.user = customer;
                    }
                }
                //loginForm.addError("attempts", "Attempt " + attempts + ", Last login " + lastLogin);
            }
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        model.addObject("model", loginForm);

        //Model model = new Model("/login.jsp", loginForm);
        return model;

    }
}
