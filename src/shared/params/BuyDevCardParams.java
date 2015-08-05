package shared.params;
/**
 * Class for sending data for a buy development card or a finish turn request.
 * @author Ife's Group
 *
 */
public class BuyDevCardParams {
	private String type="buyDevCard";
	private int playerIndex=-1;
	
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
	
}
