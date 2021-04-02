package factor.re.web.controller.user;

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
 * Controller to handle delete user request
 */
public class UserDeleteController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserDeleteController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req {@link HttpServletRequest Request} associated with HTTP request
     * @param resp {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public UserDeleteController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather a {@link User} from {@link UserService} by user's id and sends it to {@link UserService} to be deleted.
     * <p>
     *     (Wraps {@link #deleteUserById()} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            deleteUserById();

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Gather a {@link User} from {@link UserService}, converts it to JSON, and prints it to response body.
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void deleteUserById() throws IOException {
        try {

            //parse id, delete user
            int id = Integer.parseInt(req.getParameter("id"));
            UserService us = new UserService();
            us.delete(us.getUserById(id));
            resp.setStatus(200);

            //if id could not be parsed to int
        } catch (NumberFormatException e) {
            LOGGER.debug("Failed parse id in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("<p>id could not be understood</p>");
        }
    }
}
