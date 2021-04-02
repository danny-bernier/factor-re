package factor.re.web.servlet;

import factor.re.web.controller.reimbursement.*;
import factor.re.web.controller.user.*;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Bernier
 * Front handler to dispatch all incoming requests
 */
public class HandlerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(HandlerServlet.class);


    /**
     * Handles all HTTP GET requests to this servlet
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        switch (req.getRequestURI()){
            case "/reimbursement/all":
                new ReimbursementGetAllController(req, resp, this.getServletContext()).handle();
                break;
            case "/reimbursement/userid":
                new ReimbursementGetByUserIdController(req, resp, this.getServletContext()).handle();
                break;
            case "/reimbursement/delete":
                new ReimbursementDeleteController(req, resp, this.getServletContext()).handle();
                break;
            case "/reimbursement/id":
                new ReimbursementGetByIdController(req, resp, this.getServletContext()).handle();
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
            case "/user/delete":
                new UserDeleteController(req, resp, this.getServletContext()).handle();
                break;
            default:
                noSuitableController(req, resp);
                break;
        }
    }


    /**
     * Handles all HTTP POST requests to this servlet
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
            case "/user/login":
                new UserGetByLoginController(req, resp, this.getServletContext()).handle();
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
