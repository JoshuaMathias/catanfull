package shared.params;
/**
 * Class for sending data for a monopoly request.
 * @author Ife's Group
 *
 */
public class MonopolyParams {
	private String type="Monopoly";
	private String resource;
	private int playerIndex=-1;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
}
