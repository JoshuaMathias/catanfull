package shared.params;

import shared.gameModel.ResourceList;
/**
 * Class for sending data for a discard card request.
 * @author Ife's Group
 *
 */
public class DiscardCardsParams {
	
	private String type="discardCards";
	private int playerIndex=-1;
	private ResourceList discardedCards;
	
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
	public ResourceList getDiscardedCards() {
		return discardedCards;
	}
	public void setDiscardedCards(ResourceList discardedCards) {
		this.discardedCards = discardedCards;
	}
	
}
