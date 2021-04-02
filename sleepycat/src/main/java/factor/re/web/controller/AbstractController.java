package factor.re.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Bernier
 * Abstract controller to define basic outline of all other Controllers
 * <p>
 *     Controllers are intended to perform some operation based on HTTP request and mutate HTTP response
 * </p>
 */
public abstract class AbstractController {

    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    /**
     * Simple constructor initializes context, request, and response
     * @param req {@link HttpServletRequest Request} associated with HTTP request
     * @param resp {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    protected AbstractController(HttpServletRequest req, HttpServletResponse resp, ServletContext context){
        this.context = context;
        this.req = req;
        this.resp = resp;
    }

    /**
     * Handles the request, performing whatever operations that entails
     */
    public abstract void handle();
}
