package shared.params;

import shared.gameModel.ResourceList;
/**
 * Class for sending data for an offer trade request.
 * @author Ife's Group
 *
 */
public class OfferTradeParams {
	private String type="offerTrade";
	private int playerIndex=-1;
	private ResourceList offer;
	private int receiver=-1;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public ResourceList getOffer() {
		return offer;
	}
	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	
}
