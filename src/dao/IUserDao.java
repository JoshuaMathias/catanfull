package dao;

import java.util.List;

import server.User;

public interface IUserDao {
	
	public void addUser(User user);
	
	public List<User> getAllUsers();
	
}
