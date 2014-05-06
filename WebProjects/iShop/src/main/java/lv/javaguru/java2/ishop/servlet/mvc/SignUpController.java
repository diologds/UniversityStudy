package lv.javaguru.java2.ishop.servlet.mvc;


import lv.javaguru.java2.ishop.database.CategoryDAO;
import lv.javaguru.java2.ishop.database.CustomerDAO;
import lv.javaguru.java2.ishop.database.DBException;
import lv.javaguru.java2.ishop.database.jdbc.CustomerDAOImpl;
import lv.javaguru.java2.ishop.domain.Category;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.javaguru.java2.ishop.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Fatum on 3/6/14.
 */
@Component
public class SignUpController implements Controller {

    @Qualifier("CustomerDAO_ORM")
    @Autowired
    private CustomerDAO customerDAO;

    //private Customer customer;

    @Override
    public Model getModel(HttpServletRequest req, HttpServletResponse resp) throws DBException
    {
        req.setAttribute("msg", " ");

        if (req.getParameter("signup")!=null){
            System.out.println("Sign up attempt");

            //check email and confirm email
            String customerEmail = req.getParameter("customerEmail");
            String customerEmailConfirm = req.getParameter("customerEmailConfirm");
            if ( customerEmail.equals( customerEmailConfirm )  )
            {
                //System.out.println("Email equals");

                Pattern pattern =
                        Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");

                Matcher matcher =
                        pattern.matcher( customerEmail );

                //check if email is valid

                if ( matcher.matches() )
                {
                    //System.out.println("Email valid");



                    // check if email in use
                    CustomerDAOImpl customerDAO1 = new CustomerDAOImpl();
                    Customer customer = customerDAO1.getByEmail(customerEmail);
                    boolean emailExists = true;
                    try
                    {
                        customer.getEmail().equals(customerEmail);
                    }
                    catch (NullPointerException e)
                    {
                        emailExists = false;
                    }

                    if (emailExists)
                    {
                        //System.out.println("Email exists");
                        req.setAttribute("msg", "Email exists ");
                    }
                    else
                    {
                        //System.out.println("Email doesn't exist");

                        //check password and confirm password
                        String customerPassword = req.getParameter("customerPassword");
                        String customerPasswordConfirm = req.getParameter("customerPasswordConfirm");

                        if ( customerPassword.equals(customerPasswordConfirm) )
                        {
                            //System.out.println("Password match");

                            //check if password is valid
                            if (customerPassword.length() >5 )
                            {
                                //System.out.println("Password length OK");

                                //check if password has capitals
                                pattern =
                                        Pattern.compile(".*[A-Z].*");
                                matcher =
                                        pattern.matcher( customerPassword );
                                if ( matcher.matches() )
                                {
                                    //System.out.println("Password has uppercase characters");

                                    //check if password has non-capitals
                                    pattern =
                                            Pattern.compile(".*[a-z].*");
                                    matcher =
                                            pattern.matcher( customerPassword );
                                    if ( matcher.matches() )
                                    {
                                        // System.out.println("Password has lowercase characters");

                                        //check if password has numbers
                                        pattern =
                                                Pattern.compile(".*[0-9].*");
                                        matcher =
                                                pattern.matcher( customerPassword );
                                        if ( matcher.matches() )
                                        {
                                            //System.out.println("Password has digits");


                                            if (req.getParameter("customerName").length() > 0)
                                            {
                                                if (req.getParameter("customerSurname").length() > 0)
                                                {
                                                    if (req.getParameter("customerPersonalCode").length() > 0)
                                                    {
                                                        if (req.getParameter("customerPhone").length() > 0)
                                                        {
                                                            try
                                                            {
                                                                Customer newCustomer = new  Customer(customerEmail, "", req.getParameter("customerName"), req.getParameter("customerSurname"),
                                                                        "", req.getParameter("customerPhone"), customerPassword, req.getParameter("customerPersonalCode"),
                                                                        false, false, false );
                                                                customerDAO1.create(newCustomer);
                                                                req.setAttribute("msg","Registration complete");
                                                            }
                                                            catch (DBException e)
                                                            {
                                                                req.setAttribute("msg", "Database exception during customer registration");
                                                            }
                                                        }
                                                        else
                                                        {
                                                            //System.out.println("Customer phone number is empty");
                                                            req.setAttribute("msg", "Customer phone number is empty ");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        //System.out.println("Customer personal code is empty");
                                                        req.setAttribute("msg", "Customer personal code is empty ");
                                                    }
                                                }
                                                else
                                                {
                                                    //System.out.println("Customer surname is empty");
                                                    req.setAttribute("msg", "Customer surname is empty ");
                                                }
                                            }
                                            else
                                            {
                                                //System.out.println("Customer name is empty");
                                                req.setAttribute("msg", "Customer name is empty ");
                                            }
                                        }
                                        else
                                        {
                                            //System.out.println("Password should have digits");
                                            req.setAttribute("msg", "Password should have digits ");
                                        }
                                    }
                                    else
                                    {
                                        //System.out.println("Password should have lowercase characters");
                                        req.setAttribute("msg", "Password should have lowercase characters ");
                                    }
                                }
                                else
                                {
                                    //System.out.println("Password should have uppercase characters");
                                    req.setAttribute("msg", "Password should have uppercase characters ");
                                }

                            }
                            else
                            {
                                //System.out.println("Password should be longer than 5 characters");
                                req.setAttribute("msg", "Password should be longer than 5 characters ");
                            }
                        }
                        else
                        {
                            //System.out.println("Password doesn't match");
                            req.setAttribute("msg", "Password doesn't match ");
                        }
                    }
                }
                else
                {
                    //System.out.println("Email not valid");
                    req.setAttribute("msg", "Email not valid ");
                }
            }
            else
            {
                // System.out.println("Email not equals");
                req.setAttribute("msg", "Email not equals ");
            }
        }

        //customer =customerDAO.getById( (long) 1 );
        return new Model("signUp.jsp",new Object());

    }
}
