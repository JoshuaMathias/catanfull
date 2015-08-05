package dao;

import java.util.List;

import server.command.Command;
import shared.gameModel.GameModel;

public interface IGameDao {

	public void addGame(GameModel game);
	
	public void getGame(int gameID);//Maybe don't need this
	
	public List<GameModel> getAllGames();
	
	public void updateGame(GameModel game);
	
	public void removeGame(int gameID);
	
	public void addCommand(Command command, int key);
	
	public List<Command> getCommands(int gameID);//Should also delete commands
	
}
