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
import java.util.stream.Collectors;

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
        ADD,
        RESOLVE
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
                case RESOLVE:
                    requestResolve();
            }

            //closing response writer
            resp.getWriter().close();

        //general Exception
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

                resp.setStatus(200);

            //if no id was provided
            } else {
                badRequest();
            }

        //if id could not be parsed
        } catch (NumberFormatException ignored) {
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

        resp.setStatus(200);
    }


    /**
     * Adds new Reimbursement
     * <p>
     *     This constructs and adds a new Reimbursement based on provided JSON
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestAdd() throws IOException{

        //getting body JSON from request
        String body = req.getReader().lines().collect(Collectors.joining());

        //trying to create new Reimbursement
        if(new ReimbursementService().createReimbursement(body))
            resp.setStatus(201);
        else
            badRequest();

    }


    /**
     * Updates resolution information in a Reimbursement
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestResolve() throws IOException{
        try {

            //gathering required header information
            String id = req.getHeader("id");
            String status = req.getHeader("status");
            String resolver = req.getHeader("resolver");

            //if required headers are present and not empty
            if ((resolver != null && status != null && id != null)
                    && (!resolver.equals("") && !status.equals("") && !id.equals(""))) {

                //parsing headers to integers
                int reimbursementId = Integer.parseInt(id);
                int reimbursementStatus = Integer.parseInt(status);
                int reimbursementResolver = Integer.parseInt(resolver);

                //updating reimbursement with resolver
                if(new ReimbursementService().updateReimbursements(reimbursementId, reimbursementResolver, reimbursementStatus)){
                    resp.setStatus(204);
                } else {
                    badRequest();
                }

            //if required headers were not present/were empty
            } else {
                badRequest();
            }

        //if id, status, or resolver could not be parsed
        } catch (NumberFormatException ignored){
            badRequest();
        }
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
