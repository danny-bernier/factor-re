package factor.re.web.controller.reimbursement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;
import factor.re.web.controller.AbstractController;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel Bernier
 * Controller to handle resolve reimbursement request
 */
public class ReimbursementResolveController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(ReimbursementResolveController.class);

    /**
     * Simple constructor initializes context, request, and response
     *
     * @param req     {@link HttpServletRequest Request} associated with HTTP request
     * @param resp    {@link HttpServletResponse Response} associated with HTTP request
     * @param context {@link ServletContext Context} associated with HTTP request
     */
    public ReimbursementResolveController(HttpServletRequest req, HttpServletResponse resp, ServletContext context) {
        super(req, resp, context);
    }


    /**
     * Update {@link Reimbursement} resolution information using {@link ReimbursementService}.
     * <p>
     *     (Wraps {@link #resolve()}} to catch potential exceptions)
     * </p>
     */
    @Override
    public void handle() {
        try {
            resolve();

        //if general exception thrown log exception and redirect to error page
        } catch (Exception e) {
            LOGGER.error("An exception (" + e.getClass().getSimpleName() + ") was thrown in " + this.getClass().getSimpleName(), e);
            resp.setStatus(500);
            req.getRequestDispatcher("/error.html");
        }
    }


    /**
     * Update {@link Reimbursement} resolution information using {@link ReimbursementService}.
     * @throws IOException thrown by {@link HttpServletResponse#getWriter()}
     */
    private void resolve() throws IOException {
        try {

            //getting body JSON from request
            String body = req.getReader().lines().collect(Collectors.joining());

            //gathering required header information
            Type mapJSONType = new TypeToken<Map<String, String>>() {}.getType();
            Gson gson = new Gson();
            Map<String, String> jsonMap = gson.fromJson(body, mapJSONType);

            //parsing headers to integers
            int reimbursementId = Integer.parseInt(jsonMap.get("id"));
            int reimbursementStatus = Integer.parseInt(jsonMap.get("status"));
            int reimbursementResolver = Integer.parseInt(jsonMap.get("resolver"));

            //updating reimbursement with resolver
            if (new ReimbursementService().updateReimbursement(reimbursementId, reimbursementResolver, reimbursementStatus)) {
                resp.setStatus(204);

            //if failed to update resolver information
            } else {
                LOGGER.debug("Failed to update resolver in " + this.getClass().getSimpleName());
                resp.setStatus(400);
                resp.getWriter().println("null");
            }

        //if id, status, or resolver could not be parsed
        } catch (NumberFormatException ignored){
            LOGGER.debug("Failed parse id, status, or resolver in " + this.getClass().getSimpleName());
            resp.setStatus(400);
            resp.getWriter().println("<p>Could not resolve Reimbursement, ensure your JSON is formatted correctly</p>");
        }
    }
}
