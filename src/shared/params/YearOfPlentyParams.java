package shared.params;

/**
 * Class for sending data for a year of plenty request.
 * @author Ife's Group
 *
 */
public class YearOfPlentyParams {
	private String type="Year_of_Plenty";
	private int playerIndex=-1;
	private String resource1;
	private String resource2;
	
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
	public String getResource1() {
		return resource1;
	}
	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}
	public String getResource2() {
		return resource2;
	}
	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}
	
}
