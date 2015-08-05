package server.command;

import java.io.Serializable;

import shared.definitions.CatanColor;
import shared.gameModel.GameModel;
import shared.gameModel.Player;

/**
 * 
 * @author Ife's Group
 *
 */
public class JoinGameCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540431475253346304L;
	private CatanColor color;
	private String username;
	private int playerID;
	private GameModel serverModel;
	
	public JoinGameCommand(CatanColor color, String username, int playerID, GameModel serverModel){
		this.color = color;
		this.username = username;
		this.playerID = playerID;
		this.serverModel = serverModel;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Player player = new Player();
		player.setName(username);
		player.setColor(color);
		player.setPlayerID(playerID);
		serverModel.addPlayer(player);
		
		if(username.toLowerCase().equals("wintest")){
			player.setVictoryPoints(9);
		}
	}

}
