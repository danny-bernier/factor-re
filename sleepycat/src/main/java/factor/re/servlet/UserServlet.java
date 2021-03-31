package factor.re.servlet;

import com.google.gson.Gson;
import factor.re.model.User;
import factor.re.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class);

    //get all users if nothing provided
    //get user by username
    //get user by id
    //get user by login

    /**
     * Processes a Get request
     * <p>
     *     get all users if nothing provided,
     *     get user by username,
     *     get user by id,
     *     get user by login
     * </p>
     * @param req HttpServletRequest associated with Get
     * @param resp HttpServletResponse associated with Get
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        //setting up response
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {

            //requesting user by username & by username with password
            if (req.getHeader("username") != null) {
                String username = req.getHeader("username");

                //requesting user by username & password
                if (req.getHeader("password") != null) {
                    String password = req.getHeader("password");
                    User requestedUser = new UserService().getUserByLogin(username, password);
                    String userAsJSON = new Gson().toJson(requestedUser);
                    resp.getWriter().println(userAsJSON);

                //requesting user by username
                } else {
                    User requestedUser = new UserService().getUserByUsername(username);
                    String userAsJSON = new Gson().toJson(requestedUser);
                    resp.getWriter().println(userAsJSON);
                }

            //requesting user by id
            } else if (req.getHeader("id") != null) {
                int id = Integer.parseInt(req.getHeader("id"));
                User requestedUser = new UserService().getUserById(id);
                String userAsJSON = new Gson().toJson(requestedUser);
                resp.getWriter().println(userAsJSON);
                resp.getWriter().close();

            //if no username, password, or id header was found, requests all users
            } else {
                List<User> users = new UserService().fetchAllUsers();
                Gson gson = new Gson();
                for (User u:users) {
                    String userAsJSON = gson.toJson(u);
                    resp.getWriter().println(userAsJSON);
                }
            }

            resp.getWriter().close();

        //exception from parsing id to integer
        } catch (NumberFormatException e) {
            LOGGER.debug("Issue when trying to parse user provided header id to integer in UserService doGet()", e);
            resp.setStatus(400);
            req.getRequestDispatcher("/400.html");

        //general exception
        } catch (Exception e){
            LOGGER.error("An exception was thrown in UserService doGet()", e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }
}
