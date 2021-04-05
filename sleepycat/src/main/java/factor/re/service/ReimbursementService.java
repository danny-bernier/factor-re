package factor.re.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import factor.re.dao.ReimbursementDao;
import factor.re.model.Reimbursement;

/**
 * @author Lok Kan Kung
 * ReimbursementService to call methods from the ReimbursementDao
 */
public class ReimbursementService {
	private ReimbursementDao rd;
	private static final Logger LOGGER = Logger.getLogger(ReimbursementService.class);
	
	public ReimbursementService() {
		rd = new ReimbursementDao();
	}

	/**
	 * Insert a single reimbursement into the database
	 *
	 * @param json The JSON representation of the reimbursement to be created
	 */
	public boolean createReimbursement(String json) {
		try {
			Reimbursement r = new Gson().fromJson(json, Reimbursement.class);
			LOGGER.debug("JSON from the client was successfully parsed.");
			r.setSubmitted(Timestamp.from(Instant.now()));
			System.out.println(r.getResolved() + "" + "" + r.getSubmitted());
			rd.insert(r);
			return true;
		} catch (Exception e) {
			LOGGER.error("Something occurred during JSON parsing for a new reimbursement. Is the JSON malformed?", e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get a list of all reimbursements in the database
	 *
	 * @return The list of all reimbursements
	 */
	public List<Reimbursement> fetchAllReimbursements() {
		return rd.getList();
	}

	/**
	 * Delete a single reimbursement from the database
	 *
	 * @param r The reimbursement to be deleted
	 */
	public void deleteReimbursement(Reimbursement r){
		rd.delete (r);
	}

	/**
	 * Get a list of reimbursements with userId in the database
	 *
	 * @return The list of reimbursements with that userId
	 * @param id The user id associated with reimbursements to be retrieved
	 */
	public List<Reimbursement> getReimbursementsByUserID(int id) {
		return rd.getByUserId(id);
	}

	/**
	 * Make an update to a reimbursement in the database
	 *
	 * @param reimbId The id of the reimbursement to be updated
	 * @param resolverId The user id of the resolver of the reimbursement
	 * @param statusId The new status of the reimbursement
	 */
	public boolean updateReimbursement(int reimbId, int resolverId, int statusId) {
		try {
			rd.update(reimbId,resolverId,statusId);
			return true;
		} catch (Exception ignored){
			return false;
		}
	}

	/**
	 * Insert a single reimbursement into the database
	 *
	 * @param r The reimbursement to be inserted
	 */
	public void insertReimbursement(Reimbursement r){
		rd.insert (r);
	}

	/**
	 * Get a single reimbursement with id in the database
	 *
	 * @return a single reimbursement with that id
	 * @param id The id of the reimbursement to be retrieved
	 */
	public Reimbursement getReimbursementByID(int id) {
		return rd.getById (id);
	}
}
