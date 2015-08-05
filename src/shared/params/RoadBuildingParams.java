package shared.params;

import client.serverproxy.EdgeLocation;

/**
 * Class for sending data for a road building request.
 * @author Ife's Group
 *
 */
public class RoadBuildingParams {
	private String type="Road_Building";
	private int playerIndex=-1;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
	
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
	public EdgeLocation getSpot1() {
		return spot1;
	}
	public void setSpot1(EdgeLocation spot1) {
		this.spot1 = spot1;
	}
	public EdgeLocation getSpot2() {
		return spot2;
	}
	public void setSpot2(EdgeLocation spot2) {
		this.spot2 = spot2;
	}
	
}
