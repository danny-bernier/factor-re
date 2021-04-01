package factor.re.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import factor.re.model.Reimbursement;
import factor.re.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/*
 * Purpose of this Dao is to send/retrieve info about a reimbursement
 * to/from the database. It then returns the composed Reimbursement Object.
 */
public class UserDao implements GenericDao <User> {
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	private User objectConstructor(ResultSet rs) throws SQLException {
		return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));
	}

	@Override
	public List<User> getList() {
        List<User> result = new ArrayList<User>();
        Transaction transaction = null;

        try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
            Session session = factory.openSession();){
            transaction = session.beginTransaction ();

            result = session.createQuery("from User",
                    User.class).list();

            LOGGER.debug ("All Users were retrieved from the database.");
            transaction.commit ();
        } catch (Exception e){
            LOGGER.error("An attempt to get all users failed.",e);
            throw e;
        }
        return result;
	}

	@Override
	public User getById(int id) {
        User result = null;
        Transaction transaction = null;

        try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
            Session session = factory.openSession();){
            transaction = session.beginTransaction ();

            result = session.createQuery("from User where id=:id",
                    User.class)
                    .setParameter ("id",id)
                    .uniqueResult();

            LOGGER.debug ("User with id "+id+" were retrieved from the database.");
            transaction.commit ();
        } catch (Exception e){
            LOGGER.error("An attempt to retrieve User failed.",e);
            throw e;
        }
        return result;
	}

	@Override
	public List<User> getByUserId(int id) {
		throw new java.lang.UnsupportedOperationException("Not implemented");
	}

	@Override
	public User getByUsername(String username) {
        User result = null;
        Transaction transaction = null;

        try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
            Session session = factory.openSession();){
            transaction = session.beginTransaction ();

            result = session.createQuery("from User where username=:username",
                    User.class)
                    .setParameter ("username",username)
                    .uniqueResult();

            LOGGER.debug ("User with username: "+username+" were retrieved from the database.");
            transaction.commit ();
        } catch (Exception e){
            LOGGER.error("An attempt to retrieve User failed.",e);
            throw e;
        }
        return result;
	}

	@Override
	public void insert(User t) {
        Transaction transaction = null;

        try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
            Session session = factory.openSession();){
            transaction = session.beginTransaction ();

            session.persist (t);

            LOGGER.debug ("A new User was successfully added to the database.");

            transaction.commit ();

        } catch (Exception e){
            LOGGER.error("An attempt to insert a User to the database failed.",e);
            throw e;
//			e.printStackTrace ();
        }

	}

	@Override
	public void delete(User t) {
        Transaction transaction = null;

        try(SessionFactory factory = new Configuration ().configure().buildSessionFactory();
            Session session = factory.openSession();){
            transaction = session.beginTransaction ();

            session.delete (t);

            LOGGER.debug ("Deleted a User from the database.");

            transaction.commit ();
            System.out.println ("after delete commit");
        } catch (Exception e){
            LOGGER.error("An attempt to delete a User from the database failed.",e);
//            throw e;
			e.printStackTrace ();
        }

	}
}
