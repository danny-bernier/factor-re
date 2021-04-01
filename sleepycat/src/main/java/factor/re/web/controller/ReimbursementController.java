package factor.re.web.controller;

import com.google.gson.Gson;
import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Daniel Bernier
 * Controller for handling {@link factor.re.model.Reimbursement Reimbursement} related requests
 */
public class ReimbursementController extends AbstractController{

    private static final Logger LOGGER = Logger.getLogger(ReimbursementController.class);

    /**
     * Enumeration of supported Requests
     */
    public enum Requests {
        ID,
        ALL,
        ADD
    }


    /**
     * Constructs a new ReimbursementController object
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param context ServletContext
     */
    public ReimbursementController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Processes String equivalent of specified request
     * <P>
     *     Supported Requests Enumeration:
     *     {@link factor.re.web.controller.ReimbursementController.Requests}
     * </P>
     * @param request The request to be processed
     */
    @Override
    public void process(String request) {

        //converts string to request enum and calls overridden process
        process(Requests.valueOf(request.toUpperCase()));
    }


    /**
     * Processes specified Request
     * <P>
     *     Supported Requests Enumeration:
     *     {@link factor.re.web.controller.ReimbursementController.Requests}
     * </P>
     * @param request The request to be processed
     */
    public void process(Requests request){
        try {

            //setting up response
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            //choosing request operation
            switch (request) {
                case ALL:
                    requestAll();
                    break;
                case ID:
                    requestByID();
                    break;
                case ADD:
                    requestAdd();
                    break;
            }

            //closing response writer
            resp.getWriter().close();

            //exception from failed parse of ID
        } catch (Exception e){
            LOGGER.error("An exception was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Requests all Reimbursements by ID
     * <p>
     *     This gathers Reimbursements matching the specified ID, converts user to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestByID() throws IOException {
        try {
            if (req.getHeader("id") != null && !req.getHeader("id").equals("")) {

                //parsing id to integer and gathering all Reimbursements by id
                int userId = Integer.parseInt(req.getHeader("id"));
                List<Reimbursement> requestedReimbursements = new ReimbursementService().getReimbursementsByUserID(userId);

                //converting to JSON and appending to response
                Gson gson = new Gson();
                for (Reimbursement r:requestedReimbursements) {
                    String reimbursementAsJSON = gson.toJson(r);
                    resp.getWriter().println(reimbursementAsJSON);
                }

            //if no id was provided
            } else {
                badRequest();
            }

        //if id could not be parsed
        } catch (NumberFormatException e) {
            badRequest();
        }
    }


    /**
     * Requests all Reimbursements
     * <p>
     *     This gathers Reimbursements, converts user to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestAll() throws IOException {

        //gathering all Reimbursements
        List<Reimbursement> requestedReimbursements = new ReimbursementService().fetchAllReimbursements();

        //converting to JSON and appending to response
        Gson gson = new Gson();
        for (Reimbursement r:requestedReimbursements) {
            String reimbursementAsJSON = gson.toJson(r);
            resp.getWriter().println(reimbursementAsJSON);
        }
    }


    /**
     * Adds new Reimbursement
     * <p>
     *     This constructs and adds a new Reimbursement based on provided JSON
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestAdd() throws IOException{
        //TODO implement
    }


    /**
     * Sets HTTP code 400 Bad Request populates body with null
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void badRequest() throws IOException {
        resp.setStatus(400);
        resp.getWriter().println("null");
    }
}
