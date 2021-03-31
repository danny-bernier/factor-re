package factor.re.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReimbursementServlet extends HttpServlet {

    //get all reimbursements
    //get reimbursement by userID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getHeader("id") != null){
            String userId = req.getHeader("id");
            //todo return json of get reimbursement by id
        }
        //todo return json of list of all reimbursements
    }

    //create new reimbursement
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo convert json to reimbursement
        //todo add reimbursement to database
    }
}
