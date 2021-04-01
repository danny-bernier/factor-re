package factor.re.dao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import factor.re.model.Reimbursement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/*
 * Purpose of this Dao is to send/retrieve info about a reimbursement
 * to/from the database. It then returns the composed Reimbursement Object.
 */
public class ReimbursementDao implements GenericDao<Reimbursement> {
	private static final Logger LOGGER = Logger.getLogger(ReimbursementDao.class);
	
	private Reimbursement objectConstructor(ResultSet rs) throws SQLException {
		return new Reimbursement(rs.getInt(1), rs.getFloat(2), rs.getTimestamp(3), rs.getTimestamp(4),
							rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
	}

	@Override
	public List<Reimbursement> getList() {
		List<Reimbursement> result = new ArrayList<Reimbursement>();
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
		Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			result = session.createQuery("from Reimbursement",
					Reimbursement.class).list();

			LOGGER.debug ("All reimbursements were retrieved from the database.");
			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to get all reimbursements failed.",e);
			throw e;
		}
		return result;
	}

	public void update(int reimb_id, int resolver_id, int status_id){
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
			Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			Query query=session.createQuery("update Reimbursement set status_id=:status_id , resolver=:resolver , resolved=:resolved where id=:id");
			query.setParameter ("status_id",status_id);
			query.setParameter ("resolver",resolver_id);
			query.setParameter ("id",reimb_id);
			query.setParameter ("resolved", Timestamp.from (Instant.now ()));
			int result = query.executeUpdate();
			// TODO: Find a way to update the resolve time
			LOGGER.debug ("Update have been made to "+reimb_id);
			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to update failed.",e);
			throw e;
		}
	};

	@Override
	public Reimbursement getById(int id) {
		Reimbursement result = null;
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
			Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			result = session.createQuery("from Reimbursement where id=:id",
					Reimbursement.class)
					.setParameter ("id",id)
					.uniqueResult();

			LOGGER.debug ("Reimbursement with id "+id+" were retrieved from the database.");
			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to retrieve reimbursement failed.",e);
			throw e;
		}
		return result;
	}

	@Override
	public List<Reimbursement> getByUserId(int id) {
		List<Reimbursement> result = new ArrayList<Reimbursement>();
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
			Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			result = session.createQuery("from Reimbursement where author=:author",
					Reimbursement.class)
					.setParameter ("author",id).list();

			LOGGER.debug ("All reimbursements with the ID "+id+" were retrieved from the database.");
			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to get reimbursements failed.",e);
			throw e;
//			e.printStackTrace ();
		}
		return result;
	}

	@Override
	public Reimbursement getByUsername(String username) {
		throw new java.lang.UnsupportedOperationException("Not implemented");
	}

	@Override
	public void insert(Reimbursement reimbursement) {
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
			Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			session.persist (reimbursement);

			LOGGER.debug ("A new reimbursement was successfully added to the database.");

			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to insert a reimbursement to the database failed.",e);
			throw e;
//			e.printStackTrace ();
		}
	}

	@Override
	public void delete(Reimbursement reimbursement) {
		Transaction transaction = null;

		try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
			Session session = factory.openSession();){
			transaction = session.beginTransaction ();

			session.delete (reimbursement);

			LOGGER.debug ("Deleted a reimbursement from the database.");

			transaction.commit ();
		} catch (Exception e){
			LOGGER.error("An attempt to delete a reimbursement from the database failed.",e);
			throw e;
//			e.printStackTrace ();
		}
	}

}
