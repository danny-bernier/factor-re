package factor.re.web.servlet;

import factor.re.web.controller.FrontController;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Bernier
 * Servlet to handle {@link factor.re.model.User User} related requests
 */
public class UserServlet extends HttpServlet {

    /**
     * Processes a GET request
     * <p>
     *     Calls Front Controller with appropriate request
     * </p>
     * @param req HttpServletRequest associated with GET
     * @param resp HttpServletResponse associated with GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        //Calling front controller with appropriate request dependant on header presence
        if(req.getHeader("id") != null){
            new FrontController(req, resp, this.getServletContext())
                    .process(FrontController.Requests.USER_ID);

        } else if(req.getHeader("password") != null){
            new FrontController(req, resp, this.getServletContext())
                    .process(FrontController.Requests.USER_LOGIN);

        } else if(req.getHeader("username") != null){
            new FrontController(req, resp, this.getServletContext())
                    .process(FrontController.Requests.USER_USERNAME);

        } else {
            new FrontController(req, resp, this.getServletContext())
                    .process(FrontController.Requests.USER_ALL);
        }
    }
}
