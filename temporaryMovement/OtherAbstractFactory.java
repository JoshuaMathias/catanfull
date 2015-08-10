package abstractFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import shared.gameModel.GameModel;

import dao.IGameDao;
import dao.IUserDao;
import dao.other.OtherGameDao;
import dao.other.OtherUserDao;

/**
 * concrete implementation of another persistent storage factory (File)
 * @author Ife's Group
 *
 */
public class OtherAbstractFactory implements IAbstractFactory {

	private IUserDao userDao = new OtherUserDao();
	private IGameDao gameDao = new OtherGameDao();
	
	@Override
	public IUserDao getUserDao() {
		return userDao;
	}

	@Override
	public IGameDao getGameDao() {
		return gameDao;
	}

	@Override
	public void erase() {
		File usersFile = new File("persistent" + File.separator + "Users"
			+ File.separator + "users.txt");
		if (usersFile.exists()) {
			usersFile.delete();
		}
		int gameID = 0;
		File gameFile = new File("persistent" + File.separator + "Games" + File.separator + "Game" + gameID+".txt");
		File commandFile = new File("persistent" + File.separator + "Commands" + File.separator + "Commands" + gameID+".txt");
		while (gameFile.exists()) {
			commandFile.delete();
			gameFile.delete();
			gameID++;
			gameFile = new File("persistent" + File.separator + "Games" + File.separator + "Game" + gameID+".txt");
			commandFile = new File("persistent" + File.separator + "Commands" + File.separator + "Commands" + gameID+".txt");
		}
	}

	@Override
	public void startTransaction() {
		
	}
	
	@Override
	public boolean inTransaction() {
		return false;
	}

	@Override
	public void endTransaction(boolean commit) {
		
	}

}
