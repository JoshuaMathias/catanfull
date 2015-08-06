package dao.other;

import java.util.List;

import server.command.Command;
import shared.gameModel.GameModel;
import dao.IGameDao;

/**
 * concrete implementation of the Game DAO for using a File 
 * @author Ife's Group
 *
 */
public class OtherGameDao implements IGameDao {
	
	public OtherGameDao() {
		
	}

	@Override
	public void addGame(GameModel game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getGame(int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameModel> getAllGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGame(GameModel game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGame(int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommand(Command command, int gameID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Command> getCommands(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

}
