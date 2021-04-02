package factor.re.web.controller.user;

import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     *
     */
    @Override
    public void handle() {
        //todo
    }
}
