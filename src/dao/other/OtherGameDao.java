package dao.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import server.User;
import server.command.Command;
import shared.gameModel.GameModel;
import dao.IGameDao;

/**
 * concrete implementation of the Game DAO for using a File
 * 
 * @author Ife's Group
 * 
 */
public class OtherGameDao implements IGameDao {
	private String gamesPath = "persistent" + File.separator + "Games";
	private String commandsPath = "persistent" + File.separator + "Commands";
	Gson g;

	public OtherGameDao() {
		g = new Gson();
	}

	@Override
	public void addGame(GameModel game) {
		File gamesDir = new File(gamesPath);
		if (gamesDir.exists()) {
		try {
			Files.write(
					Paths.get(gamesPath + File.separator + "Game"
							+ game.getGameID()+".txt"), (g.toJson(game)+"\r").getBytes(),
					StandardOpenOption.CREATE);
			System.out.println("Wrote game "+game.getGameID()+" to file "+"Game"
							+ game.getGameID()+".txt");
		} catch (IOException e) {
			System.out.println("Failed to write game to file.");
			e.printStackTrace();
		}
		} else {
			System.out.println("Directory "+gamesPath+" doesn't exist.");
		}
	}

	@Override
	// Function not used
	public void getGame(int gameID) {
	}

	@Override
	public List<GameModel> getAllGames() {
		ArrayList<GameModel> gamesList = new ArrayList<GameModel>();
		int gameID = 0;
		File gameFile = new File(gamesPath + File.separator + "Game" + gameID+".txt");
		while (gameFile.exists()) {
			Scanner gameScan;
			String gameStr = "";
			try {
				gameScan = new Scanner(gameFile);
				while (gameScan.hasNextLine()) {
					gameStr = gameScan.nextLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			gamesList.add(g.fromJson(gameStr, GameModel.class));
			gameID++;
			gameFile = new File(gamesPath + File.separator + "Game" + gameID+".txt");
		}
		return gamesList;
	}

	@Override
	public void updateGame(GameModel game) {
		try {
			Files.write(
					Paths.get(gamesPath + File.separator + "Game"
							+ game.getGameID()+".txt"), (g.toJson(game)).getBytes(),
					StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Failed to update game on file.");
			e.printStackTrace();
		}
	}

	@Override  //Function not used
	public void removeGame(int gameID) {
		

	}

	@Override
	public void addCommand(Command command, int gameID) {
		String commandFileStr = commandsPath + File.separator + "Commands" +gameID+".txt";
		try {
			File commandsFile = new File(commandFileStr);
			if (!commandsFile.exists()) {
				Files.write(Paths.get(commandFileStr),
						(g.toJson(command) + "\r").getBytes(),
						StandardOpenOption.CREATE);
			} else {
				Files.write(Paths.get(commandFileStr),
						(g.toJson(command) + "\r").getBytes(),
						StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			System.out.println("Failed to write command to file.");
			e.printStackTrace();
		}
	}

	@Override
	public List<Command> getCommands(int gameID) {
		String commandFileStr = commandsPath + File.separator + "Commands" + gameID+".txt";
		List <Command> commands = new ArrayList<>();
			File commandsFile = new File(commandFileStr);
			if (!commandsFile.exists()) {
				Scanner commandScan;
				try {
					commandScan = new Scanner(commandsFile);
					while (commandScan.hasNextLine()) {
						commands.add(g.fromJson(commandScan.nextLine(), Command.class));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("No commands found for game "+gameID);
			}
		return commands;
	}

}
