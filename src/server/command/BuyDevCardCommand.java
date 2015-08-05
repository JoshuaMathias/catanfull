package server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import shared.definitions.DevCardType;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;

/**
 * 
 * @author Ifes Group
 *
 */
public class BuyDevCardCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7066799747480305706L;
	int playerIndex;
	GameModel serverModel;
	
	public BuyDevCardCommand(int playerIndex, GameModel serverModel){
		this.playerIndex = playerIndex;
		this.serverModel = serverModel;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Random random = new Random();
//		int cardType = random.nextInt(25); //25 development cards possible
		Player player = serverModel.getPlayers().get(playerIndex);
		DevCardList deck = serverModel.getDeck();
		
		// sheep wheat ore
		ResourceList playerResources = player.getResources();
		playerResources.setSheep(playerResources.getSheep() - 1);
		playerResources.setWheat(playerResources.getWheat() - 1);
		playerResources.setOre(playerResources.getOre() - 1);
		
		ResourceList bank = serverModel.getBank();
		bank.setSheep(bank.getSheep() + 1);
		bank.setWheat(bank.getWheat() + 1);
		bank.setOre(bank.getOre() + 1);
		
		ArrayList<DevCardType> remainingDeck = new ArrayList<>();
		for(int i = 0; i < deck.getMonopoly(); i++){
			remainingDeck.add(DevCardType.MONOPOLY);
		}
		for(int i = 0; i < deck.getMonument(); i++){
			remainingDeck.add(DevCardType.MONUMENT);
		}
		for(int i = 0; i < deck.getRoadBuilding(); i++){
			remainingDeck.add(DevCardType.ROAD_BUILD);
		}
		for(int i = 0; i < deck.getYearOfPlenty(); i++){
			remainingDeck.add(DevCardType.YEAR_OF_PLENTY);
		}
		for(int i = 0; i < deck.getSoldier(); i++){
			remainingDeck.add(DevCardType.SOLDIER);
		}
		Collections.shuffle(remainingDeck);
		
		switch(remainingDeck.get(0)){
		case MONOPOLY:
			deck.setMonopoly(deck.getMonopoly() - 1);
			player.addNewDevCard(DevCardType.MONOPOLY);
			break;
		case MONUMENT:
			deck.setMonument(deck.getMonument() - 1);
			player.addNewDevCard(DevCardType.MONUMENT);
			player.incrementMonuments();
			break;
		case ROAD_BUILD:
			deck.setRoadBuilding(deck.getRoadBuilding() - 1);
			player.addNewDevCard(DevCardType.ROAD_BUILD);
			break;
		case SOLDIER:
			deck.setSoldier(deck.getSoldier() - 1);
			player.addNewDevCard(DevCardType.SOLDIER);
			break;
		case YEAR_OF_PLENTY:
			deck.setYearOfPlenty(deck.getYearOfPlenty() - 1);
			player.addNewDevCard(DevCardType.YEAR_OF_PLENTY);
			break;
		default:
			break;
		
		}
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife is a development card slut");
		}
		else{
			line.setMessage(username + " bought a development card");
		}
//		line.setMessage(username + " bought a development card");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
