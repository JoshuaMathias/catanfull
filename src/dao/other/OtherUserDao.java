package dao.other;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import server.User;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import dao.IUserDao;

/**
 * concrete implementation of the User DAO for using a File
 * @author Ife's Group
 *
 */
public class OtherUserDao implements IUserDao {
	private String usersPath="persistent/Users";
	private String gamesPath="persistent/Games";
	private String commandsPath="persistent/Commands";
	private IServerFacade facade;
	
	public OtherUserDao() {
		facade = ServerFacade.getSingleton();
	}
	
	@Override
	public void addUser(User user) {
		File usersDir=new File(usersPath);
		if (usersDir.isDirectory()) {
			
		}
//		if (file.exists()) {
//			Scanner scan = null;
//			try {
//				scan = new Scanner(file);
//			} catch (FileNotFoundException e) {
//				System.out.println("File " + file.getName() + " not found");
//			}
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
