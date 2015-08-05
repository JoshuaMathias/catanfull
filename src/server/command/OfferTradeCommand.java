package server.command;

import java.io.Serializable;

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
public class OfferTradeCommand implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3880103328620981406L;
	int sender;
	ResourceList offer;
	int receiver;
	GameModel serverModel;
	
	public OfferTradeCommand(int sender, ResourceList offer, int receiver,
			GameModel serverModel) {
		super();
		this.sender = sender;
		this.offer = offer;
		this.receiver = receiver;
		this.serverModel = serverModel;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		TradeOffer tradeOffer = new TradeOffer();
		tradeOffer.setSender(sender);
		tradeOffer.setReceiver(receiver);
		tradeOffer.setOffer(offer);
		serverModel.setTradeOffer(tradeOffer);
		
		Player senderPlayer = serverModel.getPlayers().get(sender);
		Player receiverPlayer = serverModel.getPlayers().get(receiver);
		
		MessageLine line = new MessageLine();
		String username = senderPlayer.getName();
		if(username.toLowerCase().equals("ife") || username.toLowerCase().equals("ogeorge")){
			line.setMessage("Ife sent a trade offer to " + receiverPlayer.getName() + ", requesting special \"favors\"");
		}
		else{
			line.setMessage(username + " sent a trade offer to " + receiverPlayer.getName());
		}
//		line.setMessage(username + " sent a trade offer to " + receiverPlayer.getName());
		line.setSource(username);
		serverModel.getLog().addLine(line);
	}

}
