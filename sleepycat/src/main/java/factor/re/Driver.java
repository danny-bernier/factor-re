package factor.re;

import factor.re.model.Reimbursement;
import factor.re.service.ReimbursementService;

import java.util.List;

public class Driver {
    public static void main(String[] args) {
        ReimbursementService reimbService = new ReimbursementService ();
        List<Reimbursement> result = reimbService.fetchAllReimbursements();
        for(Reimbursement res: result){
            System.out.println (res);
        }

//        reimbService.updateReimbursement(1,4,2);
        Reimbursement re = reimbService.getReimbursementByID (1);
        System.out.println (re);

        System.out.println ("TESTING GET BY USER ID");
        List<Reimbursement> result2 = reimbService.getReimbursementsByUserID(1);
        for(Reimbursement res: result2){
            System.out.println (res);
        }
    }
}
