package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.definitions.ResourceType;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;

/**
 * 
 * @author Ife's Group
 *
 */
public class YearOfPlentyCommand implements Command, Serializable {

	/**
	 * 
	 */
	private String className = "YearOfPlentyCommand";
	private static final long serialVersionUID = 8203427744840950814L;
	private int playerIndex;
	private ResourceType cardOne;
	private ResourceType cardTwo;
	private transient GameModel serverModel;
	private int gameID;
	
	public YearOfPlentyCommand(int playerIndex, ResourceType cardOne, ResourceType cardTwo, GameModel serverModel) {
		
		this.playerIndex = playerIndex;
		this.cardOne = cardOne;
		this.cardTwo = cardTwo;
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
		
		ResourceList bank = serverModel.getBank();
		
		scrollResources(cardOne,bank);
		scrollResources(cardTwo,bank);
		
		ArrayList<Player> players = serverModel.getPlayers();
		Player mainPlayer = null;
		for(Player player : players) {
			
			if(player.getPlayerIndex() == playerIndex) {
				mainPlayer = player;
			}
		}
		
		ResourceList playerResources = mainPlayer.getResources();
		
		scrollPlayerResources(cardOne,playerResources);
		scrollPlayerResources(cardTwo,playerResources);
		
		mainPlayer.setPlayedDevCard(true);
		
		DevCardList oldDevCards = mainPlayer.getOldDevCards();
		oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty() - 1);
		
		MessageLine line = new MessageLine();
		String username = mainPlayer.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife can't get them any other way, so he used a Year of Plenty card to get a " + cardOne.toString() + " and a" + cardTwo.toString());
		}
		else{
			line.setMessage(username + " played a Year of Plenty card for " + cardOne.toString() + " and " + cardTwo.toString());
		}
//		line.setMessage(username + " played a year of plenty card for " + cardOne.toString() + " and " + cardTwo.toString());
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

	private void scrollResources(ResourceType card, ResourceList list) {
		
		switch(card){
		case brick:
			int brickAmount = list.getBrick();
			brickAmount--;
			list.setBrick(brickAmount);
			break;
		case ore:
			int oreAmount = list.getOre();
			oreAmount--;
			list.setOre(oreAmount);
			break;
		case sheep:
			int sheepAmount = list.getSheep();
			sheepAmount--;
			list.setSheep(sheepAmount);
			break;
		case wheat:
			int wheatAmount = list.getWheat();
			wheatAmount--;
			list.setWheat(wheatAmount);
			break;
		case wood:
			int woodAmount = list.getWood();
			woodAmount--;
			list.setWood(woodAmount);
			break;
		default:
			break;
		
		}
	}
	
private void scrollPlayerResources(ResourceType card, ResourceList list) {
		
		switch(card){
		case brick:
			int brickAmount = list.getBrick();
			brickAmount++;
			list.setBrick(brickAmount);
			break;
		case ore:
			int oreAmount = list.getOre();
			oreAmount++;
			list.setOre(oreAmount);
			break;
		case sheep:
			int sheepAmount = list.getSheep();
			sheepAmount++;
			list.setSheep(sheepAmount);
			break;
		case wheat:
			int wheatAmount = list.getWheat();
			wheatAmount++;
			list.setWheat(wheatAmount);
			break;
		case wood:
			int woodAmount = list.getWood();
			woodAmount++;
			list.setWood(woodAmount);
			break;
		default:
			break;
		
		}
	}
}
