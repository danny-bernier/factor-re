package factor.re.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Bernier
 * Abstract controller to define basic outline of all other Controllers
 * <p>
 *     Extended by {@link FrontController}, {@link UserController}, {@link ReimbursementController}
 * </p>
 */
public abstract class AbstractController {

    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    /**
     * Simple constructor initializes context, request, and response
     * @param req HttpServletRequest associated with HTTP request
     * @param resp HttpServletResponse associated with HTTP request
     * @param context ServletContext associated with HTTP request
     */
    protected AbstractController(HttpServletRequest req, HttpServletResponse resp, ServletContext context){
        this.context = context;
        this.req = req;
        this.resp = resp;
    }

    /**
     * Process the specified request
     * @param request The request to be processed
     */
    public abstract void process(String request);
}
