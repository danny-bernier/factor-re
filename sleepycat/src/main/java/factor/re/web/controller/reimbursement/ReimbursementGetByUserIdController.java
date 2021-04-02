package factor.re.web.controller.reimbursement;

import com.google.gson.Gson;
import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Daniel Bernier
 * Controller to handle get reimbursement by user id request
 */
public class ReimbursementGetByUserIdController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ReimbursementGetByUserIdController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public ReimbursementGetByUserIdController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather {@link Reimbursement Reimbursements} from {@link ReimbursementService} by User id, converts Reimbursements to JSON, and prints it to response body.
     * <p>
     *     (Wraps {@link #getById()} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            getById();

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Gather {@link Reimbursement Reimbursements} from {@link ReimbursementService} by User id, converts Reimbursements to JSON, and prints it to response body.
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void getById() throws IOException {
        try {

            //parsing id to integer and gathering all Reimbursements by id
            int userId = Integer.parseInt(req.getParameter("id"));
            List<Reimbursement> requestedReimbursements = new ReimbursementService().getReimbursementsByUserID(userId);

            //converting to JSON and appending to response
            Gson gson = new Gson();
            resp.getWriter().println(gson.toJson(requestedReimbursements));
            resp.setStatus(200);

            //if no proper id was provided
        } catch (NumberFormatException ignored) {
            LOGGER.debug("Failed parse id in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("null");
        }
    }
}
