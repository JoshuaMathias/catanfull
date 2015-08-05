package shared.params;

/**
 * 
 * Class for sending data for a finish turn request.
 * @author Ife's Group
 *
 */
public class FinishTurnParams {
	private String type="finishTurn";
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
