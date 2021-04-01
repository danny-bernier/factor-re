package factor.re.web.controller;

import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Front Controller for handling all incoming requests
 * @author Daniel Bernier
 */
public class FrontController extends AbstractController{

    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    /**
     * Enumeration of supported Requests
     */
    public enum Requests{
        USER_ALL,
        USER_USERNAME,
        USER_ID,
        USER_LOGIN,
        REIMBURSEMENT_ID,
        REIMBURSEMENT_ALL,
        REIMBURSEMENT_ADD,
        REIMBURSEMENT_RESOLVE
    }


    /**
     * Constructs a new FrontController object
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param context ServletContext
     */
    public FrontController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Processes String equivalent of specified request
     * <P>
     *     Supported Requests Enumeration:
     *     {@link factor.re.web.controller.FrontController.Requests}
     * </P>
     * @param request The request to be processed
     */
    @Override
    public void process(String request) {

        //converts string to request enum and calls overridden process
        process(FrontController.Requests.valueOf(request.toUpperCase()));
    }


    /**
     * Processes specified request
     * <P>
     *     Supported Requests Enumeration:
     *     {@link factor.re.web.controller.FrontController.Requests}
     * </P>
     * @param request The request to be processed
     */
    public void process(Requests request) {
        try {

            //choosing request operation
            switch (request) {
                case USER_ALL:
                    new UserController(req, resp, context).process(UserController.Requests.ALL);
                    break;
                case USER_USERNAME:
                    new UserController(req, resp, context).process(UserController.Requests.USERNAME);
                    break;
                case USER_ID:
                    new UserController(req, resp, context).process(UserController.Requests.ID);
                    break;
                case USER_LOGIN:
                    new UserController(req, resp, context).process(UserController.Requests.LOGIN);
                    break;
                case REIMBURSEMENT_ALL:
                    new ReimbursementController(req, resp, context).process(ReimbursementController.Requests.ALL);
                    break;
                case REIMBURSEMENT_ID:
                    new ReimbursementController(req, resp, context).process(ReimbursementController.Requests.ID);
                    break;
                case REIMBURSEMENT_ADD:
                    new ReimbursementController(req, resp, context).process(ReimbursementController.Requests.ADD);
                    break;
                case REIMBURSEMENT_RESOLVE:
                    new ReimbursementController(req, resp, context).process(ReimbursementController.Requests.RESOLVE);
                    break;
            }

        //general Exception
        } catch (Exception e){
            LOGGER.error("An exception was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
