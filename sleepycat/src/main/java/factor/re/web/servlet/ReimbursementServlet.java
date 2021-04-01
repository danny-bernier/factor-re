package factor.re.web.servlet;

import factor.re.web.controller.FrontController;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Bernier
 * Servlet to handle {@link factor.re.model.Reimbursement Reimbursement} related requests
 */
public class ReimbursementServlet extends HttpServlet {

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
                    .process(FrontController.Requests.REIMBURSEMENT_ID);

        } else {
            new FrontController(req, resp, this.getServletContext())
                    .process(FrontController.Requests.REIMBURSEMENT_ALL);
        }
    }


    /**
     * Processes a POST request
     * <p>
     *     Calls Front Controller with appropriate request
     * </p>
     * @param req HttpServletRequest associated with POST
     * @param resp HttpServletResponse associated with POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //todo convert json to reimbursement
        //todo add reimbursement to database
    }
}
