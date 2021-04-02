package factor.re.web.controller.user;

import com.google.gson.Gson;
import factor.re.model.User;
import factor.re.service.UserService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Daniel Bernier
 * Controller to handle get all users request
 */
public class UserGetAllController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserGetAllController.class);


    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public UserGetAllController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather all {@link User Users} from {@link UserService}, converts Users to JSON, and prints to response body.
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            //getting list of all users
            List<User> users = new UserService().fetchAllUsers();

            //adding users as JSON to response
            Gson gson = new Gson();
            resp.getWriter().println(gson.toJson(users));

            resp.setStatus(200);

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e){
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
