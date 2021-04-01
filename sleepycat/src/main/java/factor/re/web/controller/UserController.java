package factor.re.web.controller;

import com.google.gson.Gson;
import factor.re.model.User;
import factor.re.service.UserService;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Daniel Bernier
 * Controller for handling {@link factor.re.model.User User} related requests
 */
public class UserController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    /**
     * Enumeration of supported Requests
     */
    public enum Requests {
        ALL,
        USERNAME,
        ID,
        LOGIN
    }


    /**
     * Constructs a new UserController object
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param context ServletContext
     */
    public UserController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Processes String equivalent of specified request
     * <P>
     *     Supported Requests Enumeration:
     *     {@link factor.re.web.controller.UserController.Requests}
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
     *     {@link factor.re.web.controller.UserController.Requests}
     * </P>
     * @param request The request to be processed
     */
    public void process(Requests request) {
        try {

            //setting up response
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            //choosing request operation
            switch (request) {
                case ALL:
                    requestAll();
                    break;
                case USERNAME:
                    requestByUsername();
                    break;
                case ID:
                    requestByID();
                    break;
                case LOGIN:
                    requestByLogin();
                    break;
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
     * Requests all users
     * <p>
     *     This gathers all users, converts users one by one to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestAll() throws IOException {

        //getting list of all users
        List<User> users = new UserService().fetchAllUsers();

        //adding users as JSON to response
        Gson gson = new Gson();
        for (User u : users) {
            String userAsJSON = gson.toJson(u);
            resp.getWriter().println(userAsJSON);
        }
    }


    /**
     * Requests user by username
     * <p>
     *     This gathers user by username, converts user to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestByUsername() throws IOException {

        //get username from request headers
        String username = req.getHeader("username");

        //if username is present, add user as JSON to response body
        if (username != null && !username.equals("")) {
            User requestedUser = new UserService().getUserByUsername(username);
            String userAsJSON = new Gson().toJson(requestedUser);
            resp.getWriter().println(userAsJSON);

        //if no username provided
        } else {
            badRequest();
        }
    }


    /**
     * Requests user by ID
     * <p>
     *     This gathers user by ID, converts user to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestByID() throws IOException {

        try {
            //if id is present in request headers
            if (req.getHeader("id") != null) {

                //parse id, add user as JSON to response body
                int id = Integer.parseInt(req.getHeader("id"));
                User requestedUser = new UserService().getUserById(id);
                String userAsJSON = new Gson().toJson(requestedUser);
                resp.getWriter().println(userAsJSON);
                resp.getWriter().close();

            //if no id was provided
            } else {
                badRequest();
            }

        //if id could not be parsed to int
        } catch (NumberFormatException e) {
            badRequest();
        }
    }


    /**
     * Requests user by login credentials
     * <p>
     *     This gathers user by login information, converts user to JSON, and prints that JSON to response body
     * </p>
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void requestByLogin() throws IOException {

        //get username and password from request headers
        String username = req.getHeader("username");
        String password = req.getHeader("password");

        //if username and password are present, add user as JSON to response body
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            User requestedUser = new UserService().getUserByLogin(username, password);
            String userAsJSON = new Gson().toJson(requestedUser);
            resp.getWriter().println(userAsJSON);

        //if no username and/or password was provided
        } else {
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
