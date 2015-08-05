package shared.params;
/**
 * Class for sending data for a monument request.
 * @author Ife's Group
 *
 */
public class MonumentParams {
	private String type="Monument";
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
