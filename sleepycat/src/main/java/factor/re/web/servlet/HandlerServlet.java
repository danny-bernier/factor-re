package factor.re.web.servlet;

import factor.re.web.controller.reimbursement.*;
import factor.re.web.controller.user.UserCreateController;
import factor.re.web.controller.user.UserGetAllController;
import factor.re.web.controller.user.UserGetByIdController;
import factor.re.web.controller.user.UserGetByUsernameController;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Daniel Bernier
 * Front handler to dispatch all incoming requests
 */
public class HandlerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(HandlerServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        switch (req.getRequestURI()){
            case "/reimbursement/all":
                new ReimbursementGetAllController(req, resp, this.getServletContext()).handle();
                break;
            case "/reimbursement/userid":
                new ReimbursementGetByUserIdController(req, resp, this.getServletContext()).handle();
                break;
            case "/user/all":
                new UserGetAllController(req, resp, this.getServletContext()).handle();
                break;
            case "/user/id":
                new UserGetByIdController(req, resp, this.getServletContext()).handle();
                break;
            case "/user/username":
                new UserGetByUsernameController(req, resp, this.getServletContext()).handle();
                break;
            default:
                noSuitableController(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getRequestURI()){
            case "/reimbursement/create":
                new ReimbursementCreateController(req, resp, this.getServletContext()).handle();
                break;
            case "/reimbursement/resolve":
                new ReimbursementResolveController(req, resp, this.getServletContext()).handle();
                break;
            case "/user/create":
                new UserCreateController(req, resp, this.getServletContext()).handle();
                break;
            default:
                noSuitableController(req, resp);
                break;
        }
    }

    /**
     * To be called when no controller could be found to handle request
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    private void noSuitableController(HttpServletRequest req, HttpServletResponse resp){
        try {
            resp.setStatus(400);
            resp.getWriter().println("<p>Could not handle your request. Did you use the wrong HTTP verb? (like GET instead of POST)</p>");
            resp.getWriter().close();

        //general exception
        } catch (Exception e){
            LOGGER.error("An exception was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }

    }
}
