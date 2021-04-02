package factor.re.web.controller.reimbursement;

import com.google.gson.Gson;
import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Daniel Bernier
 * Controller to handle get all reimbursements request
 */
public class ReimbursementGetAllController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ReimbursementGetAllController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public ReimbursementGetAllController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather all {@link Reimbursement Reimbursements} from {@link ReimbursementService}, converts Reimbursements to JSON, and prints to response body.
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            //gathering all Reimbursements
            List<Reimbursement> requestedReimbursements = new ReimbursementService().fetchAllReimbursements();

            //converting to JSON and appending to response
            Gson gson = new Gson();
            for (Reimbursement r : requestedReimbursements) {
                String reimbursementAsJSON = gson.toJson(r);
                resp.getWriter().println(reimbursementAsJSON);
            }

            resp.setStatus(200);

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
