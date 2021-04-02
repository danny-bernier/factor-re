package factor.re.service;

import java.sql.Timestamp;
import java.time.Instant;
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
	
	public boolean createReimbursement(String json) {
		try {
			Reimbursement r = new ObjectMapper().readValue(json, Reimbursement.class);
			LOGGER.debug("JSON from the client was successfully parsed.");
			r.setSubmitted(Timestamp.from(Instant.now()));
			rd.insert(r);
			return true;
		} catch (Exception e) {
			LOGGER.error("Something occurred during JSON parsing for a new reimbursement. Is the JSON malformed?");
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Reimbursement> fetchAllReimbursements() {
		return rd.getList();
	}
	
	public List<Reimbursement> getReimbursementsByUserID(int id) {
		return rd.getByUserId(id);
	}
	
	public boolean updateReimbursements(int reimb_id, int resolver_id, int status_id) {
		return true;
	}
}
