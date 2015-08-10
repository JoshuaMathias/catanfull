package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;

/**
 * 
 * @author Ife's Group
 *
 */
public class AcceptTradeCommand implements Command, Serializable{

	/**
	 * 
	 */
	private String className = "AcceptTradeCommand";
	private static final long serialVersionUID = 4777851003074432445L;
	private int playerIndex;
	private boolean willAccept;
	private transient GameModel serverModel;
	private int gameID;
	private TradeOffer tradeOffer;
	
	public AcceptTradeCommand(int playerIndex, boolean willAccept, GameModel serverModel){
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
		this.serverModel = serverModel;
		this.gameID = serverModel.getGameID();
		this.tradeOffer = serverModel.getTradeOffer();
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		if (GamesHandler.test) {
			serverModel = ServerFacadeTest.getSingleton().getGameModel(gameID);
		} else {
			serverModel = ServerFacade.getSingleton().getGameModel(gameID);
		}
		
		if (willAccept){
//			TradeOffer tradeOffer = serverModel.getTradeOffer();
			ResourceList offerList = tradeOffer.getOffer();
			
			ArrayList<Player> players = serverModel.getPlayers();
			
			Player sender = players.get(tradeOffer.getSender());
			Player receiver = players.get(playerIndex);
			
			ResourceList senderResources = sender.getResources();
			ResourceList receiverResources = receiver.getResources();
			
			int offerBrick = offerList.getBrick();
			int offerWheat = offerList.getWheat();
			int offerSheep = offerList.getSheep();
			int offerOre = offerList.getOre();
			int offerWood = offerList.getWood();
			
			senderResources.setBrick(senderResources.getBrick() - offerBrick);
			receiverResources.setBrick(receiverResources.getBrick() + offerBrick);
			
			senderResources.setWheat(senderResources.getWheat() - offerWheat);
			receiverResources.setWheat(receiverResources.getWheat() + offerWheat);
			
			senderResources.setSheep(senderResources.getSheep() - offerSheep);
			receiverResources.setSheep(receiverResources.getSheep() + offerSheep);
			
			senderResources.setOre(senderResources.getOre() - offerOre);
			receiverResources.setOre(receiverResources.getOre() + offerOre);
			
			senderResources.setWood(senderResources.getWood() - offerWood);
			receiverResources.setWood(receiverResources.getWood() + offerWood);
			
			
			MessageLine line = new MessageLine();
			String username = receiver.getName();
			if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
				line.setMessage("Ife accepted the trade but probably ripped the other guy off");
			}
			else{
				line.setMessage("The trade was accepted");
			}
//			line.setMessage("The trade was accepted");
			line.setSource(username);
			serverModel.getLog().addLine(line);
		}
		else{
			Player receiver = serverModel.getPlayers().get(playerIndex);
			MessageLine line = new MessageLine();
			String username = receiver.getName();
			if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
				line.setMessage("Ife rejected the trade, probably because he is greedy and selfish");
			}
			else{
				line.setMessage("The trade was rejected");
			}
//			line.setMessage("The trade was rejected");
			line.setSource(receiver.getName());
			serverModel.getLog().addLine(line);
		}
		serverModel.setTradeOffer(null);
	}

}
