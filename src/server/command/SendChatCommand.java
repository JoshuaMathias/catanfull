package server.command;

import java.util.ArrayList;

import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.MessageList;
import shared.gameModel.Player;

/**
 * 
 * @author Ife's Group
 *
 */
public class SendChatCommand implements Command {

	private GameModel serverModel;
	private String message;
	private int playerIndex;
	
	public SendChatCommand(String message, int playerIndex, GameModel serverModel) {
		this.message = message;
		this.playerIndex = playerIndex;
		this.serverModel = serverModel;
	}
	
	@Override
	public void execute() {
		
		MessageList messageList = serverModel.getChat();
		ArrayList<MessageLine> lines = messageList.getLines();
		
		ArrayList<Player> players = serverModel.getPlayers();
		String playerName = null;
		for(Player player : players) {
			
			if(player.getPlayerIndex() == playerIndex) {
				playerName = player.getName();
			}
		}
		
		MessageLine newLine = new MessageLine();
		newLine.setMessage(message);
		newLine.setSource(playerName);

		lines.add(newLine);
		messageList.setLines(lines);
	}

}
