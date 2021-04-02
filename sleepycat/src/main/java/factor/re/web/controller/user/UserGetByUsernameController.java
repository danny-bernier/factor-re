package factor.re.web.controller.user;

import com.google.gson.Gson;
import factor.re.model.User;
import factor.re.service.UserService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Daniel Bernier
 * Controller to handle get user by username request
 */
public class UserGetByUsernameController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserGetByUsernameController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public UserGetByUsernameController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather a {@link User} from {@link UserService} by username, converts User to JSON, and prints it to response body.
     * <p>
     *     (Wraps {@link #getUserByUsername()} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            getUserByUsername();

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Gather a {@link User} from {@link UserService} by username, converts User to JSON, and prints it to response body.
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void getUserByUsername() throws IOException {

        //get username from request headers
        String username = req.getParameter("username");

        //if username is present, add user as JSON to response body
        if (username != null && !username.equals("")) {
            User requestedUser = new UserService().getUserByUsername(username);
            String userAsJSON = new Gson().toJson(requestedUser);
            resp.getWriter().println(userAsJSON);
            resp.setStatus(200);

        //if no username provided
        } else {
            LOGGER.debug("Failed to get user by username in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("null");
        }
    }
}
