package server.command;

import java.io.Serializable;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.locations.HexLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class SoldierCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1002301330706263584L;
	int playerIndex;
	int victimIndex;
	HexLocation location;
	transient GameModel serverModel;
	private int gameID;
	
	public SoldierCommand(int playerIndex, int victimIndex, HexLocation location, GameModel serverModel) {
		super();
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
		this.serverModel = serverModel;
		this.gameID = serverModel.getGameID();
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		if (GamesHandler.test) {
			serverModel = ServerFacadeTest.getSingleton().getGameModel(gameID);
		} else {
			serverModel = ServerFacade.getSingleton().getGameModel(gameID);
		}
		
		new RobPlayerCommand(playerIndex, victimIndex, location, serverModel).execute();
		
		Player player = serverModel.getPlayers().get(playerIndex);
		DevCardList playerOldDevCardList = player.getOldDevCards();
		playerOldDevCardList.setSoldier(playerOldDevCardList.getSoldier() - 1);
		player.setSoldiers(player.getSoldiers() + 1);
		player.setPlayedDevCard(true);
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife probably screwed Daniel over with a soldier card, again");
		}
		else{
			line.setMessage(username + " played a Soldier Development Card");
		}
//		line.setMessage(username + " played a Soldier Development Card");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
