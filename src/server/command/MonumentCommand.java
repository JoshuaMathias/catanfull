package server.command;

import java.io.Serializable;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;

/**
 * 
 * @author Ife's Group
 *
 */
public class MonumentCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419614526957462622L;
	private Player player;
	private GameModel serverModel;
	private int gameID;
	
	public MonumentCommand(int playerIndex, GameModel serverModel) {
	
		this.player = serverModel.getPlayers().get(playerIndex);
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
		
		DevCardList oldDevCards = player.getOldDevCards();
		int oldDevMonumentAmount= oldDevCards.getMonument();
		if(oldDevMonumentAmount > 0) {
			
			oldDevMonumentAmount -= 1;
			oldDevCards.setMonument(oldDevMonumentAmount);
		} 
		else {
			
			DevCardList newDevCards = player.getNewDevCards();
			int newDevMonumentAmount = newDevCards.getMonument();
			newDevMonumentAmount -= 1;
			newDevCards.setMonument(newDevMonumentAmount);
		}
		
		int victoryPoints = player.getVictoryPoints();
		victoryPoints += 1;
		player.setVictoryPoints(victoryPoints);
		player.decrementMonuments();//This line not really needed, but it shouldn't hurt
		
		/*int monumentAmount = deck.getMonument();
		monumentAmount += 1;
		deck.setMonument(monumentAmount);*/
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife can only score by playing Monument cards, but can't score with girls");
		}
		else{
			line.setMessage(username + " played a Monument card");
		}
//		line.setMessage(username + " played a Monument card");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
