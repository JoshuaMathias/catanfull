package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;

/**
 * 
 * @author Ife's Group
 *
 */
public class DiscardCardsCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6370485199845730789L;
	private int playerIndex;
	private ResourceList discardedCards;
	private GameModel serverModel;
	
	public DiscardCardsCommand(int playerIndex, ResourceList discardedCards, GameModel serverModel){
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
		this.serverModel = serverModel;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = serverModel.getPlayers();
		Player player = players.get(playerIndex);
		ResourceList playerResources = player.getResources();
		int BrickToDiscard = discardedCards.getBrick();
		int WheatToDiscard = discardedCards.getWheat();
		int OreToDiscard = discardedCards.getOre();
		int WoodToDiscard = discardedCards.getWood();
		int SheepToDiscard = discardedCards.getSheep();
		
		playerResources.setBrick(playerResources.getBrick() - BrickToDiscard);
		playerResources.setWheat(playerResources.getWheat() - WheatToDiscard);
		playerResources.setOre(playerResources.getOre() - OreToDiscard);
		playerResources.setWood(playerResources.getWood() - WoodToDiscard);
		playerResources.setSheep(playerResources.getSheep() - SheepToDiscard);
		
		ResourceList bank = serverModel.getBank();
		bank.setBrick(bank.getBrick() + BrickToDiscard);
		bank.setWheat(bank.getWheat() + WheatToDiscard);
		bank.setOre(bank.getOre() + OreToDiscard);
		bank.setWood(bank.getWood() + WoodToDiscard);
		bank.setSheep(bank.getSheep() + SheepToDiscard);
		
		player.setDiscarded(true);
		//change status to "Robbing" once everyone has discarded!!!!!!!!!!!!!!!!!!!!
		boolean doneDiscarding = true;
		
		ArrayList<Integer> discardingPlayersIndeces = serverModel.getDiscardingPlayersIndeces();
		if(discardingPlayersIndeces != null){
			for(int discardingPlayerIndex: discardingPlayersIndeces){
				if(players.get(discardingPlayerIndex).isDiscarded() == false){
					doneDiscarding = false;
					break;
				}
			}
		}
		
		if(doneDiscarding){
			serverModel.getTurnTracker().setStatus("Robbing");
			serverModel.setDiscardingPlayersIndeces(null);
		}
		
		MessageLine line = new MessageLine();
		String username = player.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife discarded half his cards because he plans badly and is going nowhere in this game");
		}
		else{
			line.setMessage(username + " discarded half their cards");
		}
//		line.setMessage(username + " discarded half their cards");
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
