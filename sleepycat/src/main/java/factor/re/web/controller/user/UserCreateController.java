package factor.re.web.controller.user;

import factor.re.service.ReimbursementService;
import factor.re.service.UserService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author Daniel Bernier
 * Controller to handle create new user request
 */
public class UserCreateController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserCreateController.class);

    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public UserCreateController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }

    /**
     * Gets request body and calls {@link UserService#insert(String)} to create user from json
     */
    @Override
    public void handle() {
        try {

            //getting body JSON from request
            String body = req.getReader().lines().collect(Collectors.joining());

            //trying to create new Reimbursement
            if (new UserService().insert(body)) {
                resp.setStatus(201);
            } else {
                LOGGER.debug("Failed to add a new user " + this.getClass().getSimpleName());
                resp.setStatus(400);
                resp.getWriter().println("<h1>Could not create User, ensure your JSON is formatted correctly ie:</h1>");
                resp.getWriter().println("<p>{username=\"\", password=\"\", firstname=\"\", lastname=\"\", email=\"\", role=1}</p>");
            }

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e){
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
