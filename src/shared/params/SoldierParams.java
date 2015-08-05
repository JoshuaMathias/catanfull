package shared.params;

import shared.locations.HexLocation;

/**
 * Class for sending data for a soldier request.
 * @author Ife's Group
 *
 */
public class SoldierParams {
	private String type="Soldier";
	private int playerIndex=-1;
	private int victimIndex=-1;
	private HexLocation location;
	
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
	public int getVictimIndex() {
		return victimIndex;
	}
	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	
}
