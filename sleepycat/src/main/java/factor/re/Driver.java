package factor.re;

import factor.re.model.Reimbursement;
import factor.re.model.User;
import factor.re.service.ReimbursementService;
import factor.re.service.UserService;

import java.util.List;

public class Driver {
    public static void main(String[] args) {
        ReimbursementService reimbService = new ReimbursementService ();
        UserService us = new UserService ();

        User user = us.getUserById (6);
        List<User> result = us.fetchAllUsers ();
        for(User users: result) {
            System.out.println (users);
        }
        System.out.println();

        us.delete (user);

        System.out.println();
        List<User> results = us.fetchAllUsers ();
        for(User users: results) {
            System.out.println (users);
        }
    }
}
