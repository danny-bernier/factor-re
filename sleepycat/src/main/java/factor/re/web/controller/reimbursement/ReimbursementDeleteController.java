package factor.re.web.controller.reimbursement;

import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Daniel Bernier
 * Controller to handle delete reimbursement request
 */
public class ReimbursementDeleteController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ReimbursementDeleteController.class);

    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public ReimbursementDeleteController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Deletes {@link Reimbursement} from using {@link ReimbursementService} by its id.
     * <p>
     *     (Wraps {@link #deleteById()} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            deleteById();

            //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Deletes {@link Reimbursement} from using {@link ReimbursementService} by its id.
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void deleteById() throws IOException {
        try {

            //parsing id to integer and gathering all Reimbursements by id
            int id = Integer.parseInt(req.getParameter("id"));
            ReimbursementService rs = new ReimbursementService();
            rs.deleteReimbursement(rs.getReimbursementByID(id));
            resp.setStatus(200);

        //if no proper id was provided
        } catch (NumberFormatException ignored) {
            LOGGER.debug("Failed parse id in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("<p>could not understand id</p>");
        }
    }
}
