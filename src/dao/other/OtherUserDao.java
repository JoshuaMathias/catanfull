package dao.other;

import java.io.BufferedWriter;
import java.io.File;
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
 * @author Ife's Group
 *
 */
public class OtherUserDao implements IUserDao {
	private String usersPath="persistent"+File.separator+"Users";
	private String gamesPath="persistent"+File.separator+"Games";
	private String commandsPath="persistent"+File.separator+"Commands";
	private String usersFile="persistent"+File.separator+"Users"+File.separator+"users.txt";
	private IServerFacade facade;
	Gson g;
	
	public OtherUserDao() {
		g = new Gson();
	}
	
	@Override
	public void addUser(User user) {
		File usersDir=new File(usersPath);
		if (usersDir.isDirectory()) {
//			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(usersFile, true)))) {
//				out.write(g.toJson(user));
//			}
			try {
				Files.write(Paths.get(usersFile), (g.toJson(user)+"\r").getBytes(), StandardOpenOption.APPEND);
				System.out.println("user: "+g.toJson(user));
				System.out.println("Wrote user "+user.getName()+" to file.");
			} catch (IOException e) {
				System.out.println("Failed to write user to file.");
				e.printStackTrace();
			}
		} else {
			System.out.println("Couldn't find directory "+usersDir);
		}
	}

	@Override
	public List<User> getAllUsers() {
		
		return new ArrayList<User>();
	}

}
