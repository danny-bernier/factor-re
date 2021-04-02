package factor.re.dao;

import java.util.ArrayList;
import java.util.List;
import factor.re.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/**
 * @author Lok Kan Kung
 * UserDao with methods to manage/access User table in database
 * Purpose of this Dao is to send/retrieve info about a reimbursement
 * to/from the database. It then returns the composed Reimbursement Object.
 */
public class UserDao implements GenericDao <User> {
	private static final Logger LOGGER = Logger.getLogger(UserDao.class);
  
    /**
     * Get a list of all users in the database
     * <p>
     *     This utilize the session.createQuery with HQL and pull from User
     * </p>
     * @return a list of users
     * {@Link UserService#fetchAllUsers()}
     */
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
  
    /**
     * Get a single user with user id in the database
     * <p>
     *     This utilize the session.createQuery with HQL and pull from User with the exact id
     * </p>
     * @return a single user with that id
     * @param id {@Link UserService#getUserById(int)}
     */
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

    /**
     * Get a single user with username in the database
     * <p>
     *     This utilize the session.createQuery with HQL and pull from User with the exact username
     * </p>
     * @return a single user with that username
     * @param username {@Link UserService#getUserByUsername(String),#getUserByLogin(String,String)}
     */
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

    /**
     * Insert a single user into the database
     * <p>
     *     This utilize the session.persist to insert the user into the database
     * </p>
     * @param t {@Link UserService#insert(User)}
     */
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
  
    /**
     * Delete a single user from the database
     * <p>
     *     This utilize the session.delete to delete the user from the database
     * </p>
     * @param t {@Link UserService#delete(User)}
     */
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
