package factor.re.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;

import factor.re.dao.UserDao;
import factor.re.model.User;

/**
 * @author Lok Kan Kung
 * UserService to call methods from the UserDao
 */
public class UserService {
	private UserDao ud;
	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	public UserService() {
		ud = new UserDao();
	}

	/**
	 * Get a list of all users in the database
	 * <p>
	 *     This call the getList() from UserDao
	 * </p>
	 * @return a list of users
	 * {@Link UserGetAllController#handle()} request a list of users and fetch from {@Link UserDao#getList()}
	 */
	public List<User> fetchAllUsers() {
		return ud.getList();
	}

	/**
	 * Get a single user with user id in the database
	 * <p>
	 *     This call the getById(int) from UserDao
	 * </p>
	 * @return a single user with that id
	 * @param id
	 * {@Link UserGetByIdController#handle()} request a user with that id and fetch from {@Link UserDao#getById(int)}
	 */
	public User getUserById(int id) {
		return ud.getById(id);
	}

	/**
	 * Get a single user with username in the database
	 * <p>
	 *     This call the getByUsername(String) from UserDao
	 * </p>
	 * @return a single user with that username
	 * @param username
	 * {@Link UserGetByUsernameController#handle()} request a user with that username and fetch from {@Link UserDao#getByUsername(String)}
	 */
	public User getUserByUsername(String username) {
		User u = ud.getByUsername(username);
		if (u != null) {
			u.setPassword(""); //Remove the hashed password for security reasons.
			LOGGER.trace("Password info removed from username " + username + ".");
			return u;
		}
		return null;
	}

	public User getUserByLogin(String user, String pass) {
		User u = ud.getByUsername(user);

		if(u != null) {
		String full = user + pass + "salt";
			try {
				//Let MessageDigest know that we want to hash using MD5
				MessageDigest m = MessageDigest.getInstance("md5");
				//Convert our full string to a byte array.
				byte[] messageDigest = m.digest(full.getBytes());
				//Convert our byte array into a signum representation of its former self.
				BigInteger n = new BigInteger(1, messageDigest);

				//Convert the whole array into a hexadecimal string.
				String hash = n.toString(16);
				while(hash.length() < 32) {
					hash = "0" + hash;
				}

				if(u.getPassword().equals(hash)) {
					//System.out.println("Hash matched!");
					return u;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Insert a single user into the database
	 * <p>
	 *     This call the insert(User) from UserDao
	 * </p>
	 * @param u
	 * {@Link UserCreateController#handle()} covert the user created from {@Link UserDao#insert(User) to JSON}
	 */
	public void insert(User u){
		ud.insert (u);
	}

	/**
	 * Delete a single user from the database
	 * <p>
	 *     This call the delete(User) from UserDao
	 * </p>
	 * @param u
	 * {@Link UserDeleteController#handle()} delete a user with {@Link UserDao#delete(User)}
	 */
	public void delete(User u){
		ud.delete (u);
	}
}
