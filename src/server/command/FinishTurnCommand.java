package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.TurnTracker;

/**
 * 
 * @author Ife's Group
 *
 */
public class FinishTurnCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8888817633160407013L;
	private transient GameModel serverModel;
	private int gameID;
	
	public FinishTurnCommand(GameModel serverModel){
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
		
		TurnTracker turnTracker = serverModel.getTurnTracker();
		int currentTurn = turnTracker.getCurrentTurn();
		String status = turnTracker.getStatus();
		
		switch(status){
		case "FirstRound":
			if(currentTurn >= 3){
				turnTracker.setStatus("SecondRound");
				
			}
			else{
				turnTracker.nextTurn();
			}
			break;
		case "SecondRound":
			if(currentTurn <= 0){
				turnTracker.setStatus("Rolling");
			}
			else{
				turnTracker.previousTurn();
			}
			break;
		case "Playing":
			newToOldDevCards(currentTurn);//moves new devcards out of newDevCardList and into oldDevCardList
			turnTracker.setStatus("Rolling");
			turnTracker.nextTurn();
		}
		
		resetDiscarded();
		
//		// increment/decrement 2 points as appropriate
//		int longestRoad = serverModel.checkLongestRoad();
//		if(longestRoad != -1){
//			int previousLongestRoad = turnTracker.getLongestRoad();
//			turnTracker.setLongestRoad(longestRoad);
//			if(previousLongestRoad != longestRoad){
//				Player player = serverModel.getPlayers().get(longestRoad);
//				player.setVictoryPoints(player.getVictoryPoints() + 2);
//				if(previousLongestRoad != -1){
//					Player previousPlayer = serverModel.getPlayers().get(previousLongestRoad);
//					previousPlayer.setVictoryPoints(previousPlayer.getVictoryPoints() - 2);
//				}
//			}
//			
//			
//		}
//		
//		// increment/decrement 2 points as appropriate
//		int largestArmy = serverModel.checkLargestArmy();
//		if(largestArmy != -1){
//			int previousLargestArmy = turnTracker.getLargestArmy();
//			turnTracker.setLargestArmy(largestArmy);
//			if(previousLargestArmy != largestArmy){
//				Player player = serverModel.getPlayers().get(largestArmy);
//				player.setVictoryPoints(player.getVictoryPoints() + 2);
//				if(previousLargestArmy != -1){
//					Player previousPlayer = serverModel.getPlayers().get(previousLargestArmy);
//					previousPlayer.setVictoryPoints(previousPlayer.getVictoryPoints() - 2);
//				}
//			}
//			
//		}
		
		MessageLine line = new MessageLine();
		Player player = serverModel.getPlayers().get(currentTurn);
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife wasted his turn");
		}
		else{
			line.setMessage(username + " ended their turn");
		}
//		line.setMessage(username + " ended their turn");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

	private void resetDiscarded() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = serverModel.getPlayers();
		for (Player player: players){
			player.setDiscarded(false);
		}
	}

	private void newToOldDevCards(int currentTurn) {
		// TODO Auto-generated method stub
		Player player = serverModel.getPlayers().get(currentTurn);
		player.setPlayedDevCard(false);
		
		DevCardList newDevCards = player.getNewDevCards();
		DevCardList oldDevCards = player.getOldDevCards();
		
		oldDevCards.setMonopoly(oldDevCards.getMonopoly() + newDevCards.getMonopoly());
		oldDevCards.setMonument(oldDevCards.getMonument() + newDevCards.getMonument());
		oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding() + newDevCards.getRoadBuilding());
		oldDevCards.setSoldier(oldDevCards.getSoldier() + newDevCards.getSoldier());
		oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty() + newDevCards.getYearOfPlenty());
		
		newDevCards.setMonopoly(0);
		newDevCards.setMonument(0);
		newDevCards.setRoadBuilding(0);
		newDevCards.setSoldier(0);
		newDevCards.setYearOfPlenty(0);
		
	}

}
