package server.command;

import java.util.ArrayList;

import shared.definitions.ResourceType;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;

public class MonopolyCommand implements Command {

	private int playerIndex;
	private ResourceType resource;
	private GameModel serverModel;
	
	private Player monopolyPlayer;
	private ResourceList monopolyPlayerResources;
	
	public MonopolyCommand(int playerIndex, ResourceType resource,
			GameModel serverModel) {
		super();
		this.playerIndex = playerIndex;
		this.resource = resource;
		this.serverModel = serverModel;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = serverModel.getPlayers();
		this.monopolyPlayer = players.get(playerIndex);
		this.monopolyPlayerResources = this.monopolyPlayer.getResources();
		
		for(Player player: players){
			if(player.getPlayerIndex() != playerIndex){
				stealResources(player);
			}
		}
		
		DevCardList monopolyPlayerOldDevCardList  = monopolyPlayer.getOldDevCards();
		monopolyPlayerOldDevCardList.setMonopoly(monopolyPlayerOldDevCardList.getMonopoly() - 1);
		
		this.monopolyPlayer.setPlayedDevCard(true);
		
		MessageLine line = new MessageLine();
		String username = this.monopolyPlayer.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife is a criminal who stole everybody's " + resource.toString());
		}
		else{
			line.setMessage(username + " played a Monopoly card to steal everybody's " + resource.toString());
		}
//		line.setMessage(username + " played a Monopoly card to steal everybody's " + resource.toString());
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

	private void stealResources(Player player) {
		// TODO Auto-generated method stub
		ResourceList playerResources = player.getResources();
		
		switch(resource){
		case brick:
			this.monopolyPlayerResources.setBrick(this.monopolyPlayerResources.getBrick() + playerResources.getBrick());
			playerResources.setBrick(0);
			break;
		case ore:
			this.monopolyPlayerResources.setOre(this.monopolyPlayerResources.getOre() + playerResources.getOre());
			playerResources.setOre(0);
			break;
		case sheep:
			this.monopolyPlayerResources.setSheep(this.monopolyPlayerResources.getSheep() + playerResources.getSheep());
			playerResources.setSheep(0);
			break;
		case wheat:
			this.monopolyPlayerResources.setWheat(this.monopolyPlayerResources.getWheat() + playerResources.getWheat());
			playerResources.setWheat(0);
			break;
		case wood:
			this.monopolyPlayerResources.setWood(this.monopolyPlayerResources.getWood() + playerResources.getWood());
			playerResources.setWood(0);
			break;
		default:
			break;
		
		}
	}

}
