package shared.params;

/**
 * Class for sending data for a roll number request.
 * @author Ife's Group
 *
 */
public class RollNumberParams {
	private String type="rollNumber";
	private int playerIndex=-1;
	private int number=-1;
	
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

}
