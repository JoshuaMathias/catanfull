package dao.other;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import server.User;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import dao.IUserDao;

/**
 * concrete implementation of the User DAO for using a File
 * 
 * @author Ife's Group
 * 
 */
public class OtherUserDao implements IUserDao {
	private String usersFileStr = "persistent" + File.separator + "Users"
			+ File.separator + "users.txt";
	Gson g;

	public OtherUserDao() {
		g = new Gson();
	}

	@Override
	public void addUser(User user) {
		// try(PrintWriter out = new PrintWriter(new BufferedWriter(new
		// FileWriter(usersFile, true)))) {
		// out.write(g.toJson(user));
		// }
		try {
			File usersFile = new File(usersFileStr);
			if (!usersFile.exists()) {
				Files.write(Paths.get(usersFileStr),
						(g.toJson(user) + "\r").getBytes(),
						StandardOpenOption.CREATE);
			} else {
				Files.write(Paths.get(usersFileStr),
						(g.toJson(user) + "\r").getBytes(),
						StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			System.out.println("Failed to write user to file.");
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers() {
		File usersFile = new File(usersFileStr);
		ArrayList<User> usersList = new ArrayList<User>();
		if (usersFile.exists()) {
			Scanner userScan;
			try {
				userScan = new Scanner(usersFile);
				while (userScan.hasNextLine()) {
					usersList.add(g.fromJson(userScan.nextLine(), User.class));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No users file found");
		}
		return usersList;
	}

}
