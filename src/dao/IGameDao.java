package dao;

import java.util.List;

import server.command.Command;
import shared.gameModel.GameModel;

/**
 * Interface to the Game Data Access objects
 * @author Ife's Group
 *
 */
public interface IGameDao {

	/**
	 * Adds new game to respective Storage
	 * @param game
	 */
	public void addGame(GameModel game);
	
	public void getGame(int gameID);//Maybe don't need this
	
	/**
	 * retieves List of all games in storage
	 * @return List
	 */
	public List<GameModel> getAllGames();
	
	/**
	 * updates specific game and removes all commands associated with that game
	 * @param game
	 */
	public void updateGame(GameModel game);
	
	public void removeGame(int gameID);
	
	/**
	 * adds command associated with a specific game to storage
	 * @param command
	 * @param key is the primary key of the game
	 */
	public void addCommand(Command command, int key);
	
	/**
	 * retrieves all commands associated with a specific game
	 * @param gameID
	 * @return
	 */
	public List<Command> getCommands(int gameID);//Should also delete commands
	
}
