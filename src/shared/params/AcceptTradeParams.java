package shared.params;
/**
 * Class for sending data for an accept trade request.
 * @author Ife's Group
 *
 */
public class AcceptTradeParams {
	String type="acceptTrade";
	private int playerIndex=-1;
	private boolean willAccept=false;
	
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
	public boolean isWillAccept() {
		return willAccept;
	}
	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}
	
}
