package shared.params;

/**
 * 
 * Class for sending data for a maritime trade request.
 * @author Ife's Group
 *
 */
public class MaritimeTradeParams {
	private String type="maritimeTrade";
	private int playerIndex=-1;
	private int ratio=-1;
	private String inputResource="";
	private String outputResource="";
	
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
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	public String getInputResource() {
		return inputResource;
	}
	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}
	public String getOutputResource() {
		return outputResource;
	}
	public void setOutputResource(String outputResource) {
		this.outputResource = outputResource;
	}
	
}
