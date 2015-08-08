package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.VertexObject;
import shared.locations.VertexLocation;

/**
 * 
 * @author Ife's Group
 *
 */
public class BuildCityCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -275579207207116950L;
	private int playerIndex;
	private VertexLocation vertexLocation;
	private transient GameModel serverModel;
	private Player player;
	private int gameID;
	
	public BuildCityCommand(int playerIndex, VertexLocation vertexLocation, GameModel serverModel) {
		
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation.getNormalizedLocation();
		this.serverModel = serverModel;
		this.gameID = serverModel.getGameID();
	}
	
	@Override
	public void execute() {
		
		if (GamesHandler.test) {
			serverModel = ServerFacadeTest.getSingleton().getGameModel(gameID);
		} else {
			serverModel = ServerFacade.getSingleton().getGameModel(gameID);
		}
		
		ArrayList<Player> playerList = serverModel.getPlayers();
		player = playerList.get(playerIndex);
		
		updatePlayerResources();
		updateBankResources();
		
		player.decrementCity();
		player.incrementSettlement();
		int victoryPoints = player.getVictoryPoints();
		player.setVictoryPoints(victoryPoints + 1);
		
		Map map = serverModel.getMap();
		map.removeSettlement(new VertexObject(playerIndex, vertexLocation));
		map.addCity(new VertexObject(playerIndex, vertexLocation));
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife built a city, but there are no good looking girls in it");
		}
		else{
			line.setMessage(username + " built a city");
		}
//		line.setMessage(username + " built a city");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}
	
	private void updatePlayerResources() {
		
		ResourceList playerResourceList = player.getResources();
		int currentPlayerOre = playerResourceList.getOre();
		int currentPlayerWheat = playerResourceList.getWheat();
		
		int updatedPlayerOre = currentPlayerOre - 3;
		int updatedPlayerWheat = currentPlayerWheat - 2;
		
		playerResourceList.setOre(updatedPlayerOre);
		playerResourceList.setWheat(updatedPlayerWheat);
	}
	
	private void updateBankResources() {
		
		ResourceList bank = serverModel.getBank();
		
		int currentBankOre = bank.getOre();
		int currentBankWheat = bank.getWheat();
		
		int updatedBankOre = currentBankOre + 3;
		int updatedBankWheat = currentBankWheat + 2;
		
		bank.setOre(updatedBankOre);
		bank.setWheat(updatedBankWheat);
	}

}
