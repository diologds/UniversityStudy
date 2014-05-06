package lv.javaguru.java2.ishop.servlet.mvc.business_model;

import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ann on 04/04/14.
 */
@Component
public class IshopDispatcher
{
    public void redirectTo(HttpServletResponse resp,
                           String url)
            throws IOException
    {

        resp.sendRedirect(url);
    }
    public void redirectToThisHandler(HttpServletRequest req,
                                    HttpServletResponse resp)
            throws IOException
    {
        String thisPage = req.getContextPath() + req.getServletPath();
        resp.sendRedirect(thisPage);
    }
    public void redirectToPageHandler(HttpServletResponse resp,
                                      String pageHandler)
            throws IOException
    {
        resp.sendRedirect(pageHandler);
    }
    public void forwardToTaskHandler(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     String taskHandler)
            throws IOException, ServletException
    {
        RequestDispatcher dispatcher = req.getRequestDispatcher(taskHandler);
        dispatcher.forward(req,resp);
    }
}
