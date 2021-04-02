package factor.re.web.controller.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import factor.re.model.User;
import factor.re.service.UserService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

public class UserGetByLoginController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserGetByLoginController.class);

    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public UserGetByLoginController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Gather a {@link User} from {@link UserService} by user's username and password, converts User to JSON, and prints it to response body.
     * <p>
     *     (Wraps {@link #getByLogin()} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            getByLogin();

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }

    /**
     * Gather a {@link User} from {@link UserService} by user's username and password, converts User to JSON, and prints it to response body.
     */
    public void getByLogin() throws IOException {

        //getting body JSON from request
        String body = req.getReader().lines().collect(Collectors.joining());

        //gathering required header information
        Type mapJSONType = new TypeToken<Map<String, String>>() {}.getType();
        Gson gson = new Gson();
        Map<String, String> jsonMap = gson.fromJson(body, mapJSONType);

        //get user by username and password in jsonMap
        try {
            User requestedUser = new UserService().getUserByLogin(jsonMap.get("username"), jsonMap.get("password"));
            String userAsJSON = gson.toJson(requestedUser);
            resp.getWriter().println(userAsJSON);
            resp.setStatus(200);

        //if .getWriter throws exception
        } catch (IOException e){
            throw e;

        //if no user could be retrieved
        } catch (Exception ignored){
            LOGGER.debug("Failed to get user by login information in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("null");
        }
    }
}
