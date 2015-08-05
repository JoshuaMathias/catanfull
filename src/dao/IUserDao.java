package dao;

import java.util.List;

import server.User;

/**
 * Interface to the User Data Access Objects
 * @author Ife's Group
 *
 */
public interface IUserDao {
	
	/**
	 * Adds user to storage
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * retrieves all users from storage
	 * @return List of users
	 */
	public List<User> getAllUsers();
	
}
