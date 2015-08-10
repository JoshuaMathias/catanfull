package server.command;

import java.io.Serializable;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.Road;
import shared.locations.EdgeLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class RoadBuildingCommand implements Command, Serializable {
	
	/**
	 * 
	 */
	private String className = "RoadBuildingCommand";
	private static final long serialVersionUID = -4460468594336446763L;
	private int sender;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
	private transient GameModel serverModel;
	private int gameID;
	
	public RoadBuildingCommand(int sender, EdgeLocation spot1,
			EdgeLocation spot2, GameModel serverModel) {
		super();
		this.sender = sender;
		this.spot1 = spot1.getNormalizedLocation();
		this.spot2 = spot2.getNormalizedLocation();
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
		
		Player player = serverModel.getPlayers().get(sender);
		DevCardList playerOldDevCards = player.getOldDevCards();
		
		playerOldDevCards.setRoadBuilding(playerOldDevCards.getRoadBuilding() - 1);
		
		serverModel.getMap().addRoad(new Road(sender, spot1));
		serverModel.getMap().addRoad(new Road(sender, spot2));
		
		player.setRoads(player.getRoads() - 2);
		
		player.setPlayedDevCard(true);
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife built a couple of roads to try to catch up with Paul, but Daniel always wins anyway");
		}
		else{
			line.setMessage(username + " built a couple roads with a road building development card");
		}
//		line.setMessage(username + " built a couple roads with a road building development card");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
