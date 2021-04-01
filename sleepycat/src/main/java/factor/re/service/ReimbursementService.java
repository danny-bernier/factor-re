package factor.re.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import factor.re.dao.ReimbursementDao;
import factor.re.model.Reimbursement;

public class ReimbursementService {
	private ReimbursementDao rd;
	private static final Logger LOGGER = Logger.getLogger(ReimbursementService.class);
	
	public ReimbursementService() {
		rd = new ReimbursementDao();
	}
	
	public void createReimbursement(String json) {
		try {
			Reimbursement r = new ObjectMapper().readValue(json, Reimbursement.class);
			LOGGER.debug("JSON from the client was successfully parsed.");
			rd.insert(r);
		} catch (Exception e) {
			LOGGER.error("Something occurred during JSON parsing for a new reimbursement. Is the JSON malformed?");
			e.printStackTrace();
		}
	}
	
	public List<Reimbursement> fetchAllReimbursements() {
		return rd.getList();
	}

	public void deleteReimbursement(Reimbursement r){
		rd.delete (r);
	}
	
	public List<Reimbursement> getReimbursementsByUserID(int id) {
		return rd.getByUserId(id);
	}
	
	public void updateReimbursement(int reimb_id, int resolver_id, int status_id) {
		rd.update(reimb_id,resolver_id,status_id);
	}

	public void insertReimbursement(Reimbursement r){
		rd.insert (r);
	}

	public Reimbursement getReimbursementByID(int id) {
		return rd.getById (id);
	}
}
