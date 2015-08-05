package dao;

import java.util.List;

import server.User;

/**
 * Interface to the User Data Access Objects
 * @author Ife's Group
 *
 */
public interface IUserDao {
	
	public void addUser(User user);
	
	public List<User> getAllUsers();
	
}
