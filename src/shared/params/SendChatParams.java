package shared.params;

/**
 * 
 * Class for sending data for a send chat request.
 * @author Ife's Group
 *
 */
public class SendChatParams {
	private String type="sendChat";
	private int playerIndex=-1;
	private String content="";
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
