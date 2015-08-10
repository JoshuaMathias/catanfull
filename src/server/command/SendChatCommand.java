package server.command;

import java.io.Serializable;
import java.util.ArrayList;

import Testing.Proxy.ServerFacadeTest;
import server.GamesHandler;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.MessageLine;
import shared.gameModel.MessageList;
import shared.gameModel.Player;

/**
 * 
 * @author Ife's Group
 *
 */
public class SendChatCommand implements Command, Serializable {

	/**
	 * 
	 */
	private String className = "SendChatCommand";
	private static final long serialVersionUID = -8149568725648502070L;
	private transient GameModel serverModel;
	private String message;
	private int playerIndex;
	private int gameID;
	
	public SendChatCommand(String message, int playerIndex, GameModel serverModel) {
		this.message = message;
		this.playerIndex = playerIndex;
		this.serverModel = serverModel;
		this.gameID = serverModel.getGameID();
	}
	
	@Override
	public void execute() {
		
		if (GamesHandler.test) {
			serverModel = ServerFacadeTest.getSingleton().getGameModel(gameID);
		} else {
			serverModel = ServerFacade.getSingleton().getGameModel(gameID);
		}
		
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
