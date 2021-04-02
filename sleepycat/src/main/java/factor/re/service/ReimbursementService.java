package factor.re.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	 * <p>
	 *     This call the insert(Reimbursement) into ReimbursementDao
	 * </p>
	 * @param json
	 * {@Link ReimbursementCreateController#handle()} create a reimbursement from JSON with {@Link ReimbursementDao#delete(Reimbursement) to JSON}
	 */
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

	/**
	 * Get a list of all reimbursements in the database
	 * <p>
	 *     This call the getList() from ReimbursementDao
	 * </p>
	 * @return a list of reimbursements
	 * {@Link ReimbursementGetAllController#handle()} request a list of reimbursements and fetch from {@Link ReimbursementDao#getList()}
	 */
	public List<Reimbursement> fetchAllReimbursements() {
		return rd.getList();
	}

	/**
	 * Delete a single reimbursement from the database
	 * <p>
	 *     This call the delete(Reimbursement) from ReimbursementDao
	 * </p>
	 * @param r
	 * {@Link ReimbursementDeleteController#handle()} delete a reimbursement with {@Link ReimbursementDao#delete(Reimbursement)}
	 */
	public void deleteReimbursement(Reimbursement r){
		rd.delete (r);
	}

	/**
	 * Get a list of reimbursements with userId in the database
	 * <p>
	 *     This call the getByUserId(int) from ReimbursementDao
	 * </p>
	 * @return a list of reimbursements with that userId
	 * @param id
	 * {@Link ReimbursementGetByUserIdController#handle()} request a list of reimbursements with that userId and fetch from {@Link ReimbursementDao#getByUserId(int)}
	 */
	public List<Reimbursement> getReimbursementsByUserID(int id) {
		return rd.getByUserId(id);
	}

	/**
	 * Make an update to a reimbursement in the database
	 * <p>
	 *     This call the update(int,int,int) from the reimbursementDao
	 * </p>
	 * @param reimb_id,resolver_id,status_id
	 * {@Link ReimbursementUpdateController#handle()} issue and update on a reimbursement and is done in{@Link ReimbursementService#updateReimbursement(int,int,int)}
	 */
	public void updateReimbursement(int reimb_id, int resolver_id, int status_id) {
		rd.update(reimb_id,resolver_id,status_id);
	}

	/**
	 * Insert a single reimbursement into the database
	 * <p>
	 *     This call the insert(Reimbursement) from ReimbursementDao
	 * </p>
	 * @param r
	 * {@Link ReimbursementCreateController#handle()} convert the reimbursement created from {@Link ReimbursementDao#delete(Reimbursement) to JSON}
	 */
	public void insertReimbursement(Reimbursement r){
		rd.insert (r);
	}

	/**
	 * Get a single reimbursement with id in the database
	 * <p>
	 *     This call the getById(int) from ReimbursementDao
	 * </p>
	 * @return a single reimbursement with that id
	 * @param id {@Link ReimbursementByIdController#handle() request a reimbursement and is done in}{@Link ReimbursementService#getReimbursementByID(int)}
	 */
	public Reimbursement getReimbursementByID(int id) {
		return rd.getById (id);
	}
}
