package factor.re.web.controller.reimbursement;

import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author Daniel Bernier
 * Controller to handle create reimbursement request
 */
public class ReimbursementCreateController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ReimbursementCreateController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public ReimbursementCreateController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Creates new {@link Reimbursement} using JSON from request body and {@link ReimbursementService#createReimbursement(String)}.
     */
    @Override
    public void handle() {
        try {

            //getting body JSON from request
            String body = req.getReader().lines().collect(Collectors.joining());

            //trying to create new Reimbursement
            if (new ReimbursementService().createReimbursement(body)) {
                resp.setStatus(201);
            } else {
                LOGGER.debug("Failed to add a new Reimbursement in " + this.getClass().getSimpleName());
                resp.setStatus(400);
                resp.getWriter().println("<h1>Could not create Reimbursement, ensure your JSON is formatted correctly example:</h1>");
                resp.getWriter().println("<p>{amount=123.00, description=\"this is a description\", author=5, resolver=0, status_id=0, type_id=1}</p>");
            }

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e){
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
