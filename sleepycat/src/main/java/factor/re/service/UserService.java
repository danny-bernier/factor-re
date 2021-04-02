package factor.re.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.google.gson.Gson;
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
	 *
	 * @return The list of all users
	 */
	public List<User> fetchAllUsers() {
		return ud.getList();
	}

	/**
	 * Get a single user with user id in the database
	 *
	 * @return a single user with that id
	 * @param id The user id of the user to be retrieved
	 */
	public User getUserById(int id) {
		return ud.getById(id);
	}

	/**
	 * Get a single user with username in the database
	 *
	 * @return The user with that username
	 * @param username The username of the user to be retrieved
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

	/**
	 * Retrieves User from {@link UserDao#getByUsername(String)}, and confirms hashed passwords match
	 * <p>
	 *     If password hashes match, this will return the User. If password hashes do not match, this will return null.
	 * </p>
	 * @param user The username of the user to be retrieved
	 * @param pass The password of the user to be retrieved
	 * @return The retrieved user
	 */
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
	 *
	 * @param json The JSON representation of the user to be created
	 */
	public boolean insert(String json){
		try {
			User u = new Gson().fromJson(json, User.class);
			LOGGER.debug("JSON from the client was successfully parsed.");

			String username = u.getUsername();
			String password = u.getPassword();

			if(username != null && !username.equals("") && password != null && !password.equals("")) {
				String full = username + password + "salt";

				try {
					//Let MessageDigest know that we want to hash using MD5
					MessageDigest m = MessageDigest.getInstance("md5");
					//Convert our full string to a byte array.
					byte[] messageDigest = m.digest(full.getBytes());
					//Convert our byte array into a signum representation of its former self.
					BigInteger n = new BigInteger(1, messageDigest);

					//Convert the whole array into a hexadecimal string.
					String hash = n.toString(16);
					while (hash.length() < 32) {
						hash = "0" + hash;
					}

					u.setPassword(hash);

					ud.insert(u);
					return true;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			LOGGER.error("Something occurred during JSON parsing for a new reimbursement. Is the JSON malformed?", e);
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Delete a single user from the database
	 *
	 * @param u The user to be deleted
	 */
	public void delete(User u){
		ud.delete (u);
	}
}
